package com.ubtechinc.excel.write;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.alibaba.excel.EasyExcel;
import com.ubtechinc.excel.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 *创建excel file并在里面写东西
 * /// <a href="https://blog.csdn.net/weixin_72979483/article/details/128676578">...</a>
 * @author MacBook Air
 * @date 2025/8/12 23:04
 */

public class WriteExcel {

    public  static void main(String[] args) {

        //只要不改文件名且设置创建的路径相同，那再run的时候就不会创建新文件，只会把旧文件替换掉，不多占内存
        String fileName = "Student.xlsx";

        //先创建想要装进excel file的那些内容
        List<Student> array = new ArrayList<Student>();
        array.add(new Student("王二",19));
        array.add(new Student("李三",30)) ;

        //使用EasyExcel提供的方法写入excel。
        //write方法里的两个参数是 文件名 和 模板类。
        //sheet方法里面对参数是excel表格下面的标签名。
        //doWrite方法的参数是 要写入的数据
        EasyExcel.write(fileName, Student.class).sheet("学生表").doWrite(array);

        /**
         * 如果我想要指定excel保存的路径那我该怎么办
         */

        //在桌面生成
        String filePath = Paths.get(System.getProperty("user.home"), "Desktop", "Student.xlsx").toString();
        //避免误覆盖
        if (!Files.exists(Paths.get(fileName))) {
        EasyExcel.write(filePath, Student.class).sheet("学生表").doWrite(array);
        }

        //在文件夹指定处生成
        String fileOutput = "target/Student.xlsx";
        File parentDir = new File(Paths.get(fileOutput).getParent().toString());
        // 创建 target 目录
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        EasyExcel.write(fileOutput, Student.class).sheet("学生表").doWrite(array);

        /**
         * 调用stream，生成一个数据量超大的excel
         */


        /**
         * https://blog.csdn.net/qq_41413619/article/details/98736554?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-98736554-blog-90814287.235^v43^pc_blog_bottom_relevance_base8&spm=1001.2101.3001.4242.1&utm_relevant_index=3
         */





    }
}
