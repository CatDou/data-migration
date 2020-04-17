package com.github.shootercheng.migration.io;

import com.github.shootercheng.migration.exception.ResourceException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author James
 */
public class Resource {

    public static final List<ClassLoader> CLASS_LOADERS = Arrays.asList(
                ClassLoader.getSystemClassLoader(),
                Thread.currentThread().getContextClassLoader(),
                Resource.class.getClassLoader()
    );

    public static InputStream getResourceAsStream(String resourcePath, ClassLoader classLoader) {
        InputStream inputStream = null;
        if (classLoader != null) {
           inputStream = classLoader.getResourceAsStream(resourcePath);
        }
        if (inputStream == null) {
            for (ClassLoader loader : CLASS_LOADERS) {
                inputStream = loader.getResourceAsStream(resourcePath);
                if (inputStream != null) {
                    break;
                }
            }
        }
        if (inputStream == null) {
            throw new ResourceException("can't find resource file " + resourcePath);
        }
        return inputStream;
    }

    public static InputStream getResourceAsStream(String filePath) {
        return getResourceAsStream(filePath, null);
    }

    public static Properties loadProperties(String resourcePath) {
        InputStream inputStream = getResourceAsStream(resourcePath);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ResourceException("load properties error, resource path " + resourcePath);
        }
        return properties;
    }

    public static Reader getResourceAsReader(String filePath, Charset charset) {
        InputStream inputStream = getResourceAsStream(filePath);
        return new InputStreamReader(inputStream, charset);
    }

    public static Reader getResourceAsReader(String filePath) {
        InputStream inputStream = getResourceAsStream(filePath);
        return new InputStreamReader(inputStream, Charset.defaultCharset());
    }
}
