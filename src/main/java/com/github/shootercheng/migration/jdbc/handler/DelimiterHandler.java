package com.github.shootercheng.migration.jdbc.handler;


import com.github.shootercheng.migration.jdbc.ScriptRunner;

/**
 * @author James
 */
public interface DelimiterHandler {
  boolean resetDelimiter(ScriptRunner scriptRunner, String line);
}
