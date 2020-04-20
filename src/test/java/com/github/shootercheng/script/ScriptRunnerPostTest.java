package com.github.shootercheng.script;

import com.github.shootercheng.migration.common.DbType;
import com.github.shootercheng.migration.common.MigrationConstant;
import com.github.shootercheng.migration.io.Resource;
import com.github.shootercheng.migration.jdbc.BaseScriptRunner;
import com.github.shootercheng.migration.jdbc.MySqlScriptRunner;
import com.github.shootercheng.migration.jdbc.PostgresScriptRunner;
import com.github.shootercheng.migration.jdbc.factory.ScriptRunnerFactory;
import com.github.shootercheng.migration.jdbc.handler.MySqlDelimiterHandler;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author James
 */
public class ScriptRunnerPostTest {

    private static HikariDataSource hikariDataSource;

    static {
        String filePath = "db/db-postgres.properties";
        Properties properties = Resource.loadProperties(filePath);
        HikariConfig hikariConfig = new HikariConfig(properties);
        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    private Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    @Test
    public void testPostSql() {
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DROP FUNCTION IF EXISTS public.add_1");
//            stmt.executeUpdate("CREATE FUNCTION public.add_1(a integer, b integer) RETURNS integer\n" +
//                    "    LANGUAGE sql\n" +
//                    "    AS $_$\n" +
//                    "    SELECT $1+$2;\n" +
//                    "$_$;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPostgresSql() throws IOException {
        String sqlPath = "sql/postgres/postgres.sql";
        String delimiterMark = "AS";
        String delimiter = ";";
        boolean markStatus = false;
        InputStream inputStream = Resource.getResourceAsStream(sqlPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder commond = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            String trimedLine = line.trim();
            if (trimedLine.length() == 0) {
                continue;
            } else if (trimedLine.startsWith("--")) {
//                System.out.println(trimedLine);
            } else if (trimedLine.startsWith(delimiterMark)) {
                delimiter = trimedLine.substring(delimiterMark.length()).trim();
                commond.append(trimedLine);
                commond.append(MigrationConstant.LINE_SEPARATOR);
                markStatus = true;
            } else if (trimedLine.startsWith(delimiter)) {
                commond.append(trimedLine);
                commond.append(MigrationConstant.LINE_SEPARATOR);
                delimiter = trimedLine.substring(delimiter.length()).trim();
                markStatus = false;
            } else {
                commond.append(trimedLine);
                commond.append(MigrationConstant.LINE_SEPARATOR);
            }
            if (trimedLine.endsWith(delimiter) && !markStatus) {
                System.out.println("----------------------------------------------");
                System.out.println(commond);
                commond.setLength(0);
            }
        }
    }

    @Test
    public void testScript() {
        String sqlPath = "sql/postgres/postgres.sql";
        Reader reader = null;
        Connection proxyConn = null;
        try {
            proxyConn = getConnection();
            BaseScriptRunner scriptRunner = ScriptRunnerFactory.createScriptRunner(DbType.POSTGRESQL, proxyConn);
            reader = Resource.getResourceAsReader(sqlPath);
            scriptRunner.runScript(reader);
            scriptRunner.setStopOnError(true);
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
