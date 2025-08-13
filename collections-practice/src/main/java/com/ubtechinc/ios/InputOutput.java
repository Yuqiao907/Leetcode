package com.ubtechinc.ios;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * IO
 *
 * @author MacBook Air
 * @date 2025/8/6 3:14
 */

public class InputOutput {
    /**
     * Input指从外部读入数据到内存,例如，把文件从磁盘读取到内存，从网络读取数据到内存等等
     * Output指把数据从内存输出到外部，例如，把数据从内存写入到文件，把数据从内存输出到网络等等。
     * <p>
     * 要把数据读到内存才能处理这些数据？因为代码是在内存中运行的，数据也必须读到内存，最终的表示方式无非是byte数组，字符串等，都必须存放在内存里
     * 内存有“易失性”的特点，所以必须把处理后的数据以某种方式输出，例如，写入到文件。Output实际上就是把Java表示的数据格式，例如，byte[]，String等输出到某个地方
     * IO流是一种顺序读写数据的模式，它的特点是单向流动。数据类似自来水一样在水管中流动，所以我们把它称为IO流。
     * <p>
     * 从磁盘读入一个文件是输入字节流 inputstream
     * 字节从内存写入磁盘文件，就是输出字节流 outputstream
     * 这是最基本的两种IO流
     * <p>
     * 如果我们需要读写的是字符，并且字符不全是单字节表示的ASCII字符，那么，按照char来读写显然更方便，这种流称为字符流。
     * Reader和Writer表示字符流，字符流传输的最小数据单位是char,Reader和Writer本质上是一个能自动编解码的InputStream和OutputStream
     * <p>
     * Reader和Writer vs InputStream和OutputStream
     *究竟使用Reader还是InputStream，要取决于具体的使用场景。
     * 如果数据源不是文本，就只能使用InputStream，如果数据源是文本，使用Reader更方便一些。
     * <p>
     * 使用Reader，数据源虽然是字节，但我们读入的数据都是char类型的字符，原因是Reader内部把读入的byte做了解码，转换成了char
     * 使用InputStream，我们读入的数据和原始二进制数据一模一样，是byte[]数组，但是我们可以自己把二进制byte[]数组按照某种编码转换为字符串。
     * */

    /**
     * 同步和异步
     * 同步IO是指，读写IO时代码必须等待数据返回后才继续执行后续代码，它的优点是代码编写简单，缺点是CPU执行效率低。
     * 异步IO是指，读写IO时仅发出请求，然后立刻执行后续代码，它的优点是CPU执行效率高，缺点是代码编写复杂。
     * Java标准库的包java.io提供了同步IO，而java.nio则是异步IO，上述讨论的都是同步
     */

    public static void readfile(File files) throws IOException {
        InputStream inputStream = new FileInputStream(files);
        //读取每一个字节
        for (; ; ) {
            int n = inputStream.read();
            if (n == -1) {
                break;
            }
            System.out.print((char) n);
        }
        inputStream.close();

    }

