package com.ubtechinc.remote.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//获取到db.properties文件中的数据库信息
/**
 *新建util包，然后在里面创建JdbcUtil类，利用反射获取db.properties文件信息，最后返回数据库连接
 * 把这个类理解成一个读取文件，返回数据库链接的东西就好了
 *<a href="https://www.cnblogs.com/dadian/p/11938707.html">...</a>
 *
 * @author MacBook Air
 * @date 2025/8/14 5:11
 */
public class JdbcUtils {
    //私有变量
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    //静态块
    static{
        try{


            //1.新建属性集对象
            Properties properties = new Properties();
            //2通过反射，新建字符输入流，读取db.properties文件
            InputStream input = com.ubtechinc.local.utils.JdbcUtils.class.getClassLoader().getResourceAsStream("db.properties.remote");
            //3.将输入流中读取到的属性，加载到properties属性集对象中
            properties.load(input);
            //4.根据键，获取properties中对应的值
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //返回数据库连接
    public static Connection getConnection(){
        try{
            //注册数据库的驱动
            Class.forName(driver);
            //获取数据库连接（里面内容依次是：主机名和端口、用户名、密码）
            Connection connection = DriverManager.getConnection(url,user,password);
            //返回数据库连接
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}