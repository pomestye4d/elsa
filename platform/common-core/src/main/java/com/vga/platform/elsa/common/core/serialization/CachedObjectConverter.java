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

package com.vga.platform.elsa.common.core.serialization;

import com.vga.platform.elsa.common.core.model.common.*;
import com.vga.platform.elsa.common.core.model.domain.CachedObject;
import com.vga.platform.elsa.common.core.model.domain.EntityReference;
import com.vga.platform.elsa.common.core.model.domain._CachedEntityReference;
import com.vga.platform.elsa.common.core.reflection.ReflectionFactory;
import com.vga.platform.elsa.common.core.serialization.meta.BaseObjectMetadataProvider;
import com.vga.platform.elsa.common.core.serialization.meta.ObjectMetadataProvidersFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CachedObjectConverter {
    @Autowired
    private ObjectMetadataProvidersFactory metadataProvidersFactory;

    @Autowired
    private ReflectionFactory reflectionFactory;

    public<T> T toCachedObject(T object){
        if(object == null || object instanceof CachedObject){
            return object;
        }
        var processed = new HashMap<>();
        return toCachedObject(object, processed);
    }

    private<T> T toCachedObject(T object, Map<Object,Object> processed){
        if(object == null){
            return null;
        }
        if(object instanceof EntityReference<?> ref){
            var result = new _CachedEntityReference<>();
            result.setAllowChanges(true);
            result.setCaption(ref.getCaption());
            result.setId(ref.getId());
            result.setType((Class<BaseIdentity>) ref.getType());
            result.setAllowChanges(false);
            return (T) result;
        }
        var existing = processed.get(object);
        if(existing != null){
            return (T) existing;
        }
        final var className = object.getClass().getName();
        final var idx = className.lastIndexOf(".");
        final var cachedClassName = className.substring(0, idx)+"._Cached"+className.substring(idx+1);
        final var result = reflectionFactory.<T>newInstance(cachedClassName);
        processed.put(object, result);
        final var  mp = (BaseObjectMetadataProvider<Object>) metadataProvidersFactory.getProvider(className);
        ((CachedObject) result).setAllowChanges(true);
        mp.getAllProperties().forEach((prop) -> {
            var value = mp.getPropertyValue(object, prop.id());
            var cachedValue = switch (prop.type()) {
                case ENTITY_REFERENCE, ENTITY -> toCachedObject(value, processed);
                default -> value;
            };
            ((BaseIntrospectableObject) result).setValue(prop.id(), cachedValue);
        });
        mp.getAllCollections().forEach((coll) -> {
            var sourceColl = mp.getCollection(object, coll.id());
            var targetColl = ((BaseIntrospectableObject) result).getCollection(coll.id());
            if(targetColl instanceof ReadOnlyArrayList<?> rc){
                rc.setAllowChanges(true);
            }
            if(targetColl instanceof ReadOnlyHashSet<?> rc){
                rc.setAllowChanges(true);
            }
            sourceColl.forEach((obj) ->{
                var cachedValue = switch (coll.elementType()) {
                    case ENTITY_REFERENCE, ENTITY -> toCachedObject(obj, processed);
                    default -> obj;
                };
                ((Collection<Object>) targetColl).add(cachedValue);
            });
            if(targetColl instanceof ReadOnlyArrayList<?> rc){
                rc.setAllowChanges(false);
            }
            if(targetColl instanceof ReadOnlyHashSet<?> rc){
                rc.setAllowChanges(false);
            }
        });
        mp.getAllMaps().forEach((map) -> {
            var sourceMap = mp.getMap(object, map.id());
            var targetMap =(Map<Object,Object>) ((BaseIntrospectableObject) result).getMap(map.id());
            ((ReadOnlyHashMap<?,?>) targetMap).setAllowChanges(true);
            sourceMap.forEach((key, value) -> {
                var cachedValue = switch (map.valueType()) {
                    case ENTITY_REFERENCE, ENTITY -> toCachedObject(value, processed);
                    default -> value;
                };
                targetMap.put(key, cachedValue);
            });
            ((ReadOnlyHashMap<?,?>) targetMap).setAllowChanges(true);
        });
        ((CachedObject) result).setAllowChanges(false);
        return result;
    }

    public<T> T toStandardObject(T object){
        if(!(object instanceof CachedObject)){
            return object;
        }
        var processed = new HashMap<>();
        return toStandardObject(object, processed);
    }

    private<T> T toStandardObject(T object, Map<Object,Object> processed){
        if(object == null){
            return null;
        }
        if(object instanceof _CachedEntityReference<?> ref){
            var result = new EntityReference<>();
            result.setCaption(ref.getCaption());
            result.setId(ref.getId());
            if(ref.getType().getName().contains("_Cached")){
                result.setType(reflectionFactory.getClass(ref.getType().getName().replace("_Cached","")));
            } else {
                result.setType((Class<BaseIdentity>) ref.getType());
            }
            return (T) result;
        }
        var existing = processed.get(object);
        if(existing != null){
            return (T) existing;
        }
        final var className = object.getClass().getName().replace("_Cached","");
        final var result = reflectionFactory.<T>newInstance(className);
        processed.put(object, result);
        final var  mp = (BaseObjectMetadataProvider<Object>) metadataProvidersFactory.getProvider(className);
        mp.getAllProperties().forEach((prop) -> {
            var cachedValue = mp.getPropertyValue(object, prop.id());
            var standardValue = switch (prop.type()) {
                case ENTITY_REFERENCE, ENTITY -> toStandardObject(cachedValue, processed);
                default -> cachedValue;
            };
            ((BaseIntrospectableObject) result).setValue(prop.id(), standardValue);
        });
        mp.getAllCollections().forEach((coll) -> {
            var sourceColl = mp.getCollection(object, coll.id());
            var targetColl = ((BaseIntrospectableObject) result).getCollection(coll.id());
            sourceColl.forEach((obj) ->{
                var standardValue = switch (coll.elementType()) {
                    case ENTITY_REFERENCE, ENTITY -> toStandardObject(obj, processed);
                    default -> obj;
                };
                ((Collection<Object>) targetColl).add(standardValue);
            });
        });
        mp.getAllMaps().forEach((map) -> {
            var sourceMap = mp.getMap(object, map.id());
            var targetMap =(Map<Object,Object>) ((BaseIntrospectableObject) result).getMap(map.id());
            sourceMap.forEach((key, cachedValue) -> {
                var standardValue = switch (map.valueType()) {
                    case ENTITY_REFERENCE, ENTITY -> toStandardObject(cachedValue, processed);
                    default -> cachedValue;
                };
                targetMap.put(key, standardValue);
            });
        });
        return result;
    }
}
