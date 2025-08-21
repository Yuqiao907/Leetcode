package com.ubtechinc.log;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

/**
 * 关于日志内容基础概念的整理
 * 参考网址：<a href="https://www.cnblogs.com/xiezhr/p/18358066">...</a>
 *
 * @author MacBook Air
 * @date 2025/8/20 23:40
 */

public class LogBasic {
    /**
     * 1、学习Java中的日志打印 日志分级、日志规范、日志格式，需养成规范化的日志打印习惯,
     * 常用日志框架：log4j2、logback ，@Slf4j的作用和使用
     */

    /**
     * 常用框架介绍
     *
     * 主流的日志实现框架有：
     *
     * JUL
     *Java自带的日志框架 ，功能相对基础，性能一般，但对于简单的日志需求来说足够用了。
     *
     * Log4j
     *非常老牌的日志框架，功能非常强大，可以自定义很多日志的细节，比如日志级别、输出格式、输出目的地等。现由Apache软件基金会维护
     *
     * Log4j2
     * 也是Apache软件基金会开发，相比Log4j，Log4j2在性能上有显著提升，同时保持了丰富的功能，支持异步日志处理，适合高性能需求的场景
     *
     * Logback
     * 由Log4j的原开发者之一主导开发,Spring Boot默认日志，轻量级，性能优秀，功能也比较全面
     */

    /**
     * 关于日志组件和基本原理
     * <p>
     * 3.1 主要组件
     * 1. Logger：日志记录器，是日志系统的核心，用来生成日志记录。
     * 2. Handler：日志处理器，负责将日志信息输出到不同的目的地，比如控制台、文件等。可以为每个Logger配置一个或多个Handler
     * 3. Formatter：日志格式化器，负责定义日志的输出格式。比如时间戳、日志级别、消息等。
     * 4. Level：设置日志级别，常见的级别有SEVERE、WARNING、INFO、CONFIG、FINE、FINER、FINEST等。
     * 5. Filter： 这个组件用来过滤日志记录。你可以设置一些规则，只有满足这些规则的日志才会被记录。
     * 6. Log Record：这是日志记录本身，包含了日志的所有信息，比如时间、日志级别、消息等
     * <p>
     * 3.2 使用步骤
     * 1. 获取Logger实例。
     * 2. 添加Handler
     * 3. 为上一步添加的Handler 设置日志级别（Level）和格式输出（Formatter）
     * 4. 创建Filter过滤器
     * 5. 为Logger实例添加日志处理器（Handler）和日志过滤器（Filter）
     * 6. 记录日志。
     */

    /**
     * 一个日志的示例代码（以下是JUL框架的）
     * <p>
     * 关于test注解
     * 这个注解来自 JUnit（或者 TestNG），表示这是一个单元测试方法。
     * 当你在 IDEA 里点绿色小箭头运行测试类/方法的时候，JUnit 会自动扫描并执行所有标记了 @Test 的方法，即使你自己在 main 方法里没调用。
     */
    @Test
    public void testLogQuick() {
        //创建日志记录对象
        Logger logger = Logger.getLogger("com.xiezhr");
        //日志记录输出
        logger.info("这是一个info日志");
        logger.log(Level.INFO, "这是一个info日志");

        String name = "xxx";
        Integer age = 18;
        logger.log(Level.INFO, "姓名：{0},年龄：{1}", new Object[]{name, age});

    }

