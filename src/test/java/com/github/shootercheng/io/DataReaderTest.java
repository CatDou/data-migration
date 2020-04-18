package com.github.shootercheng.io;

import com.github.shootercheng.migration.io.Resource;
import com.github.shootercheng.migration.io.StreamUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author James
 */
public class DataReaderTest {

    @Test
    public void testSqlReader() throws IOException {
        String filePath = "sql/rp-test.sql";
        InputStream inputStream = Resource.getResourceAsStream(filePath);
        byte[] bytes = StreamUtil.copyToByteArray(inputStream);
        InputStream inputStream1 = new ByteArrayInputStream(bytes);
        InputStream inputStream2 = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream1));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
        String line2;
        while ((line2 = bufferedReader2.readLine()) != null) {
            System.out.println(line2);
        }
    }
}
