package com.github.shootercheng.str;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author James
 */
public class StringTest {

    private static final Pattern pattern = Pattern.compile("(\\{(.*?)\\})");

    private static String replaceSql(String sql, Properties properties) {
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String match = matcher.group(1);
            String var = matcher.group(2);
            System.out.println(match);
            System.out.println(var);
            sql = sql.replace(match, properties.getProperty(var));
        }
        return sql;
    }

    private static String appendSql(String sql, Properties properties) {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int leftIndex = sql.indexOf("{");
            int rightIndex = sql.indexOf("}");
            if (leftIndex == -1 && rightIndex == -1) {
                stringBuilder.append(sql);
                break;
            }
            stringBuilder.append(sql.substring(0, leftIndex));
            String var = sql.substring(leftIndex + 1, rightIndex);
            String value = properties.getProperty(var);
            stringBuilder.append(value);
            sql = sql.substring(rightIndex + 1);
        }
        return stringBuilder.toString();
    }

    @Test
    public void testRpSql() {
        String sql = "@@ DROP DATABASE IF EXISTS {db_name} {test} {db_name};";
        sql = sql.substring(2).trim();
        Properties properties = new Properties();
        properties.put("db_name", "dbname");
        properties.put("test","111");
        Assert.assertEquals("DROP DATABASE IF EXISTS dbname 111 dbname;", appendSql(sql, properties));
        Assert.assertEquals("DROP DATABASE IF EXISTS dbname 111 dbname;", replaceSql(sql, properties));
    }
}