    /**
     * 关于日志的级别
     * <p>
     * 日志级别
     * 1. SEVERE（严重）：这是最高级别的日志，用来记录严重错误，比如系统崩溃、数据丢失等。这类日志通常需要立即关注和处理。
     * 2. WARNING（警告）：用来记录可能不会立即影响系统运行，但可能表明潜在问题的信息。比如，某个操作没有达到预期效果，或者系统资源接近耗尽。
     * 3. INFO（信息）：用来记录一般性的信息，比如程序运行的状态、重要的操作步骤等。这类信息对于了解程序的运行情况很有帮助，但通常不需要立即处理。
     * 4. CONFIG（配置）：用来记录配置信息，比如程序启动时加载的配置文件、初始化的参数等。这类日志有助于调试和验证程序的配置是否正确。
     * 5. FINE（详细）：用来记录更详细的信息，比如程序内部的执行细节、变量的值等。这类日志对于开发者在调试程序时了解程序的内部状态非常有用。
     * 6. FINER（更详细）：比FINE级别更细的日志，记录更深入的执行细节。通常用于深入分析程序的运行情况。
     * 7. FINEST（最详细）：这是最低级别的日志，记录最详细的信息，包括程序的每一步执行细节。这类日志可能会产生大量的输出，通常只在需要非常详细的调试信息时使用。
     * 3.4.2 级别关系
     * SEVERE > WARNING > INFO > CONFIG > FINE > FINER > FINEST
     * 日志级别越高，记录的信息越重要。
     * 当你设置一个日志级别时，比如INFO，那么INFO级别以及以上的日志（SEVERE和WARNING）都会被记录，而FINE、FINER和FINEST级别的日志则会被忽略
     */


    /**
     * 另外一个代码示例
     */


