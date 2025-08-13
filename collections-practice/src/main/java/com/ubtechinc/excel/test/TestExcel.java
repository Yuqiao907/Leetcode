package com.ubtechinc.excel.test;
import com.alibaba.excel.EasyExcel;
import com.ubtechinc.excel.model.Student;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
/**
 *读入excel file,完成read的要求
 * @author MacBook Air
 * @date 2025/8/13 2:20
 */

public class TestExcel {

    public static void main(String[] args) {

        //要读取的文件名字
        String fileName ="target/Student.xlsx";

        //使用EasyExcel表格方法读取数据。
        //read方法里三个参数 文件名 模板类 一个解析监听
        //注意：监听里面的方法是需要我们重写的。

        /**
         * 关于匿名类的使用：
         * 在下列示例中，代码运行时直接创建了 AnalysisEventListener<Student> 的一个子类，并且立刻实例化它
         * •  特点：
         * 1.  没有类名（所以叫匿名类）
         * 2.  可以在创建对象的同时重写接口或父类的方法
         * 3.  这种对象通常是一次性使用的，不会在多个地方复用
         * 4.  必须被 new 出来，所以语法外面要有括号包住（new ... {}）
         *
         * 用匿名内部类的好处是：
         * •  就地实现监听器逻辑
         * •  省去额外类文件
         * •  代码结构集中（读 Excel 的逻辑和监听实现放在一起）
         *
         * 总之监听逻辑比较简单的时候这么做合适，不然还是适合新写一个类implement AnalysisEventListener<Student>()，就像这样：
         * public class MyStudentListener extends AnalysisEventListener<Student> {
         *     ...
         * }
         * 然后把MyStudentListener传入read方法
         */

        /**
         * 关于监听器的概念：当某个事件发生时，自动触发事先注册好的方法去处理这个事件。
         * 这里的 AnalysisEventListener 就是一个监听器：
         *     事件源：Excel 文件的读取过程
         *     事件：读到一行 / 全部读完
         *
         * 监听器方法：
         *invoke() → 监听并处理“读到一行”的事件
         *doAfterAllAnalysed() → 监听并处理“全部读完”的事件
         */

        //Student.class → 指定数据映射的 Java 类，这样 EasyExcel 会自动把每一行数据映射到 Student 对象。
        //new AnalysisEventListener<Student>() { ... } → 匿名内部类，定义读取 Excel 时的回调处理逻辑。
        EasyExcel.read(fileName, Student.class, new AnalysisEventListener<Student>() {
            int num=0;

            //解析一行运行一次此方法。
            @Override
            //student → 这一行的 Excel 数据，已经按 Student.class 自动封装成对象。
            //analysisContext → 解析上下文对象，里面包含一些读取状态信息（比如当前行号等）
            public void invoke(Student student, AnalysisContext analysisContext) {
                num++;
                System.out.println(student.getName()+" "+student.getAge());
            }

            //解析所有数据完成，运行此方法。
            @Override
            //参数 analysisContext 依然是解析上下文对象，这里用得不多
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("读取完成"+"共 "+num+" 条数据");
            }
        })
                //.sheet()：指定要读取的工作表（sheet），不传参数就默认读取第一个 sheet。
                //.doRead()：开始真正执行读取操作
                .sheet("学生表").doRead();





    }

    /**
     * 关于填充excel
     */

    }







