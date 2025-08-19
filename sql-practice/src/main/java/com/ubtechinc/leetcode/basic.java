package com.ubtechinc.leetcode;

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

        /**
         * 一些sql的练习：
         *
         * 1、选出表中特定内容
         * Write a solution to find the employees who earn more than their managers. Return the result table in any order.
         *
         * eg:
         * Employee table:
         * +----+-------+--------+-----------+
         * | id | name  | salary | managerId |
         * +----+-------+--------+-----------+
         * | 1  | Joe   | 70000  | 3         |
         * | 2  | Henry | 80000  | 4         |
         * | 3  | Sam   | 60000  | Null      |
         * | 4  | Max   | 90000  | Null      |
         * +----+-------+--------+-----------+
         * Output:
         * +----------+
         * | Employee |
         * +----------+
         * | Joe      |
         * +----------+
         * Explanation: Joe is the only employee who earns more than his manager.
         *
         * solution:
         *
         * SELECT e2.name as Employee
         * FROM employee e1
         * INNER JOIN employee e2 on e1.id=e2.managerId
         * where e1.salary<e2.salary
         *
         *
         * 1.FROM employee e1
         * 把 employee 表取一个别名叫 e1（代表经理）。
         * 2.  INNER JOIN employee e2 ON e1.id = e2.managerId
         * 再取一份 employee 表别名叫 e2（代表员工）。
         * 条件：e1.id = e2.managerId，即 e1 是 e2 的经理。
         * 3.  WHERE e1.salary < e2.salary;
         * 限制条件：员工工资大于经理工资。
         * 4.  SELECT e2.name as Employee
         * 输出员工的名字, employee → 表的名字（数据来源）。
         * •  AS Employee → 查询结果中的列别名（显示用）。
         *
         * 2、关于count()函数
         * +-----+---------+-------+------------+
         * | aid | site_id | count | date       |
         * +-----+---------+-------+------------+
         * |   1 |       1 |    45 | 2016-05-10 |
         * |   2 |       3 |   100 | 2016-05-13 |
         * |   3 |       1 |   230 | 2016-05-14 |
         * |   4 |       2 |    10 | 2016-05-14 |
         * |   5 |       5 |   205 | 2016-05-14 |
         * |   6 |       4 |    13 | 2016-05-15 |
         * |   7 |       3 |   220 | 2016-05-15 |
         * |   8 |       5 |   545 | 2016-05-16 |
         * |   9 |       3 |   201 | 2016-05-17 |
         * +-----+---------+-------+------------+
         * COUNT(*) •  含义：计算 所有行的数量（无论某列是否为 NULL，都算上）。
         *
         * COUNT(DISTINCT column_name) 函数返回指定列的不同值的数目，强调，是不同值
         ** SELECT COUNT(DISTINCT column_name) FROM table_name;
         * 这里的 COUNT(count) 不是把 count 里的数（比如 100、230、220…）加起来，而是数 多少条记录有 count 值。
         *
         *如果你真的想要把 count 字段里面的数加起来，应该用 SUM：
         * SELECT SUM(count) AS total
         * FROM access_log
         * WHERE site_id = 3;
         *这样返回的是 100 + 220 + 201 = 521
         *
         *
         *
         *
         *
         * Table: Courses
         * +-------------+---------+
         * | Column Name | Type    |
         * +-------------+---------+
         * | student     | varchar |
         * | class       | varchar |
         * +-------------+---------+
         * (student, class) is the primary key (combination of columns with unique values) for this table.
         * Each row of this table indicates the name of a student and the class in which they are enrolled.
         * Write a solution to find all the classes that have at least five students. Return the result table in any order.
         *
         *
         *
         * SELECT c.class
         * from courses c
         * GROUP BY c.class
         * HAVING COUNT(c.student)>=5
         *
         * 这里只有一个表所以不用.student也可以，但为了习惯这样最好
         *
         *关于having语句
         * 如果选择的内容限定条件有两个：
         * SELECT Websites.name, SUM(access_log.count) AS nums FROM Websites
         * INNER JOIN access_log
         * ON Websites.id=access_log.site_id
         * WHERE Websites.alexa < 200
         * GROUP BY Websites.name
         * HAVING SUM(access_log.count) > 200;
         *
         * 如果还有别的限制条件就在having后面加and
         *
         * 关于groupby的简单应用之二：
         * +----+--------------+---------------------------+-------+---------+
         * | id | name         | url                       | alexa | country |
         * +----+--------------+---------------------------+-------+---------+
         * | 1  | Google       | https://www.google.cm/    | 1     | USA     |
         * | 2  | 淘宝          | https://www.taobao.com/   | 13    | CN      |
         * | 3  | 菜鸟教程      | http://www.runoob.com/    | 4689  | CN      |
         * | 4  | 微博          | http://weibo.com/         | 20    | CN      |
         * | 5  | Facebook     | https://www.facebook.com/ | 3     | USA     |
         * | 7  | stackoverflow | http://stackoverflow.com/ |   0 | IND     |
         * +----+---------------+---------------------------+-------+---------+
         * mysql> SELECT * FROM access_log;
         * +-----+---------+-------+------------+
         * | aid | site_id | count | date       |
         * +-----+---------+-------+------------+
         * |   1 |       1 |    45 | 2016-05-10 |
         * |   2 |       3 |   100 | 2016-05-13 |
         * |   3 |       1 |   230 | 2016-05-14 |
         * |   4 |       2 |    10 | 2016-05-14 |
         * |   5 |       5 |   205 | 2016-05-14 |
         * |   6 |       4 |    13 | 2016-05-15 |
         * |   7 |       3 |   220 | 2016-05-15 |
         * |   8 |       5 |   545 | 2016-05-16 |
         * |   9 |       3 |   201 | 2016-05-17 |
         * +-----+---------+-------+------------+
         *
         *
         * 统计 access_log 各个 site_id 的访问量：
         *
         * SELECT site_id, SUM(access_log.count) AS nums
         * FROM access_log GROUP BY site_id;
         *
         * 显示的就是两列，siteid和num
         *
         * 3、聚合的再次练习
         *
         *
         *Table: Person
         * Write a solution to report all the duplicate emails. Note that it's guaranteed that the email field is not NULL.
         * Return the result table in any order.
         *
         * solution1:
         *Select p.email
         * from Person p
         * GROUP BY p.email
         * HAVING COUNT(p.email)>1
         * GROUP BY email → 把相同 email 的行分到同一组。
         * •  HAVING COUNT(email) > 1 → 过滤掉只有 1 行的组，只保留出现超过一次的 email。
         *
         * solution2:
         * select distinct(p1.email)
         * From Person p1, Person p2
         * WHERE p1.email=p2.email AND
         *p1.email<>p2.email
         *
         * 在 SQL 里，<> 就是 "不等于" (NOT EQUAL) 的意思。  •  它等价于 !=，两者都可以用。
         *避免自己和自己匹配
         *
         * WHERE：在分组前过滤行。
         * HAVING：在 GROUP BY 之后，对聚合结果再过滤
         *
         * select c1.name
         * from customer c1
         *group by c1.name
         * having c1.referr_id is null or c1c1.referr_id!=2
         *
         *
         *select c1.name
         * from customer c1
         *where c1.referr_id is null or c1c1.referr_id!=2
         *
         * 4、CASE
         *
         * 搜索型 CASE（Searched CASE） •  语法结构：
         * CASE
         *     WHEN 条件1 THEN 结果1
         *     WHEN 条件2 THEN 结果2
         *     ELSE 默认结果
         * END
         *
         * 把f和m替换
         * UPDATE Salary
         * SET sex = (CASE
         *               WHEN sex = 'f' THEN 'm'
         *               ELSE 'f'
         *            END);
         *
         */



    }
}
