package com.ubtechinc.leetcode;

/**
 *简单介绍sql的概念
 * @author MacBook Air
 * @date 2025/8/13 23:12
 */

public class Basic {

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
         * access_log:
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
         * having c1.referr_id is null or c1.referr_id!=2
         *
         *
         *select c1.name
         * from customer c1
         *where c1.referr_id is null or c1c1.referr_id!=2
         *
         * 4、CASE相关条件选择
         *
         * 搜索型 CASE（Searched CASE） •  语法结构：
         * CASE
         *     WHEN 条件1 THEN 结果1
         *     WHEN 条件2 THEN 结果2
         *     ELSE 默认结果
         * END
         *
         * 把表中的f和m替换
         * UPDATE Salary
         * SET sex = (CASE
         *               WHEN sex = 'f' THEN 'm'
         *               ELSE 'f'
         *            END);
         *
         * 关于是否是三角形的判断：
         * Example 1: Input:
         * Triangle table:
         * +----+----+----+
         * | x  | y  | z  |
         * +----+----+----+
         * | 13 | 15 | 30 |
         * | 10 | 20 | 15 |
         * +----+----+----+
         * Output:
         * +----+----+----+----------+
         * | x  | y  | z  | triangle |
         * +----+----+----+----------+
         * | 13 | 15 | 30 | No       |
         * | 10 | 20 | 15 | Yes      |
         * +----+----+----+----------+
         *
         * SELECT x,y,z,
         * case WHEN (x+y) > z AND (x+z) > y AND (y+z) > x THEN 'Yes' ELSE 'No' end AS triangle
         * FROM Triangle
         *
         * 从Triangle表里选择了x,y,z,triangle(相当于是创的）作为显示目录
         *
         *
         *5.关于两个表聚合和not in
         *
         * Write a solution to find all customers who never order anything.
         *
         * Return the result table in any order.
         *
         * The result format is in the following example.
         *
         *有两个表
         *
         * Example 1:
         *
         * Input:
         * Customers table:
         * +----+-------+
         * | id | name  |
         * +----+-------+
         * | 1  | Joe   |
         * | 2  | Henry |
         * | 3  | Sam   |
         * | 4  | Max   |
         * +----+-------+
         * Orders table:
         * +----+------------+
         * | id | customerId |
         * +----+------------+
         * | 1  | 3          |
         * | 2  | 1          |
         * +----+------------+
         * Output:
         * +-----------+
         * | Customers |
         * +-----------+
         * | Henry     |
         * | Max       |
         * +-----------+
         *
         * solution1:
         * Select c1.name as Customers
         * from Customer c1
         * where c1.id not in (select customerid from order)
         *
         *
         *
         * 关于in语法
         * IN 操作符允许您在 WHERE 子句中规定多个值。
         * 语法 SELECT column1, column2, ...
         * FROM table_name
         * WHERE column IN (value1, value2, ...);
         *
         * 另外一道题的not in应用
         *
         * SELECT s.name
         * FROM SalesPerson s
         * WHERE s.sales_id NOT IN (
         *     SELECT o.sales_id
         *     FROM Orders o
         *     JOIN Company c ON o.com_id = c.com_id
         *     WHERE c.name = 'RED'
         * );
         *
         *
         *
         * 示例
         * +----+---------------+---------------------------+-------+---------+
         * | id | name          | url                       | alexa | country |
         * +----+---------------+---------------------------+-------+---------+
         * |  1 | Google        | https://www.google.cm/    |     1 | USA     |
         * |  2 | 淘宝          | https://www.taobao.com/   |    13 | CN      |
         * |  3 | 菜鸟教程       | http://www.runoob.com/    |  5000 | USA     |
         * |  4 | 微博           | http://weibo.com/         |    20 | CN      |
         * |  5 | Facebook      | https://www.facebook.com/ |     3 | USA     |
         * |  7 | stackoverflow | http://stackoverflow.com/ |     0 | IND     |
         * +----+---------------+---------------------------+-------+---------+
         *
         * SELECT * FROM Websites
         * WHERE name IN ('Google','菜鸟教程');
         *
         * solution2: left join 方法
         *
         * select c1.name as customers
         * from customer c1
         * left outer join orders o1
         * on c1.id=c2.customername
         * where c2.customersname isNULL
         *
         * 关于left join语法：
         * SELECT column_name(s)
         * FROM table1
         * LEFT OUTER JOIN table2
         * ON table1.column_name=table2.column_name;
         *
         * 特点：
         *
         *     返回左表中的所有记录，即使右表没有匹配的数据。
         *     如果右表没有匹配的记录，结果中该行右表字段为 NULL，上述例子就是专门输出了为null的那些数据
         *
         * 关于其他的join语法整理：
         * +-----------------+-------------------------------------------------------------+
         * | 类型            | 描述                                                        |
         * +-----------------+-------------------------------------------------------------+
         * | INNER JOIN      | 返回两个表中满足连接条件的记录（交集）。                     |
         * | LEFT JOIN       | 返回左表中的所有记录，即使右表中没有匹配的记录（保留左表）。 |
         * | RIGHT JOIN      | 返回右表中的所有记录，即使左表中没有匹配的记录（保留右表）。 |
         * | FULL OUTER JOIN | 返回两个表的并集，包含匹配和不匹配的记录。                   |
         * | CROSS JOIN      | 返回两个表的笛卡尔积，每条左表记录与每条右表记录进行组合。   |
         * | SELF JOIN       | 将一个表与自身连接。                                        |
         * | NATURAL JOIN    | 基于同名字段自动匹配连接的表。                              |
         * +-----------------+-------------------------------------------------------------+
         *
         * 6、关于表的日期筛选
         * Write a solution to find all dates' id with higher temperatures compared to its previous dates (yesterday). Return the result table in any order. The result format is in the following example.   Example 1: Input:
         * Weather table:
         * +----+------------+-------------+
         * | id | recordDate | temperature |
         * +----+------------+-------------+
         * | 1  | 2015-01-01 | 10          |
         * | 2  | 2015-01-02 | 25          |
         * | 3  | 2015-01-03 | 20          |
         * | 4  | 2015-01-04 | 30          |
         * +----+------------+-------------+
         * Output:
         * +----+
         * | id |
         * +----+
         * | 2  |
         * | 4  |
         * +----+
         * Explanation:
         * In 2015-01-02, the temperature was higher than the previous day (10 -> 25).
         * In 2015-01-04, the temperature was higher than the previous day (20 -> 30).
         *
         *solution:
         * SELECT today.id
         * FROM Weather yesterday
         * CROSS JOIN Weather today
         * WHERE DATEDIFF(today.recordDate, yesterday.recordDate) = 1
         *   AND today.temperature > yesterday.temperature;
         *
         * 这里的yesterday和today相当于新建了两张表，都是weather，crossjoin这里的效果是在自对应，
         * 把各种前后的可能的对应都写出来了
         *
         * 然后DATEDIFF（）是它自带的方法
         *
         * 7、关于另外的日期和聚合练习
         *
         * Write a solution to find the first login date for each player.
         * Return the result table in any order. The result format is in the following example.
         *
         * Example 1: Input:
         * Activity table:
         * +-----------+-----------+------------+--------------+
         * | player_id | device_id | event_date | games_played |
         * +-----------+-----------+------------+--------------+
         * | 1         | 2         | 2016-03-01 | 5            |
         * | 1         | 2         | 2016-05-02 | 6            |
         * | 2         | 3         | 2017-06-25 | 1            |
         * | 3         | 1         | 2016-03-02 | 0            |
         * | 3         | 4         | 2018-07-03 | 5            |
         * +-----------+-----------+------------+--------------+
         * Output:
         * +-----------+-------------+
         * | player_id | first_login |
         * +-----------+-------------+
         * | 1         | 2016-03-01  |
         * | 2         | 2017-06-25  |
         * | 3         | 2016-03-02  |
         * +-----------+-------------+
         *
         * select a1.player_id, MIN(event_date) AS a1.first_login
         * from Activity a1
         * group by a1.player_id
         *
         * Write a solution to report the name and bonus amount of each employee with a bonus less than 1000. Return the result table in any order.
         * The result format is in the following example.
         *
         * Example 1: Input:
         * Employee table:
         * +-------+--------+------------+--------+
         * | empId | name   | supervisor | salary |
         * +-------+--------+------------+--------+
         * | 3     | Brad   | null       | 4000   |
         * | 1     | John   | 3          | 1000   |
         * | 2     | Dan    | 3          | 2000   |
         * | 4     | Thomas | 3          | 4000   |
         * +-------+--------+------------+--------+
         * Bonus table:
         * +-------+-------+
         * | empId | bonus |
         * +-------+-------+
         * | 2     | 500   |
         * | 4     | 2000  |
         * +-------+-------+
         * Output:
         * +------+-------+
         * | name | bonus |
         * +------+-------+
         * | Brad | null  |
         * | John | null  |
         * | Dan  | 500   |
         * +------+-------+
         *
         * select e1.name, b1.bonus
         * from Employee e1
         * left join Bonus b1
         * on e1.empid = b1.empid
         * where b1.bonus is Null or b1.bonus<1000
         *
         * 8、order升降序应用
         *
         *Write a solution to find the customer_number for the customer who has placed the largest number of orders. The test cases are generated so that exactly one customer will have placed more orders than any other customer. The result format is in the following example.   Example 1: Input:
         * Orders table:
         * +--------------+-----------------+
         * | order_number | customer_number |
         * +--------------+-----------------+
         * | 1            | 1               |
         * | 2            | 2               |
         * | 3            | 3               |
         * | 4            | 3               |
         * +--------------+-----------------+
         * Output:
         * +-----------------+
         * | customer_number |
         * +-----------------+
         * | 3               |
         * +-----------------+
         * Explanation:
         * The customer with number 3 has two orders, which is greater than either customer 1 or 2 because each of them only has one order.
         * So the result is customer_number 3.
         *
         *
         * solution:
         * SELECT customer_number
         * FROM Orders
         * GROUP BY customer_number
         * ORDER BY COUNT(*) DESC
         * LIMIT 1;
         *
         * 如果同样数量的customer有很多个，那么就用rank()函数：
         *
         * 窗口函数（更简洁）
         * SELECT customer_number
         * FROM (
         *     SELECT customer_number,
         *            RANK() OVER (ORDER BY COUNT(order_number) DESC) AS rnk
         *     FROM Orders
         *     GROUP BY customer_number
         * ) t
         * WHERE rnk = 1;
         *   这里 RANK() 会给最多订单的顾客打上 rank = 1，不管有多少个并列。
         * 最后 WHERE rnk = 1 就能把所有并列最大值都取出来。
         *
         * 9.
         * A single number is a number that appeared only once in the MyNumbers table.
         * Find the largest single number. If there is no single number, report null. The result format is in the following example.   Example 1: Input:
         * MyNumbers table:
         * +-----+
         * | num |
         * +-----+
         * | 8   |
         * | 8   |
         * | 3   |
         * | 3   |
         * | 1   |
         * | 4   |
         * | 5   |
         * | 6   |
         * +-----+
         * Output:
         * +-----+
         * | num |
         * +-----+
         * | 6   |
         * +-----+
         * Explanation: The single numbers are 1, 4, 5, and 6.
         * Since 6 is the largest single number, we return it.
         *  Example 2: Input:
         * MyNumbers table:
         * +-----+
         * | num |
         * +-----+
         * | 8   |
         * | 8   |
         * | 7   |
         * | 7   |
         * | 3   |
         * | 3   |
         * | 3   |
         * +-----+
         * Output:
         * +------+
         * | num  |
         * +------+
         * | null |
         * +------+
         * Explanation: There are no single numbers in the input table so we return null.
         *
         * select MAX(num) as num
         *from(
         * select num from mynumber num1
         *group by num
         * having COUNT(num)<=1)
         * as num
         *
         * 子查询必须还有一个别名，所以必须还得加一个as num
         */



    }
}
