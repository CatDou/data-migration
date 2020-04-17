package com.github.shootercheng.pool;

import com.github.shootercheng.migration.io.Resource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author James
 */
public class HikariPoolTest {

    private HikariConfig initConfig() {
        String filePath = "db/db-mysql.properties";
        Properties properties = Resource.loadProperties(filePath);
        return new HikariConfig(properties);
    }

    @Test
    public void testHikatPool() {
        HikariConfig config = initConfig();
        try {
            HikariDataSource ds = new HikariDataSource(config);
            Connection conn = ds.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS basic_pool_test");
            stmt.executeUpdate("CREATE TABLE basic_pool_test (id int primary key auto_increment)");
            conn.close();
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
