/*
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.vga.platform.elsa.core.remoting;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.vga.platform.elsa.common.core.l10n.Localizer;
import com.vga.platform.elsa.common.core.model.common.Xeption;
import com.vga.platform.elsa.common.core.serialization.JsonMarshaller;
import com.vga.platform.elsa.common.core.serialization.JsonUnmarshaller;
import com.vga.platform.elsa.common.core.serialization.SerializationParameters;
import com.vga.platform.elsa.common.core.utils.ExceptionUtils;
import com.vga.platform.elsa.common.core.utils.TextUtils;
import com.vga.platform.elsa.common.meta.remoting.RemotingMetaRegistry;
import com.vga.platform.elsa.common.rest.core.RemotingMessage;
import com.vga.platform.elsa.common.rest.core.RemotingMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public abstract class BaseRemotingController {
    private final String remotingId;

    @Autowired
    private BeanFactory bf;

    @Autowired
    private RemotingMetaRegistry registry;

    @Autowired
    private JsonUnmarshaller unmarshaller;

    private final SerializationParameters serializationParameters;

    private final JsonFactory jsonFactory = new JsonFactory();

    @Autowired
    private Localizer localizer;

    @Autowired
    private JsonMarshaller marshaller;

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ConcurrentHashMap<String, ChannelData> channels = new ConcurrentHashMap<>();

    public BaseRemotingController(String restId) {
        this.remotingId = restId;
        serializationParameters = new SerializationParameters().setClassSerializationStrategy(SerializationParameters.ClassSerializationStrategy.NAME)
                .setEntityReferenceCaptionSerializationStrategy(SerializationParameters.EntityReferenceCaptionSerializationStrategy.ALL)
                .setEnumSerializationStrategy(SerializationParameters.EnumSerializationStrategy.NAME)
                .setEmptyListSerializationStrategy(SerializationParameters.EmptyListSerializationStrategy.INCLUDE).setPrettyPrint(false);
    }

    @PostMapping("subscribe")
    public String subscribe(@RequestHeader String groupId, @RequestHeader String subscriptionId, @RequestHeader String clientId, HttpServletRequest request) throws Exception {
        var channel = channels.get(clientId);
        if (channel == null) {
            throw Xeption.forDeveloper("channel does not exist");
        }
        var remoting = registry.getRemotings().get(remotingId);
        var subscriptionDescription = remoting.getGroups().get(groupId).getSubscriptions().get(subscriptionId);
        Object param;
        try (var is = request.getInputStream()) {
            param = unmarshaller.unmarshal(Class.forName(subscriptionDescription.getParameterClassName()), is, serializationParameters);
        }
        var uuid = TextUtils.generateUUID();
        var sd = new SubscriptionData(param);
        channel.subscriptions.put(uuid, sd);
        return uuid;
    }

    @PostMapping("confirmSubscriptionEvent")
    public void confirmSubscriptionEvent(@RequestHeader String clientId, @RequestHeader String subscriptionId, @RequestParam boolean received) {
        var channel = channels.get(clientId);
        if (channel == null) {
            return;
        }
        synchronized (channel.lock) {
            var subscription = channel.subscriptions.get(subscriptionId);
            if (subscription == null) {
                return;
            }
            if (!received) {
                channel.subscriptions.remove(subscriptionId);
            }
            subscription.lastMessageSent = null;
        }
    }

    public void deleteStalledChannels() {
        var now = LocalDateTime.now();
        entry:
        for (var entry : new HashSet<>(channels.entrySet())) {
            synchronized (entry.getValue().lock) {
                for (SubscriptionData sv : entry.getValue().subscriptions.values()) {
                    if (sv.lastMessageSent != null && Duration.between(sv.lastMessageSent, now).toMillis() > 10000L) {
                        channels.remove(entry.getKey());
                        entry.getValue().sink.complete();
                        continue entry;
                    }
                }
                if (Duration.between(entry.getValue().lastUpdated, now).toMillis() > TimeUnit.MINUTES.toMillis(10)) {
                    channels.remove(entry.getKey());
                    entry.getValue().sink.complete();
                }
            }
        }
    }

    @PostMapping("unsubscribe")
    public void unsubscribe(@RequestHeader String clientId, @RequestHeader String subscriptionId) {
        var channel = channels.get(clientId);
        if (channel == null) {
            return;
        }
        channel.subscriptions.remove(subscriptionId);
    }

    public void sendSubscriptionEvent(String groupId, String subscriptionId, Object event) {
        ExceptionUtils.wrapException(() -> {
            String content = null;
            var handler = bf.getBean(RemotingHandlersRegistry.class).getSubscriptionHandler("%s:%s:%s".formatted(remotingId, groupId, subscriptionId));
            for (ChannelData channelData : channels.values()) {
                for (Map.Entry<String, SubscriptionData> entry : channelData.subscriptions.entrySet()) {
                    String subId = entry.getKey();
                    if (handler.isApplicable(event, entry.getValue().parameter)) {
                        if (content == null) {
                            var remoting = registry.getRemotings().get(remotingId);
                            var subscriptionDescription = remoting.getGroups().get(groupId).getSubscriptions().get(subscriptionId);
                            var baos = new ByteArrayOutputStream();
                            marshaller.marshal(event, baos, false, serializationParameters);
                            content = baos.toString(StandardCharsets.UTF_8);
                        }
                        var message = new RemotingMessage();
                        message.setType(RemotingMessageType.SUBSCRIPTION);
                        message.setSubscriptionId(subId);
                        message.setData(content);
                        var baos = new ByteArrayOutputStream();
                        marshaller.marshal(message, baos, false, serializationParameters);
                        channelData.sink.next(ServerSentEvent.<String>builder().data(baos.toString(StandardCharsets.UTF_8)).build());
                        entry.getValue().lastMessageSent = LocalDateTime.now();
                    }
                }
            }
        });

    }

    public void checkStalledClientCalls() {
        channels.values().forEach(channelData -> {
                    synchronized (channelData.lock) {
                        channelData.lock.notifyAll();
                    }
                }
        );
    }


    @PostMapping("request")
    public void request(Principal principal, @RequestHeader String groupId, @RequestHeader String clientId, @RequestHeader String methodId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            var channel = channels.get(clientId);
            if(channel != null) {
                synchronized (channel.lock) {
                    channel.lock.notifyAll();
                }
                channel.lastUpdated = LocalDateTime.now();
            }
            var remoting = registry.getRemotings().get(remotingId);
            var serverCall = remoting.getGroups().get(groupId).getServerCalls().get(methodId);
            var ctx = new RemotingServerCallContext();
            ctx.setClientId(clientId);
            Object rq = null;
            if (serverCall.getRequestClassName() != null) {
                try (var is = request.getInputStream()) {
                    rq = unmarshaller.unmarshal(Class.forName(serverCall.getRequestClassName()), is, serializationParameters);
                }
            }
            var handler = bf.getBean(RemotingHandlersRegistry.class).getServerCallHandler("%s:%s:%s".formatted(remotingId, groupId, methodId));
            var rp = handler.service(rq, ctx);
            if (serverCall.getResponseClassName() != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                try (var os = response.getOutputStream()) {
                    marshaller.marshal(rp, os, false, serializationParameters);
                    os.flush();
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } catch (Throwable t) {
            log.error("unable to process request", t);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try (var generator = jsonFactory.createGenerator(response.getOutputStream(), JsonEncoding.UTF8)) {
                generator.writeStartObject();
                var xeption = findXeption(t);
                if (xeption != null) {
                    generator.writeStringField("type", xeption.getType().name());
                    generator.writeStringField(
                            "message",
                            switch (xeption.getType()) {
                                case FOR_END_USER -> localizer.toString(xeption.getEndUserMessage());
                                case FOR_ADMIN -> localizer.toString(xeption.getAdminMessage());
                                case FOR_DEVELOPER -> xeption.getDeveloperMessage();
                            });
                } else {
                    generator.writeStringField("message", t.getCause() != null ? t.getCause().getMessage() : t.getMessage());
                }
                generator.writeStringField("stacktrace", TextUtils.getExceptionStackTrace(t.getCause() == null ? t : t.getCause()));
                generator.writeEndObject();
                generator.flush();
            }
        }
    }

    private static Xeption findXeption(Throwable e) {
        if (e instanceof Xeption xe) {
            return xe;
        }
        if (e.getCause() != null) {
            return findXeption(e.getCause());
        }
        return null;
    }

    @GetMapping(value = "createChannel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> createChannel(@RequestParam String clientId) {
        var data = new ChannelData();
        var existing = channels.putIfAbsent(clientId, data);
        var fdata = existing == null ? data : existing;
        synchronized (fdata.lock) {
            if (fdata.sink != null) {
                fdata.sink.complete();
            }
            fdata.subscriptions.clear();
            return Flux.create((emitter) -> {
                fdata.lastUpdated = LocalDateTime.now();
                fdata.sink = emitter;
                var message = new RemotingMessage();
                message.setType(RemotingMessageType.PING);
                message.setData("connection is established");
                var baos = new ByteArrayOutputStream();
                marshaller.marshal(message, baos, false, serializationParameters);
                emitter.next(ServerSentEvent.<String>builder().data(baos.toString(StandardCharsets.UTF_8)).build());
            });
        }
    }

    @GetMapping(value = "check")
    public void check() {
    }

    static class SubscriptionData {
        final Object parameter;
        volatile LocalDateTime lastMessageSent;

        SubscriptionData(Object parameter) {
            this.parameter = parameter;
        }
    }

    static class ClientCallData {
        volatile String errorMessage;
        volatile boolean completed;
        volatile Object response;
        final String methodId;
        final String groupId;
        final LocalDateTime sent;

        public ClientCallData(String groupId, String methodId, LocalDateTime sent) {
            this.methodId = methodId;
            this.groupId = groupId;
            this.sent = sent;
        }
    }

    static class ChannelData {
        volatile LocalDateTime lastUpdated = LocalDateTime.now();
        volatile FluxSink<ServerSentEvent<String>> sink;
        final Map<String, SubscriptionData> subscriptions = new ConcurrentHashMap<>();
        final Object lock = new Object();
    }
}
