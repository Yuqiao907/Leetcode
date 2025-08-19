package com.ubtechinc.remote.linksql;

import com.ubtechinc.remote.utils.JdbcUtils;
import com.ubtechinc.remote.adjustdata.AdjustData;
import com.ubtechinc.remote.model.datamodel.User;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 公司远端sql数据库的连接
 *
 * @author MacBook Air
 * @date 2025/8/18 2:20
 */

public class Linksql {



    public static void main(String[] args) throws SQLException {




        /**
         * 表创建
         */

        /**
         * 以下是一个规范的表头创建，comment是必须要写的，是团队协作的时候讲述每一个变量的功能
         * 标识符（表名、字段名）要么不用引号，要么用反引号 `（键盘 1 左边那个符号）
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



        // 1. 获取数据库连接
        Connection connection = JdbcUtils.getConnection();
        // 2. 建表 SQL
        //这里跟excel不一样，如果是excel路径文件名一致再运行不会报错，这里如果已经创建了一个表再创建会报错
        var createTableSql = """
                    CREATE TABLE IF NOT EXISTS `user_info`(
                        `user_name` varchar(32) NOT NULL COMMENT '用户名(默认为’user‘)', 
                        `password` char(10) NOT NULL COMMENT '用户密码',
                        `mobile`  varchar(14) NULL COMMENT'用户年龄'
                
                    );
                """;
        try (Statement createStatement = connection.createStatement()) {
            createStatement.execute(createTableSql);
            System.out.println("表 user_info 已创建（如果之前不存在）");
        }

        // 3. 插入数据 SQL
        //这里statement就自动close了
        String insertSql = "INSERT INTO user_info(user_name, password,mobile) VALUES (?, ?,?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
            statement.setString(1, "张三");
            statement.setString(2, "12zz");
            statement.setString(3, "1234567890");

            AdjustData.addUser(new User("王五","h8h8h8h","ks0uqo"));
            AdjustData.EmptyUserMobile("ks0uqo");
            AdjustData.addUser(new User("小六","h8h8h8h","ks0uqo"));
            AdjustData.addUser(new User("小七","h8h8h8h","ks0uqo"));





            int rowsInserted = statement.executeUpdate();
            System.out.println("成功插入记录数: " + rowsInserted);
        }

        // 4. 关闭连接
        //传上去进行完操作之后就关闭jdbc连接

        connection.close();
        System.out.println("数据库连接已关闭");

        /**
         * 表的调、改、查
         */


    }




}









