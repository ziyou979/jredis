package org.zy.util;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class PropertiesUtil {

    public static Properties getProperties() {
        return getProperties("/redis.conf");
    }

    public static String getNodeAddress() {
        String address = getProperties().getProperty("ip");
        if (StringUtil.isBlank(address)) {
            return "127.0.0.1";
        }
        return address;
    }

    public static boolean getTcpKeepAlive() {
        String tcpKeepalive = getProperties().getProperty("tcp_keepalive");
        return BooleanUtil.parseBoolean(tcpKeepalive);
    }

    public static int getNodePort() {
        int port;
        try {
            String strPort = getProperties().getProperty("port");
            port = Integer.parseInt(strPort);
        } catch (Exception e) {
            return 6379;
        }
        if (port <= 0 || port > 60000) {
            return 6379;
        }
        return port;
    }

    public static int getDatabases() {
        String database = getProperties().getProperty("databases");
        try {
            return Integer.parseInt(database);
        } catch (NumberFormatException e) {
            return 16;
        }
    }

    private static Properties getProperties(String propertiesName) {
        Properties prop = new Properties();
        try (InputStream input = PropertiesUtil.class.getResourceAsStream(propertiesName)) {
            prop.load(input);
        } catch (IOException e) {
            log.error("读取配置文件失败", e);
        }
        return prop;
    }
}