    @Test
    public void testLogging() {
        // 获取日志记录器
        Logger logger = Logger.getLogger("LoggingExample");

        // 只会处理 INFO 及以上级别（INFO, WARNING, SEVERE）的日志消息
        // 比如 FINE 级别就不会被 logger 直接处理。
        logger.setLevel(Level.INFO);

        // 创建控制台Handler 将日志输出到控制台
        // 并设置其日志级别和Formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //设置级别为 WARNING，所以只有 WARNING 和 SEVERE 级别的日志才会输出到控制台。
        consoleHandler.setLevel(Level.WARNING);
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord record) {
                // 自定义日志格式
                return String.format("%1$tF %1$tT [%2$s] %3$s %n", record.getMillis(), record.getLevel(), record.getMessage());
            }
        });
        logger.addHandler(consoleHandler);

        // 创建文件Handler 将日志输出到文件
        // 并设置其日志级别和Formatter
        try {
            //日志会写入 app.log 文件。
            //第二个参数 true 表示 追加写入。
            FileHandler fileHandler = new FileHandler("app.log", true);
            //设置级别为 ALL，所以所有日志（包括 FINE 等低级别）都会写进文件。
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord record) {
                    // 自定义日志格式
                    return String.format("%1$tF %1$tT [%2$s] %3$s %n", record.getMillis(), record.getLevel(), record.getMessage());
                }
            });
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建并设置Filter
        //定义了一个过滤器：只有日志消息包含 "important" 才会被记录
        Filter filter = new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                // 这里可以添加过滤逻辑，例如只记录包含特定字符串的日志
                return record.getMessage().contains("important");
            }
        };

        // 将Filter应用到Logger
        //所有 logger 发出的日志都会先过一遍 filter。返回 false 的就会被丢弃。
        logger.setFilter(filter);

        // 记录不同级别的日志
        logger.severe("严重错误信息 - 应记录到控制台和文件");
        logger.warning("警告信息 - 应记录到控制台和文件");
        //级别 INFO，会写入文件，但不会出现在控制台（因为控制台最低 WARNING）
        logger.info("常规信息 - 只记录到文件");
        logger.config("配置信息 - 只记录到文件");
        logger.fine("详细日志 - 只记录到文件");


        // 这条日志将被Filter过滤掉，不会记录
        logger.info("这条信息不重要，将被过滤");

        // 这条日志将被记录，因为消息中包含"important"
        logger.info("这条信息important，将被记录到控制台和文件");
    }


    /**
     * 上述都为硬编码，不利于维护，推荐的是以下这种在resource里面建property文件来配置这些
     */

    /**在resource的property文件中：
     * # 指定日志处理器为：ConsoleHandler,FileHandler 表示同时使用控制台和文件处理器
     * handlers= java.util.logging.ConsoleHandler,java.util.logging.FileHandler
     *
     * #设置默认的日志级别为：ALL
     * .level= ALL
     *
     * # 配置自定义 Logger
     * com.xiezhr.handlers = com.xiezhr.DefConsoleHandler
     * com.xiezhr.level = CONFIG
     *
     * # 如果想要使用自定义配置，需要关闭默认配置
     * com.xiezhr.useParentHanlders =true
     *
     * # 向日志文件输出的 handler 对象
     * # 指定日志文件路径 当文件数为1时 日志为/logs/java0.log
     * java.util.logging.FileHandler.pattern = /logs/java%u.log
     * # 指定日志文件内容大小，下面配置表示日志文件达到 50000 字节时，自动创建新的日志文件
     * java.util.logging.FileHandler.limit = 50000
     * # 指定日志文件数量，下面配置表示只保留 1 个日志文件
     * java.util.logging.FileHandler.count = 1
     * # 指定 handler 对象日志消息格式对象
     * java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter
     * # 指定 handler 对象的字符集为 UTF-8 ，防止出现乱码
     * java.util.logging.FileHandler.encoding = UTF-8
     * # 指定向文件中写入日志消息时，是否追加到文件末尾，true 表示追加，false 表示覆盖
     * java.util.logging.FileHandler.append = true
     *
     *
     * # 向控制台输出的 handler 对象
     * # 指定 handler 对象的日志级别
     * java.util.logging.ConsoleHandler.level =WARNING
     * # 指定 handler 对象的日志消息格式对象
     * java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
     * # 指定 handler 对象的字符集
     * java.util.logging.ConsoleHandler.encoding = UTF-8
     *
     * # 指定日志消息格式
     * java.util.logging.SimpleFormatter.format = [%1$tF %1$tT] %4$s: %5$s %n
     *
     */

    /**
     * 关于日志的格式
     *
     * %1$、%2$：表示用第几个参数。
     * •  %1$ → 第一个参数
     * •  %2$ → 第二个参数
     * •  %3$ → 第三个参数
     * •  tF、tT：时间/日期格式化代码（t = time，F/T = 具体格式）。
     * •  s：表示字符串。
     * •  n：换行符。
     *
     * 举例：
     *
     * # 指定日志消息格式
     * java.util.logging.SimpleFormatter.format = %1$tF %1$tT [%2$s] %3$s %n (在配置文件中设置）
     *
     * 随后传入三个参数：
     *  // 第1个参数：日志时间（毫秒）
     * record.getMillis(),
     * //第2个参数：日志级别
     * record.getLevel(),
     * // 第3个参数：日志消息
     * record.getMessage()
     *
     * 最终输出为：2025-08-21 15:42:07 [WARNING] 数据库连接失败
     *
     * 如果是硬编码，则是：
     * fileHandler.setFormatter(new SimpleFormatter() {
     *     @Override
     *     public synchronized String format(LogRecord record) {
     *         // 自定义日志格式
     *         return String.format("%1$tF %1$tT [%2$s] %3$s %n", record.getMillis(), record.getLevel(), record.getMessage());
     *     }
     * });
     *
     */

    /**
     * 上述的property文件配合的执行测试代码应该是这样的：
     *
     * @Test
     * public void testLogProperties()throws Exception{
     *
     *     // 1、读取配置文件，通过类加载器
     *     InputStream ins = LoggingExampleTest.class.getClassLoader().getResourceAsStream("logconfig.properties");
     *     // 2、创建LogManager
     *     LogManager logManager = LogManager.getLogManager();
     *     // 3、通过LogManager加载配置文件
     *     logManager.readConfiguration(ins);
     *
     *     // 4、创建日志记录器
     *     Logger logger = Logger.getLogger("com.xiezhr");
     *
     *     // 5、记录不同级别的日志
     *     logger.severe("这是一条severe级别信息");
     *     logger.warning("这是一条warning级别信息");
     *
     * }
     */



    public static void main(String[] args) {


    }


}
