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

package com.vga.platform.elsa.common.core.search;

import com.vga.platform.elsa.common.core.utils.TextUtils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SearchQuery extends BaseQuery{
    private final Map<String, SortOrder> orders = new LinkedHashMap<>();
    private final Set<String> preferredFields = new LinkedHashSet<>();
    private int limit = 200;
    private int offset = 0;

    public Map<String, SortOrder> getOrders() {
        return orders;
    }

    public Set<String> getPreferredFields() {
        return preferredFields;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        var buf = new StringBuilder("SELECT %s".formatted(preferredFields.isEmpty()? "*" : TextUtils.join(preferredFields, ", ")));
        if(!getCriterions().isEmpty()){
            buf.append(" WHERE %s".formatted(TextUtils.join(getCriterions(), " AND ")));
        }
        if (limit > 0) {
            buf.append(" LIMIT %s OFFSET %s".formatted(limit, offset));
        }
        return buf.toString();
    }
}
