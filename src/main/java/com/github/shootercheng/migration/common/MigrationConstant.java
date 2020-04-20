package com.github.shootercheng.migration.common;

import java.util.regex.Pattern;

/**
 * @author James
 */
public class MigrationConstant {
    /**
     * line variable marked
     */
    public static final String VAR_START_MARK = "@@";

    /**
     * {} sql variable
     */
    public static final Pattern SQL_MARK = Pattern.compile("\\{(.*?)\\}");

    public static final String LINE_SEPARATOR = System.lineSeparator();
}
