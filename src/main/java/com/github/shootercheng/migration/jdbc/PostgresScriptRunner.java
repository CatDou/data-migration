package com.github.shootercheng.migration.jdbc;

import com.github.shootercheng.migration.common.MigrationConstant;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author James
 */
public class PostgresScriptRunner extends BaseScriptRunner {

    private String delimiter = DEFAULT_DELIMITER;

    private String DEFAULT_DELIMITER_MARK = "AS";

    private String delimiterMark = DEFAULT_DELIMITER_MARK;

    private boolean markStatus = false;

    public PostgresScriptRunner(Connection connection) {
        super.setConnection(connection);
    }

    public PostgresScriptRunner(Connection connection, String delimiterMark) {
        super.setConnection(connection);
        this.delimiterMark = delimiterMark;
    }

    public String getDelimiterMark() {
        return delimiterMark;
    }

    public void setDelimiterMark(String delimiterMark) {
        this.delimiterMark = delimiterMark;
    }

    @Override
    public void handleLine(StringBuilder command, String line) throws SQLException {
        String trimedLine = line.trim();
        if (trimedLine.length() == 0) {
            // ignore
        } else if (lineIsComment(trimedLine)) {
            println(trimedLine);
        } else if (trimedLine.startsWith(delimiterMark)) {
            delimiter = trimedLine.substring(delimiterMark.length()).trim();
            command.append(trimedLine);
            command.append(MigrationConstant.LINE_SEPARATOR);
            markStatus = true;
        } else if (trimedLine.startsWith(delimiter)) {
            command.append(trimedLine);
            command.append(MigrationConstant.LINE_SEPARATOR);
            delimiter = trimedLine.substring(delimiter.length()).trim();
            markStatus = false;
        } else {
            command.append(trimedLine);
            command.append(MigrationConstant.LINE_SEPARATOR);
        }
        if (trimedLine.endsWith(delimiter) && !markStatus) {
            println(trimedLine);
            executeStatement(command.toString());
            command.setLength(0);
        }
    }
}
