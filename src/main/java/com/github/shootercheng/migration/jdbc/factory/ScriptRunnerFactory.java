package com.github.shootercheng.migration.jdbc.factory;

import com.github.shootercheng.migration.common.DbType;
import com.github.shootercheng.migration.jdbc.ScriptRunner;
import com.github.shootercheng.migration.jdbc.handler.MySqlDelimiterHandler;

import java.sql.Connection;

/**
 * @author James
 */
public class ScriptRunnerFactory {

    public static ScriptRunner createScriptRunner(DbType dbType, Connection connection) {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        switch (dbType) {
            case MYSQL:
                scriptRunner.setDelimiterHandler(new MySqlDelimiterHandler());
                break;
            case POSTGRESQL:
                break;
            case ORACLE:
                break;
            default:
                break;
        }
        return scriptRunner;
    }
}
