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

package com.vga.platform.elsa.core.storage.database.jdbc.handlers;

import com.vga.platform.elsa.common.core.model.common.ClassMapper;
import com.vga.platform.elsa.common.core.model.common.EnumMapper;
import com.vga.platform.elsa.common.core.reflection.ReflectionFactory;
import com.vga.platform.elsa.common.core.utils.Pair;
import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDialect;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcFieldType;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcIndexDescription;

import java.sql.ResultSet;
import java.util.Map;

public interface JdbcFieldHandler {
    Map<String, JdbcFieldType> getColumns();
    Map<String, JdbcIndexDescription> getIndexes(String tableName);
    Object getModelValue(ResultSet rs, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory, JdbcDialect dialiect) throws Exception;
    Map<String, Pair<Object, JdbcFieldType>> getSqlValues(Object value, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory) throws Exception;
    Pair<Object, JdbcFieldType> getSqlQueryValue(Object value, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory) throws Exception;
}
