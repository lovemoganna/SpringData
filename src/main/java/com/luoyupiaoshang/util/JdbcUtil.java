package com.luoyupiaoshang.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author: ligang
 * date: 2018/1/22
 * time: 15:46
 *
 * JDBC工具类
 * 1.获取Connection
 * 2.释放资源
 */
public class JdbcUtil {
    /**
     * 获取Connection
     * @return 所获得的JDBC的Connection
     */
    public static Connection getConnection() throws Exception {

        /**
         * 建议配置型的东西写到配置文件当中.
         */
        /*String url="jdbc:mysql://192.168.25.131:3306/spring_data";
        String user="root";
        String password="root";
        String driverClass="com.mysql.jdbc.Driver";*/

        //采用Properties载入输入流
        InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(is);

        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String driverClass = properties.getProperty("jdbc.driverClass");

        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }

    /**
     * 释放DB相关资源
     * @param resultSet
     * @param statement
     * @param connection
     */
    public static void release(ResultSet resultSet, PreparedStatement statement, Connection connection){
        if (resultSet !=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement !=null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection !=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
