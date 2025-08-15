package com.ubtechinc.concept.linksql;

import com.ubtechinc.concept.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 调用JdbcUtil类返回的数据库连接操作数据库，简单来说就是具体操作是在这个类里面
 *
 * @author MacBook Air
 * @date 2025/8/14 5:48
 */

/**
 * 之后可以尝试一下配batis
 * https://blog.csdn.net/bigblack12321/article/details/129406483
 */

public class Linksql {

    //throws ClassNotFoundException, SQLException 表示方法可能会抛出两种异常：
    //ClassNotFoundException：找不到 JDBC 驱动类时会抛出。
    //SQLException：执行 SQL 相关操作出错时会抛出。
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        //这是try-resources自动关闭流的写法
        /*
          String sql = "insert into stu(id,name,age) values(?,?,?)";

                  try (Connection connection = JdbcUtils.getConnection();
                       PreparedStatement statement = connection.prepareStatement(sql)) {

                      // 给占位符赋值
                      statement.setInt(1, 14);
                      statement.setString(2, "李四");
                      statement.setInt(3, 16);

                      // 执行 SQL 并获取受影响行数
                      int rowsAffected = statement.executeUpdate();
                      System.out.println("插入成功，受影响行数：" + rowsAffected);

                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
         */


        //获取utils那个类返回的数据库连接
        Connection connection = JdbcUtils.getConnection();


        //建表 SQL（如果表不存在则创建）
        String createTableSql = """
                create table if not exists stu (
                                     id INT PRIMARY KEY,
                                     name VARCHAR(50),
                                     age INT
                                 )
                """;
        try (Statement createStatement = connection.createStatement()) {
            createStatement.execute(createTableSql);
            System.out.println("表 stu 已创建（如果之前不存在）");
        }


        //需要执行的sql语句
        //定义一个 SQL 插入语句，往 stu 表中插入 id、name 和 age 三个字段。
        //这里用 ? 是 占位符，表示后面要通过代码动态赋值。
        //这是在已经创建的表里插入东西，不是创建表
        var sql = "insert into stu(id,name,age) values(?,?,?)";
        //获取预处理对象，并给参数赋值
        //获取一个 PreparedStatement 对象，用来执行 SQL。
        // •  注意：这里用 prepareCall 其实是调用 存储过程 时更常用，执行普通 SQL 一般用 prepareStatement
        //依次给 SQL 语句里的 ? 占位符赋值：
        // 1.  第一个参数 1 → id = 14
        // 2.  第二个参数 2 → name = "李四"
        // 3.  第三个参数 3 → age = 16


        PreparedStatement statement = connection.prepareCall(sql);
        statement.setInt(1, 14);
        statement.setString(2, "李四");
        statement.setInt(3, 16);
        //调用 executeUpdate() 方法执行 SQL。
        // •  返回值 i 表示受影响的行数（比如插入一条数据就是 1）。
        int i = statement.executeUpdate();
        System.out.println(i);
        //传上去进行完操作之后就关闭jdbc连接
        statement.close();
        connection.close();


        /**
         * // 1. 获取数据库连接
         *         Connection connection = JdbcUtils.getConnection();
         *
         *         // 2. 建表 SQL（如果表不存在则创建）
         *         String createTableSql = """
         *             CREATE TABLE IF NOT EXISTS stu (
         *                 id INT PRIMARY KEY,
         *                 name VARCHAR(50),
         *                 age INT
         *             )
         *         """;
         *         try (Statement createStatement = connection.createStatement()) {
         *             createStatement.execute(createTableSql);
         *             System.out.println("表 stu 已创建（如果之前不存在）");
         *         }
         *
         *         // 3. 插入数据 SQL
         *         String insertSql = "INSERT INTO stu(id, name, age) VALUES (?, ?, ?)";
         *         try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
         *             statement.setInt(1, 14);
         *             statement.setString(2, "李四");
         *             statement.setInt(3, 16);
         *
         *             int rowsInserted = statement.executeUpdate();
         *             System.out.println("成功插入记录数: " + rowsInserted);
         *         }
         *
         *         // 4. 关闭连接
         *         connection.close();
         *         System.out.println("数据库连接已关闭");
         *     }
         */


    }
}





