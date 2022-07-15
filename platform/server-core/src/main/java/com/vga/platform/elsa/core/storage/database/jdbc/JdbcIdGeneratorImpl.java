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

package com.vga.platform.elsa.core.storage.database.jdbc;

import com.vga.platform.elsa.common.core.model.common.IdGenerator;
import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDialect;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("ClassCanBeRecord")
public class JdbcIdGeneratorImpl implements IdGenerator {

    private final JdbcTemplate template;

    private final JdbcDialect dialect;

    public JdbcIdGeneratorImpl(JdbcTemplate template, JdbcDialect dialect) {
        this.template = template;
        this.dialect = dialect;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long nextId() {
        var found = true;
        var id = 0L;
        while (found) {
            id = template.query(dialect.getSequenceNextValueSql("longid"), rs -> {
                rs.next();
                return rs.getLong(1);
            });
            found = !template.queryForList("select id from identifiers where id=%s".formatted(id)).isEmpty();
            if(!found) {
                try {
                    template.execute("insert into identifiers(id) values(%s)".formatted(id));
                } catch (Exception e) {
                 found = true;
                }
            }
        }
        return id;
    }

    @Override
    public void ensureIdRegistered(long id) {
        var found = false;
        while (!found){
            found = !template.queryForList("select id from identifiers where id=%s".formatted(id)).isEmpty();
            if(!found){
                try {
                    template.execute("insert into identifiers(id) values(%s)".formatted(id));
                } catch (Exception e) {
                    found = true;
                }
            }
        }
    }
}
