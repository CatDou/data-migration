package com.github.shootercheng.migration.jdbc.handler;

import com.github.shootercheng.migration.jdbc.MySqlScriptRunner;

/**
 * @author James
 */
public class MySqlDelimiterHandler implements NonCommentHandler {
    private String delimiterName = "DELIMITER";

    @Override
    public boolean resetDelimiter(MySqlScriptRunner scriptRunner, String trimmedLine, StringBuilder commond) {
        if (lineStartWithDelimiter(trimmedLine, delimiterName)) {
            String delimiter = trimmedLine.substring(delimiterName.length()).trim();
            if (delimiter.length() > 0) {
                scriptRunner.setDelimiter(delimiter);
                return true;
            }
        }
        return false;
    }
}
