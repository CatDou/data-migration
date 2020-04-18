package com.github.shootercheng.migration.jdbc.handler;

import com.github.shootercheng.migration.jdbc.ScriptRunner;

/**
 * @author James
 */
public interface NonCommentHandler extends DelimiterHandler {
    String DELIMITER_NAME = "DELIMITER";

    default boolean lineStartWithDelimiter(String trimmedLine) {
        if (trimmedLine.length() <= DELIMITER_NAME.length()) {
            return false;
        }
        return DELIMITER_NAME.equalsIgnoreCase(trimmedLine.substring(0, DELIMITER_NAME.length()));
    }

    @Override
    default boolean resetDelimiter(ScriptRunner scriptRunner, String trimmedLine) {
        if (lineStartWithDelimiter(trimmedLine)) {
            String delimiter = trimmedLine.substring(DELIMITER_NAME.length()).trim();
            if (delimiter.length() > 0) {
                scriptRunner.setDelimiter(delimiter);
                return true;
            }
        }
        return false;
    }
}