    public static void main(String[] args) throws IOException {

        /**
         * 传入数据，传入文件路径
         */

        /**

         构造File对象时，既可以传入绝对路径，也可以传入相对路径。绝对路径是以根目录开头的完整路径，例如：
         File f = new File("C:\\Windows\\notepad.exe");
         传入相对路径传时，相对路径前面加上当前目录就是绝对路径
         假设当前目录是C:\Docs,对应的绝对路径就是C:\Docs\sub\javac
         File f1 = new File("sub\\javac");
         绝对路径是C:\sub\javac
         File f3 = new File("..\\sub\\javac");
         System.out.println(f);

         */

        File file = new File("C:\\Users\\MacBook Air\\test.txt");
        System.out.println(file.isDirectory());
        readfile(file);


        /**
         *.isfile() .isdirectory()，判断该File对象是否是一个已存在的目录
         * boolean canRead()：是否可读；
         * • boolean canWrite()：是否可写；
         * • boolean canExecute()：是否可执行；
         * • long length()：文件字节大小。
         */

        /**
         * 创建与删除文件
         * File file = new File("/path/to/file");
         * if (file.createNewFile()) {
         *     // 文件创建成功:
         *
         *     if (file.delete()) {
         *         // 删除文件成功:
         *     }
         * }
         * <p>
         * //创建临时文件并将它删除
         * // 提供临时文件的前缀和后缀
         * File f = File.createTempFile("tmp-", ".txt");
         * // JVM退出时自动删除
         * f.deleteOnExit();
         *
         */


        /**
         * readfile方法在最上方
         */


        File openFile = new File("C:\\Users\\MacBook Air\\test.txt");

        try {
            readfile(openFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //9.1后的练习 有空可做


        /**
         * 请利用File对象列出指定目录下的所有子目录和文件，并按层次打印。 例如，输出： Documents/
         *   word/
         *     1.docx
         *     2.docx
         *     work/
         *       abc.doc
         *   ppt/
         *   other/
         *  如果不指定参数，则使用当前目录，如果指定参数，则使用指定目录。
         */

        /**
         * public static void main(String[] args) throws IOException {
         *
         * 		 File f = new File("C:\\luotl\\leantest");
         * 		    // 列出所有文件
         * 	        File[] fs1 = f.listFiles();
         * 	        //把每个文件的子目录都打出来
         * 	        printFiles(fs1);
         *                }
         *
         * 	 static void printFiles(File[] files) {
         * 	        System.out.println("==========");
         * 	        //确认file数组不为空
         * 	        if (files != null) {
         * 	            //把数组里每个file过一遍
         * 	            for (File f : files) {
         * 	                //确定存在
         * 	            	if(f.isDirectory())
         *                    {  //递归，只要还有子目录就一直打印
         * 	            		 File[] fs1 = f.listFiles();
         * 	            		 printFiles(fs1);
         *                    }
         * 	                System.out.println(f);
         *                }
         *            }
         * 	        System.out.println("==========");
         *        }
         */

        /// input stream的Fileinputstream的read方法，close，缓冲、阻塞区与实现
        /// InputStream并不是一个接口，而是一个抽象类，它是所有输入流的超类。这个抽象类定义的一个最重要的方法就是int read()，签名如下：
        ///public abstract int read() throws IOException;
        /// 这个方法会读取输入流的下一个字节，并返回字节表示的int值（0~255）。如果已读到末尾，返回-1表示不能继续读取了。
        ///如何读取文件的示例如下：
        //也许会存在错误不能读取，所以方法必须抛异常和用try，所有涉及到inputstream、outputstream的都要如此
        /*
        public void readFile() throws IOException {
            InputStream input = new FileInputStream("src/readme.txt");
            try {
                for (; ; ) {
                    int n = input.read();
                    //不断调用直到返回值为负1
                    if (n == -1) {
                        break;
                    }
                    System.out.print((char) n);

                }
            } finally {
                //必须close不然占内存，如果读取中发生了错误容易无法到close这步，所以要用try
                if( input!=null) {
                    input.close();}
            }

        }
         */


        /**
         * 缓冲区，读取到缓冲区，示例：
         * public void readFile() throws IOException {
         *     try (InputStream input = new FileInputStream("src/readme.txt")) {
         *         // 定义1000个字节大小的缓冲区:
         *         byte[] buffer = new byte[1000];
         *         int n;
         *         //一次性尽量多地从文件中读取字节，存到 buffer[0] ~ buffer[n-1],如果没有缓冲区那就只能一个一个字节地读
         *         //input.read(buffer)是缓冲区专门的用法
         *         while ((n = input.read(buffer)) != -1) {
         *             System.out.println("read " + n + " bytes.");
         *         }
         *
         *
         *     }
         * }
         */


        /**
         * 阻塞。read方法自带blocking，必须要等到read（）方法结束返回才能执行下一项，所以IO流比较费时
         */

        /**
         * inputstream实现类
         * 我们想从文件中读取所有字节，并转换成char然后拼成一个字符串，可以这么写：
         *
         */

        String s;
        try (InputStream input = new FileInputStream("C:\\Users\\MacBook Air\\test.txt")) {
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = input.read()) != -1) {
                //n!=' '这样可以把空格去掉
                //这样可以把换行之类的都去掉
                if (!Character.isWhitespace((char) n)) {
                    sb.append((char) n);
                }

            }
            s = sb.toString();
        }
        System.out.println();
        System.out.print(s);


        /**
         * outputstream 有一个flush方法强制把缓冲区的内容输出，不然就是缓冲区满了才会输出。
         *
         * 关于读缓冲区和写缓冲区的区别：
         * 读缓冲区：read() 调用时数据会立即从输入源（文件/网络）读进缓冲区，然后直接返回给你 → 不需要 flush()。
         * 写缓冲区：write() 先把数据放到内存的写缓冲区，等缓冲区满了或调用 flush() 才会把数据真正写到输出目标（文件/网络）。
         *
         * 这下面是读缓冲区：
         * InputStream input = new FileInputStream("D://out.txt");
         * byte[] data = new byte[1024];
         * //读进缓存区一批,缓冲区 data 大小是 1024,这次可能只读到 500 个字节，也有可能文件很大，需要读进缓存区好几次
         * int bytesRead = input.read(data);
         * //在缓存区的内容没有被读尽的时候，把byteRead看成n就行
         * while(bytesRead != -1) {
         *   doSomethingWithData(data, bytesRead);
         * //就是不断更新 bytesRead 为下一批数据的有效长度。覆盖成“本次读到多少字节”
         *   bytesRead = input.read(data);
         * }
         * input.close();
         *
         *
         */


        /**
         * 关于path接口：Path 是 Java NIO（New IO）里的一个接口（java.nio.file.Path），表示文件或目录的路径
         *
         * 用 String 存 "C:\\Users\\you\\file.txt"：
         *拼路径要自己手动加 / 或 \\，还要担心跨平台分隔符（Windows \，Linux /）。
         *解析父目录、文件名都要自己用字符串处理。
         *
         * 如果用 Path：
         *跨平台拼接路径不用关心分隔符，它会自动用当前系统的正确分隔符。
         *提供现成方法取父目录、文件名、根目录等信息。
         */


        /// output stream实现类
        /// input是把外部文件的内容读取，output是把内部的内容编写输出

        /// 模拟output stream，将一些内容实现并打印,这个方便于内部测试的时候
        byte[] data;

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            output.write("Hello".getBytes("UTF-8"));
            data = output.toByteArray();
        }
        System.out.println(data);

