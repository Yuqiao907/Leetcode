package com.ubtechinc.concept;

/**
 *简单介绍sql的概念
 * @author MacBook Air
 * @date 2025/8/13 23:12
 */

public class basic {

    /**
     *
     * MySQL = 数据仓库（存放数据）
     * •  JDBC = 翻译规则（Java 访问数据库的统一标准）
     * •  MySQL Connector = 会按照 JDBC 规则翻译的管道（把 Java 和 MySQL 连起来）
     *
     * 1、JDBC
     * _JDBC_ 是 Java 编程语言的 API，用于定义客户端如何访问数据库。
     * 它提供了查询和更新数据库中数据的方法，相当于会说 MySQL 语言的“翻译官”。
     * 它能帮你和 MySQL 对话，但前提是要有一个 MySQL 数据库在运行（本地或者远程）
     *
     * JDBC 管理以下三个主要的编程活动：
     *连接到数据库；向数据库发送查询和更新语句；检索和处理从数据库接收到的结果以响应查询。
     *
     * 2、MySQL 连接器
     *为了以 Java 连接到 MySQL，MySQL 提供了 MySQL Connector / J，它是实现 JDBC API 的驱动程序
     *在 Maven 里加 mysql-connector-java 依赖
     *
     * 3、MySQL 数据库
     * MySQL 是开源数据库管理系统。 它是一个多用户，多线程的数据库管理系统。
     *有了 MySQL 才能做完整练习
     *      *      * •  可以自己建数据库、表
     *      *      * •  可以插入测试数据
     *      *      * •  可以练习 SQL 语句（SELECT、INSERT、UPDATE、DELETE）
     *      *      * •  可以验证 JDBC 连接的整个流程
     *
     *
     */

    public static void main(String[] args) {
        

    }
}
