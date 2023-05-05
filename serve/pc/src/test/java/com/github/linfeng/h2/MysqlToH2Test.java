package com.github.linfeng.h2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MysqlToH2Test {

    private static final Logger log = LoggerFactory.getLogger(MysqlToH2Test.class);

    @Test
    public void toH2File() {
        URL file = ClassLoader.getSystemResource("sql/shiro.sql");
        StringBuilder content = new StringBuilder(500);

        try (InputStreamReader isr = new InputStreamReader(file.openStream(), StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(isr);
            content.append("SET MODE MYSQL;\n\n");

            String tableName = "";
            StringBuilder tableIndex = new StringBuilder(50);
            String line;
            String pkSplit = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("--")
                    || line.startsWith("DROP TABLE")
                    || line.startsWith("SET NAMES ")
                    || line.startsWith("SET FOREIGN_KEY_CHECKS")) {
                    // 清除注释和删除表
                    continue;
                } else if (line.startsWith("CREATE TABLE")) {
                    // 创建表
                    content.append(line).append("\n");
                    int begin = line.indexOf("`");
                    int end = line.indexOf("`", begin + 1);
                    tableName = line.substring(begin + 1, end);
                    pkSplit = "";
                } else if (line.contains("PRIMARY KEY")) {
                    // 主键
                    line = line.replace("USING BTREE", "").trim();
                    if (',' == (line.charAt(line.length() - 1))) {
                        line = line.substring(0, line.length() - 2);
                    }
                    content.append(pkSplit).append("  CONSTRAINT pk_").append(tableName).append(" ")
                        .append(line).append("\n");
                    pkSplit = ",";
                } else if (line.contains("FOREIGN KEY")) {
                    // 外键
                    line = line.substring(0, line.indexOf(" ON ")).trim();
                    if (',' == (line.charAt(line.length() - 1))) {
                        line = line.substring(0, line.length() - 2);
                    }
                    content.append(pkSplit).append(line).append("\n");
                    pkSplit = ",";
                } else if (line.contains(" INDEX ")) {
                    // 索引
                    line = line.replace("(", " ON `" + tableName + "`(")
                        .replace("USING BTREE", "").trim();
                    if (',' == (line.charAt(line.length() - 1))) {
                        line = line.substring(0, line.length() - 2);
                    }
                    tableIndex.append("CREATE ").append(line).append(";").append("\n");
                } else if (line.startsWith(")")
                    // 创建表结束
                    && line.contains("ENGINE =")) {
                    content.append(");").append("\n").append(tableIndex);
                    tableIndex.delete(0, tableIndex.length());
                } else if (line.contains("CHARACTER SET utf8 COLLATE utf8_general_ci")) {
                    // 清除字段指定的编码
                    line = line.replace("CHARACTER SET utf8 COLLATE utf8_general_ci", "")
                        .replace(" UNSIGNED ", " ");
                    content.append(line).append("\n");
                } else if (line.startsWith("INSERT INTO ")) {
                    content.append(line).append("\n");
                } else {
                    // 其他的保留
                    line = line.replace(" UNSIGNED ", " ");
                    content.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String saveFileName = backFileName(file.getFile());
        File saveFile = new File(saveFileName);
        try {
            FileUtils.writeStringToFile(saveFile, content.toString(), StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(content.toString());
    }

    private String backFileName(String filename) {
        int begin = filename.lastIndexOf(".");
        String prePath = filename.substring(0, begin);
        String fileExt = filename.substring(begin);
        return prePath + "-back" + fileExt;
    }
}
