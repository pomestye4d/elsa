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

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StalledChannelsCleaner {

    private Timer timer;

    private List<BaseRemotingController> controllers = new ArrayList<>();

    @Autowired(required = false)
    private void setControllers(List<BaseRemotingController> ctrs){
        controllers.addAll(ctrs);

        timer = new Timer("stalled-channels-cleaner", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                controllers.forEach(BaseRemotingController::deleteStalledChannels);
                controllers.forEach(BaseRemotingController::checkStalledClientCalls);

            }
        }, 10000L, 10000L);

    }

    @PreDestroy
    public void preDestroy(){
        timer.cancel();
    }

}