        /**
         * 关于transferto方法
         */

        // 读取input.txt，写入output.txt:
        /// //这里是从当前路径来找这个txt文件，是相对位置
        /// try (InputStream input = new FileInputStream("input.txt");
        ///              OutputStream output = new FileOutputStream("output.txt")) {
        ///             // transferTo的作用是：它会自动：
        ///             // 1.  从当前 InputStream（这里是 input.txt）循环读取数据到内部的缓冲区；
        ///             // 2.  再把数据写到你传入的 OutputStream（这里是 output.txt）；
        ///             // 3.  一直读到文件末尾（-1）为止。
        ///             input.transferTo(output);
        ///         }
        /// 编写一个复制文件的方法
        /// static void copy(String source, String target) throws IOException {
        /// 		InputStream input = new FileInputStream(source);
        /// 		OutputStream output = new FileOutputStream(target);
        /// 	    //这里是简化写法，try (input; output) 这一句的唯一目的就是——告诉 try-with-resources 这两个流要自动 close，而不是重新创建它们，这样try里面也不需要重新new对象
        /// 		try (input; output) {
        /// 			//建立缓存5M（1024*1024*5）大小
        /// 			byte[] b = new byte[5242880];
        /// 			//获取每次读取返回的数组长度
        /// 			int len = 0;
        /// 			//input.read(b) 会尝试从输入流中读取数据到 b 数组里。
        /// 			//1. 返回值是实际读取到的字节数（len）,如果到了文件末尾，返回 -1。
        /// 			//2.  len = input.read(b) 是赋值表达式，会把返回值存进 len 变量。
        /// 			while ((len = input.read(b)) != -1) {
        /// 				//写入时，从数组0索引开始读取，只写入读取了的长度,这里是一次性就写到了尾
        /// 				output.write(b, 0, len);
        ///            }
        ///    }
        ///}

