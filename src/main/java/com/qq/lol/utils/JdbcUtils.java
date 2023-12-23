package com.qq.lol.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Auther: null
 * @Date: 2023/12/6 - 12 - 06 - 16:46
 * @Description: 连接池工具类
 * @version: 1.0
 */
public class JdbcUtils {
    // 连接池对象
    private static DataSource dataSource;

    /**
     * 私有构造
     */
    private JdbcUtils() { }

    static {
        //数据源配置
        Properties prop = new Properties();
        //读取配置文件
        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            System.out.println("druid连接池初始化失败...");
            e.printStackTrace();
        }

        try {
            //返回的是DataSource
            dataSource = DruidDataSourceFactory.createDataSource(prop);
            System.out.println(StandardOutTime.getCurrentTime() + " ------ 初始化数据库连接池 ------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 提供释放资源的方法（conn对象不是销毁，而是归还到连接池）
    public static void release(ResultSet resultSet, Statement statement, Connection connection) {
        // 关闭ResultSet
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //手动 null，使垃圾回收机制更早回收，符合：晚创建早释放
        resultSet = null;

        // 关闭 Statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        statement = null;

        // 关闭Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        connection = null;
    }

    // 方法重载
    public static void release(Statement statement, Connection connection) {
        release(null, statement, connection);
    }

}
