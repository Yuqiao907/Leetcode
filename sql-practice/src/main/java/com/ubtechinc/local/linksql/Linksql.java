package com.ubtechinc.local.linksql;

import com.ubtechinc.local.utils.JdbcUtils;

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
        //一般在工作的实际操作中不会创建表，只会调动改变数据库里面的数据
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
        //这是在已经创建的表stu里插入东西，不是创建表
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

        /**
         * 以下是一个规范的表头创建，comment是必须要写的，是团队协作的时候讲述每一个变量的功能
         */

        /**
         * CREATE TABLE `ucsp_user` (
         *   `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
         *   `app_id` int(11) NOT NULL COMMENT '应用ID',
         *   `username` varchar(32) NOT NULL COMMENT '用户名(默认为UUID)',
         *   `nickname` varchar(64) NOT NULL COMMENT '用户昵称(默认值为手机号或邮箱)',
         *   `password` char(60) NOT NULL COMMENT '用户密码',
         *   `area_code` varchar(5) DEFAULT NULL COMMENT '手机区号',
         *   `mobile` varchar(24) DEFAULT NULL COMMENT '手机号',
         *   `email` varchar(152) DEFAULT NULL COMMENT '用户邮箱',
         *   `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户性别(0:保密;1:男;2:女)',
         *   `birthday` date DEFAULT NULL COMMENT '用户生日',
         *   `avatar` varchar(684) DEFAULT NULL COMMENT '用户头像',
         *   `area` varchar(3) DEFAULT NULL COMMENT '用户所属国家编码',
         *   `register_ip` varchar(64) DEFAULT NULL COMMENT '注册时的ip',
         *   `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
         *   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
         *   `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
         *   PRIMARY KEY (`user_id`)
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='C端用户信息表';
         */

        //传上去进行完操作之后就关闭jdbc连接
        statement.close();
        connection.close();


    }
}





