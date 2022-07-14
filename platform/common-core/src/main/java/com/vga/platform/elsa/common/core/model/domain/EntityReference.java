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

package com.vga.platform.elsa.common.core.model.domain;

import com.vga.platform.elsa.common.core.model.common.BaseIdentity;

import java.util.Objects;

public class EntityReference<T extends BaseIdentity>{

    public static class Fields{
        public final static String id ="id";
        public final static String type ="type";
        public final static String caption ="caption";
    }
    private Class<T> type;

    private String caption;

    private long id;

    public EntityReference() {}

    public EntityReference(long id, Class<T> type, String caption) {
        this.id = id;
        this.type = type;
        this.caption = caption;
    }

    @SuppressWarnings("unchecked")
    public EntityReference(T document) {
       this(document.getId(), (Class<T>) document.getClass(), document.toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof _CachedEntityReference<?> cer){
            return Objects.equals(type.getName(), cer.getType().getName().replace("_Cached", ""))
                    && Objects.equals(id, cer.getId());
        }
        if (o == null || getClass() != o.getClass()) return false;
        EntityReference<?> that = (EntityReference<?>) o;
        return Objects.equals(type, that.type) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return caption == null? "%s(%s)".formatted(type.getName(), id): caption;
    }
}
