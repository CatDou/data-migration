package com.github.shootercheng.migration.jdbc.handler;

/**
 * @author James
 */
public interface NonCommentHandler extends DelimiterHandler {

    default boolean lineStartWithDelimiter(String trimmedLine, String delimiterName) {
        if (trimmedLine.length() <= delimiterName.length()) {
            return false;
        }
        return delimiterName.equalsIgnoreCase(trimmedLine.substring(0, delimiterName.length()));
    }
}
