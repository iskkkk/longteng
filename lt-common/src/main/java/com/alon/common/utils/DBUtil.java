package com.alon.common.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @ClassName DBUtil
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/17 16:55
 * @Version 1.0
 **/
public class DBUtil {
    private static Properties props;

    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.yml");
            props = new Properties();
            props.load(in);
            in.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws Exception{
        String url = props.getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        String driver = props.getProperty("spring.datasource.driver-class-name");
        Class.forName(driver);
        return DriverManager.getConnection(url,username, password);
    }
}
