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

package com.vga.platform.elsa.core.storage.database.jdbc.model;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class JdbcUtils {
    public static String getTableName(String id){
        var idx = id.lastIndexOf(".");
        return idx == -1? id.toLowerCase(): id.substring(idx+1).toLowerCase();
    }

    public static String getVersionTableName(String id){
        var idx = id.lastIndexOf(".");
        return (idx == -1? id.toLowerCase(): id.substring(idx+1).toLowerCase())+"version";
    }

    public static String getCaptionTableName(String id){
        var idx = id.lastIndexOf(".");
        return (idx == -1? id.toLowerCase(): id.substring(idx+1).toLowerCase())+"caption";
    }

    public static boolean isNull(ResultSet rs, String propertyName) throws SQLException {
        return rs.getObject(propertyName) == null;
    }

    public static boolean isNotEquals(Object obj1, Object obj2) throws SQLException {
        if(obj1 == null){
            return obj2 != null;
        }
        if(obj1 instanceof Array){
            if(!(obj2 instanceof Array)){
                return true;
            }
            var arr1 = (Object[]) ((Array) obj1).getArray();
            var arr2 = (Object[]) ((Array) obj2).getArray();
            if(arr1.length != arr2.length){
                return true;
            }
            for(int n =0; n < arr1.length; n++){
                if(!Objects.equals(arr1[n], arr2[n])){
                    return true;
                }
            }
            return false;
        }
        return !obj1.equals(obj2);
    }

    private JdbcUtils(){}

}
