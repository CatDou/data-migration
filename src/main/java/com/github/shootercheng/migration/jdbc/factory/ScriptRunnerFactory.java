package com.github.shootercheng.migration.jdbc.factory;

import com.github.shootercheng.migration.common.DbType;
import com.github.shootercheng.migration.jdbc.BaseScriptRunner;
import com.github.shootercheng.migration.jdbc.MySqlScriptRunner;
import com.github.shootercheng.migration.jdbc.PostgresScriptRunner;

import java.sql.Connection;

/**
 * @author James
 */
public class ScriptRunnerFactory {

    public static BaseScriptRunner createScriptRunner(DbType dbType, Connection connection) {
        BaseScriptRunner baseScriptRunner = null;
        switch (dbType) {
            case MYSQL:
                baseScriptRunner = new MySqlScriptRunner(connection);
                break;
            case POSTGRESQL:
                baseScriptRunner = new PostgresScriptRunner(connection);
                break;
            case ORACLE:
                break;
            default:
                break;
        }
        if (baseScriptRunner == null) {
            throw new IllegalArgumentException("script runner not init " + dbType);
        }
        return baseScriptRunner;
    }
}
