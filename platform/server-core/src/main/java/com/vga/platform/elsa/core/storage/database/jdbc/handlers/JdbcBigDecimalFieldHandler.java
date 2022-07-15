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
import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDialect;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcFieldType;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.Map;

public class JdbcBigDecimalFieldHandler extends BaseJdbcSingleFieldHandler {

    public JdbcBigDecimalFieldHandler(String fieldName, boolean indexed) {
        super(fieldName, indexed, JdbcFieldType.BIG_DECIMAL);
    }
    @Override
    public Object getModelValue(ResultSet rs, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory, JdbcDialect dialect) throws Exception {
        return JdbcUtils.isNull(rs, fieldName)? null : rs.getBigDecimal(fieldName);
    }

    @Override
    public Map<String, Pair<Object, JdbcFieldType>> getSqlValues(Object value, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory) throws Exception {
        return Collections.singletonMap(fieldName, Pair.of(value, JdbcFieldType.BIG_DECIMAL));
    }

    @Override
    public Pair<Object, JdbcFieldType> getSqlQueryValue(Object value, EnumMapper enumMapper, ClassMapper classMapper, ReflectionFactory factory) throws Exception {
        return Pair.of(value, JdbcFieldType.BIG_DECIMAL);
    }

}
