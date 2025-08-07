package com.ubtechinc.ios;

import java.io.*;
import java.util.Scanner;

/**
 *IO
 * @author MacBook Air
 * @date 2025/8/6 3:14
 */

public class InputOutput {
    /**
     * Input指从外部读入数据到内存,例如，把文件从磁盘读取到内存，从网络读取数据到内存等等
     * Output指把数据从内存输出到外部，例如，把数据从内存写入到文件，把数据从内存输出到网络等等。
     *
     * 要把数据读到内存才能处理这些数据？因为代码是在内存中运行的，数据也必须读到内存，最终的表示方式无非是byte数组，字符串等，都必须存放在内存里
     * 内存有“易失性”的特点，所以必须把处理后的数据以某种方式输出，例如，写入到文件。Output实际上就是把Java表示的数据格式，例如，byte[]，String等输出到某个地方
     * IO流是一种顺序读写数据的模式，它的特点是单向流动。数据类似自来水一样在水管中流动，所以我们把它称为IO流。
     *
     * 从磁盘读入一个文件是输入字节流 inputstream
     * 字节从内存写入磁盘文件，就是输出字节流 outputstream
     * 这是最基本的两种IO流
     *
     * 如果我们需要读写的是字符，并且字符不全是单字节表示的ASCII字符，那么，按照char来读写显然更方便，这种流称为字符流。
     * Reader和Writer表示字符流，字符流传输的最小数据单位是char,Reader和Writer本质上是一个能自动编解码的InputStream和OutputStream
     *
     * Reader和Writer vs InputStream和OutputStream
     *究竟使用Reader还是InputStream，要取决于具体的使用场景。
     * 如果数据源不是文本，就只能使用InputStream，如果数据源是文本，使用Reader更方便一些。
     *
     * 使用Reader，数据源虽然是字节，但我们读入的数据都是char类型的字符，原因是Reader内部把读入的byte做了解码，转换成了char
     * 使用InputStream，我们读入的数据和原始二进制数据一模一样，是byte[]数组，但是我们可以自己把二进制byte[]数组按照某种编码转换为字符串。
     * */

    /**
     * 同步和异步
     * 同步IO是指，读写IO时代码必须等待数据返回后才继续执行后续代码，它的优点是代码编写简单，缺点是CPU执行效率低。
     * 异步IO是指，读写IO时仅发出请求，然后立刻执行后续代码，它的优点是CPU执行效率高，缺点是代码编写复杂。
     * Java标准库的包java.io提供了同步IO，而java.nio则是异步IO，上述讨论的都是同步
     *
     *
     */

    public static void main(String args[]) throws IOException {

        /**
         * 传入数据，传入文件路径
         */

        //构造File对象时，既可以传入绝对路径，也可以传入相对路径。绝对路径是以根目录开头的完整路径，例如：
        File f = new File("C:\\Windows\\notepad.exe");
        //传入相对路径传时，相对路径前面加上当前目录就是绝对路径
        // 假设当前目录是C:\Docs,对应的绝对路径就是C:\Docs\sub\javac
        File f1 = new File("sub\\javac");
        // 绝对路径是C:\sub\javac
        File f3 = new File("..\\sub\\javac");

        System.out.println(f);

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
         *     // TODO:
         *     if (file.delete()) {
         *         // 删除文件成功:
         *     }
         * }
         *
         * //创建临时文件并将它删除
         * // 提供临时文件的前缀和后缀
         * File f = File.createTempFile("tmp-", ".txt");
         * // JVM退出时自动删除
         * f.deleteOnExit();
         *
         */
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
         * 	        File[] fs1 = f.listFiles(); // 列出所有文件和子目录
         * 	        printFiles(fs1);
         * 	            }
         *
         * 	 static void printFiles(File[] files) {
         * 	        System.out.println("==========");
         * 	        if (files != null) {
         * 	            for (File f : files) {
         * 	            	if(f.isDirectory())
         *                    {
         * 	            		 File[] fs1 = f.listFiles();
         * 	            		 printFiles(fs1);
         *                    }
         * 	                System.out.println(f);
         *                }
         *            }
         * 	        System.out.println("==========");
         *        }
         */

        /**
         * input stream的Fileinputstream的read方法，close，缓冲、阻塞区与实现
         *
         * InputStream并不是一个接口，而是一个抽象类，它是所有输入流的超类。这个抽象类定义的一个最重要的方法就是int read()，签名如下：
         *public abstract int read() throws IOException;
         * 这个方法会读取输入流的下一个字节，并返回字节表示的int值（0~255）。如果已读到末尾，返回-1表示不能继续读取了。
         *如何读取文件的示例如下：
         */

        /*
        //也许会存在错误不能读取，所以方法必须抛异常
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
         *         while ((n = input.read(buffer)) != -1) { // 读取到缓冲区
         *             System.out.println("read " + n + " bytes.");
         *         }
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
        try (InputStream input = new FileInputStream("C:\\test\\README.txt")) {
            int n;
            StringBuilder sb = new StringBuilder();
            while ((n = input.read()) != -1) {
                sb.append((char) n);
            }
            s = sb.toString();
        }


        /**
         * outputstream 有一个flush方法强制把缓冲区的内容输出，不然就是缓冲区满了才会输出。
         *
         */

        /**
         * output stream实现类
         */



    }






    }



