package com.github.shootercheng.migration.io;

import com.github.shootercheng.migration.common.MigrationConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @author James
 */
public class SqlBufferReader extends BufferedReader {
    private Properties properties;

    public SqlBufferReader(Reader in, int sz) {
        super(in, sz);
    }

    public SqlBufferReader(Reader in) {
        super(in);
    }

    public SqlBufferReader(Reader in, Properties properties) {
        super(in);
        this.properties = properties;
    }

    @Override
    public String readLine() throws IOException {
        String readLine = super.readLine();
        if (readLine == null) {
            return readLine;
        }
        if (isLineVarMarked(readLine)) {
            readLine = readLine.substring(MigrationConstant.VAR_START_MARK.length());
            readLine = replaceVariable(readLine);
        }
        return readLine;
    }


    private boolean isLineVarMarked(String readLine) {
        return readLine.startsWith(MigrationConstant.VAR_START_MARK);
    }

    private String replaceVariable(String readLine) {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int leftIndex = readLine.indexOf("{");
            int rightIndex = readLine.indexOf("}");
            if (leftIndex == -1 || rightIndex == -1) {
                stringBuilder.append(readLine);
                break;
            }
            stringBuilder.append(readLine.substring(0, leftIndex));
            String variable = readLine.substring(leftIndex + 1, rightIndex);
            String value = properties.getProperty(variable);
            stringBuilder.append(value);
            readLine = readLine.substring(rightIndex + 1);
        }
        return stringBuilder.toString();
    }
}
