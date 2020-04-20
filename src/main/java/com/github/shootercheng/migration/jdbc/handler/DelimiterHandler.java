package com.github.shootercheng.migration.jdbc.handler;


import com.github.shootercheng.migration.jdbc.MySqlScriptRunner;

/**
 * @author James
 */
public interface DelimiterHandler {
    boolean resetDelimiter(MySqlScriptRunner scriptRunner, String line, StringBuilder command);
}