        /**
         * mark()和reset()方法,两个配套使用
         * 正常读 → mark() → 再读一些数据 → reset() → 从 mark 的地方重新开始读
         * mark中的readlimit是限制mark生效的的字节，避免mark生效时间过长一直回到mark处循环读取
         * 如果你在 mark() 之后读的字节数 超过了 readlimit，这个标记就会失效，再调用 reset() 会抛异常（IOException）或位置不对。
         *
         */
        //也可以像这样不传入文件直接传入bytes的
        InputStream input = new ByteArrayInputStream("cctv".getBytes());
        //计算是第几次读取
        int count = 0;
        //每次读取的字节数
        byte[] bytes = new byte[3];
        //读取每次返回的是数字
        int dataRead = input.read(bytes);
        while (dataRead != -1) {
            //把每一次读取的内容打出来
            System.out.print(new String(bytes, 0, dataRead, "UTF-8"));
            count++;
            if (count == 2) {
                input.mark(16);
            }
            //继续往后读取
            dataRead = input.read(bytes);
        }

        //将内容读完一轮之后，重新回到mark的位子
        input.reset();
        dataRead = input.read(bytes);
        while (dataRead != -1) {
            System.out.print(new String(bytes));
            dataRead = input.read(bytes);
        }


        /**
         * 一些file文件的用法：
         * 1、将txt文件读取成string
         * 2、写入文件
         */

        /**
         * //将全部文件读成byte,还有.readString()、readAllLines()
         * //byte[] dataByte = Files.readAllBytes(Path.of("/path/to/file.txt"));
         * //String content1 = Files.readString(Path.of("/path/to/file.txt"));
         * //List<String> lines = Files.readAllLines(Path.of("/path/to/file.txt"));
         */


        /**
         * // 写入二进制文件:
         * byte[] data = ...;
         * Files.write(Path.of("/path/to/file.txt"), data);
         * // 写入文本并指定编码:
         * Files.writeString(Path.of("/path/to/file.txt"), "文本内容...", StandardCharsets.ISO_8859_1);
         * // 按行写入文本:
         * List<String> lines = ...;
         * Files.write(Path.of("/path/to/file.txt"), lines);
         */



        /**
         * 关于filter模式和包装
         * 如果想用filter的模式同时实现能够不通过手动解压或者创建临时文件就
         */
        // 压缩文件路径
        String filePath = "logs.txt.gz";

        try (
                // 基础流：从文件读取字节
                InputStream fileStream = new FileInputStream(filePath);

                // 第一层包装：加缓冲，提高读性能
                InputStream bufferedStream = new BufferedInputStream(fileStream);

                // 第二层包装：解压 gzip
                InputStream gzipStream = new GZIPInputStream(bufferedStream);

                // 第三层包装：转成字符流，方便按行读取
                BufferedReader reader = new BufferedReader(new InputStreamReader(gzipStream, StandardCharsets.UTF_8))
        )
        {
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理每行数据
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    //如果想要新建文件并且把内容写入相对位置的项目文件夹里,应用一下path方法
    private static void writeToNewFile(File file, String content) throws IOException {
        try (OutputStream outputStream = new FileOutputStream("target/generated-resources/")) {
            outputStream.write("Hello".getBytes("UTF-8"));

            ///这个是把文件写进去之后，返回文件路径
            /// public static Path writeToTarget(String relativeName, String content) throws IOException {
            ///     //这个是返回当前目录或者项目根目录
            ///     Path projectRoot = Paths.get(System.getProperty("user.dir"));
            ///     //这里是把目标目录先拼接出来，relativename是自己决定的名字
            ///     Path path = projectRoot.resolve(Paths.get("target", "generated-data", relativeName));
            ///     //确保父目录存在，没有的话就创建
            ///     Files.createDirectories(path.getParent());
            ///     //把content转成byte存进去, CREATE：不存在就创建文件; TRUNCATE_EXISTING：存在就清空后重写;WRITE：以写模式打开。
            ///     Files.write(path, content.getBytes(StandardCharsets.UTF_8),
            ///             StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            ///     return path;
        }

    }


}






