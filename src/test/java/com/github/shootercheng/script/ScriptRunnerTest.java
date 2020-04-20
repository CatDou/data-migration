package com.github.shootercheng.script;

import com.github.shootercheng.migration.common.DbType;
import com.github.shootercheng.migration.io.Resource;
import com.github.shootercheng.migration.jdbc.MySqlScriptRunner;
import com.github.shootercheng.migration.jdbc.factory.ScriptRunnerFactory;
import com.github.shootercheng.migration.jdbc.handler.MySqlDelimiterHandler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author James
 */
public class ScriptRunnerTest {

    private static HikariDataSource hikariDataSource;

    static {
        String filePath = "db/db-mysql.properties";
        Properties properties = Resource.loadProperties(filePath);
        HikariConfig hikariConfig = new HikariConfig(properties);
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    private Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    @Test
    public void testScript() {
        String sqlPath = "sql/dump-test.sql";
        Reader reader = null;
        Connection proxyConn = null;
        try {
            proxyConn = getConnection();
            MySqlScriptRunner scriptRunner = new MySqlScriptRunner(proxyConn, new MySqlDelimiterHandler());
            reader = Resource.getResourceAsReader(sqlPath);
            scriptRunner.runScript(reader);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (proxyConn != null) {
                try {
                    proxyConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testScriptWithVar() {
        String sqlPath = "sql/rp-test.sql";
        Reader reader = null;
        Connection proxyConn = null;
        String propertiesPath = "sql/rp.properties";
        try {
            proxyConn = getConnection();
            MySqlScriptRunner scriptRunner = new MySqlScriptRunner(proxyConn, new MySqlDelimiterHandler());
            reader = Resource.getResourceAsReader(sqlPath);
            Properties properties = Resource.loadProperties(propertiesPath);
            scriptRunner.setProperties(properties);
            scriptRunner.runScript(reader);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (proxyConn != null) {
                try {
                    proxyConn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
