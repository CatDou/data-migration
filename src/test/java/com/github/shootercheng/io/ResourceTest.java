package com.github.shootercheng.io;

import com.github.shootercheng.migration.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author James
 */
public class ResourceTest {

    @Test
    public void testLoadStream() {
        String filePath = "db/db-mysql.properties";
        InputStream inputStream = Resource.getResourceAsStream(filePath);
        Assert.assertTrue(inputStream != null);
    }

    @Test
    public void testLoadProperties() {
        String filePath = "db/db-mysql.properties";
        Properties properties = Resource.loadProperties(filePath);
        Assert.assertEquals("chengdu", properties.getProperty("username"));
    }
}
