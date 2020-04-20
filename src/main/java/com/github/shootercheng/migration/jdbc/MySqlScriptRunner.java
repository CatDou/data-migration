/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this io except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.shootercheng.migration.jdbc;

import com.github.shootercheng.migration.jdbc.handler.DefaultDelimiterHandler;
import com.github.shootercheng.migration.jdbc.handler.DelimiterHandler;

import java.sql.Connection;
import java.sql.SQLException;

import static com.github.shootercheng.migration.common.MigrationConstant.LINE_SEPARATOR;

public class MySqlScriptRunner extends BaseScriptRunner {

    private DelimiterHandler delimiterHandler = new DefaultDelimiterHandler();

    public void setDelimiterHandler(DelimiterHandler delimiterHandler) {
        this.delimiterHandler = delimiterHandler;
    }

    public MySqlScriptRunner(Connection connection) {
        super.setConnection(connection);
    }

    public MySqlScriptRunner(Connection connection, DelimiterHandler delimiterHandler) {
        super.setConnection(connection);
        this.delimiterHandler = delimiterHandler;
    }

    @Override
    public void handleLine(StringBuilder command, String line) throws SQLException {
        String trimmedLine = line.trim();
        if (delimiterHandler.resetDelimiter(this, trimmedLine, command)) {
            println(trimmedLine);
        } else if (lineIsComment(trimmedLine)) {
            println(trimmedLine);
        } else if (commandReadyToExecute(trimmedLine)) {
            command.append(line, 0, line.lastIndexOf(super.getDelimiter()));
            command.append(LINE_SEPARATOR);
            println(command);
            executeStatement(command.toString());
            command.setLength(0);
        } else if (trimmedLine.length() > 0) {
            command.append(line);
            command.append(LINE_SEPARATOR);
        }
    }
}
