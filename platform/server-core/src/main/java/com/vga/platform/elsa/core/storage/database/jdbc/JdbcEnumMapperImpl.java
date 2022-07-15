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

import com.vga.platform.elsa.common.core.l10n.SupportedLocalesProvider;
import com.vga.platform.elsa.common.core.model.common.EnumMapper;
import com.vga.platform.elsa.common.core.model.common.Xeption;
import com.vga.platform.elsa.common.core.utils.LocaleUtils;
import com.vga.platform.elsa.common.meta.domain.DomainMetaRegistry;
import com.vga.platform.elsa.core.storage.database.jdbc.adapter.JdbcDialect;
import com.vga.platform.elsa.core.storage.database.jdbc.model.JdbcUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcEnumMapperImpl implements EnumMapper {
    private final Map<String,Map<String, Integer>>  name2Id = new HashMap<>();
    private final Map<String, Map<Integer,String>> id2name = new HashMap<>();

    public JdbcEnumMapperImpl(DomainMetaRegistry metaRegistry, JdbcTemplate template, SupportedLocalesProvider supportedLocalesProvider, JdbcDialect dialect) {
        metaRegistry.getEnums().values().forEach(it ->{
            var res = template.queryForList("select id, enumconstant, %s from %s".formatted(StringUtils.join(supportedLocalesProvider.getSupportedLocales()
                    .stream().map(loc -> "%sname".formatted(loc.getLanguage())).toList(), ", "), JdbcUtils.getTableName(it.getId())));
            var map = new HashMap<String, Integer>();
            for(var enumItem: it.getItems().values()){
                var row = res.stream().filter(it2 ->enumItem.getId().equals(it2.get("enumconstant"))).findFirst().orElse(null);
                if(row == null){
                    Integer id = null;
                    boolean found = false;
                    while (id == null || found) {
                        id = template.query(dialect.getSequenceNextValueSql("intid"), rs -> {
                            rs.next();
                            return rs.getInt(1);
                        });
                        var id2 = id;
                        found = res.stream().anyMatch(it2 -> id2 != null && id2.equals(it2.get("id")));
                    }
                    template.execute("insert into %s(id, enumconstant, %s) values (%s, '%s', %s)".formatted(
                            JdbcUtils.getTableName(it.getId()),
                            StringUtils.join(supportedLocalesProvider.getSupportedLocales().stream().map(loc -> "%sName".formatted(loc.getLanguage())).toList(), ", "),
                            id,enumItem.getId(),
                            StringUtils.join(supportedLocalesProvider.getSupportedLocales().stream()
                                    .map(loc -> "'%s'".formatted(LocaleUtils.getLocalizedName(enumItem.getDisplayNames(), loc, enumItem.getId()))).toList(), ", ")
                    ));
                    map.put(enumItem.getId(), id);
                } else {
                    var differs = supportedLocalesProvider.getSupportedLocales().stream().anyMatch(loc ->!LocaleUtils.getLocalizedName(enumItem.getDisplayNames(), loc, enumItem.getId()).equals(row.get("%sName".formatted(loc.getLanguage()))));
                    if(differs){
                        template.execute("update %s set %s where enumconstant='%s'".formatted(
                                JdbcUtils.getTableName(it.getId()),
                                StringUtils.join(supportedLocalesProvider.getSupportedLocales().stream()
                                        .map(loc -> "%sName='%s'".formatted(loc.getLanguage(), LocaleUtils.getLocalizedName(enumItem.getDisplayNames(), loc, enumItem.getId()))).toList(), ", "),
                                enumItem.getId()));
                    }
                    map.put(enumItem.getId(), (Integer) row.get("id"));
                }

            }
            var nid = new LinkedHashMap<String, Integer>();
            name2Id.put(it.getId(), nid);
            var idn = new LinkedHashMap<Integer, String>();
            id2name.put(it.getId(), idn);
            map.forEach((name, id) ->{
                nid.put(name, id);
                idn.put(id, name);
            });
        });
    }

    @Override
    public int getId(Enum<?> value) {
        var className = value.getClass().getName();
        if(!name2Id.containsKey(className)){
            return value.ordinal();
        }
        var result = name2Id.get(className).get(value.name());
        if(result == null){
            throw Xeption.forDeveloper("unknown value %s of enum %s".formatted(value.name(), className));
        }
        return result;
    }

    @Override
    public String getName(int id, Class<Enum<?>> cls) {
        var className = cls.getName();
        if(!id2name.containsKey(className)){
            for(var item: cls.getEnumConstants()){
                if(item.ordinal() == id){
                    return item.name();
                }
            }
            throw Xeption.forDeveloper("unknown id %s of enum %s".formatted(id, className));
        }
        var result = id2name.get(className).get(id);
        if(result == null){
            throw Xeption.forDeveloper("unknown id %s of enum %s".formatted(id, className));
        }
        return result;
    }
}
