package com.ubtechinc.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
/**
 *用easyexcel读取、写入excel文件，这里是创建student结构
 * @author MacBook Air
 * @date 2025/8/12 6:07
 */



public class Student {


    //当读取 Excel 时，程序会在 Excel 第一行找到列标题为 "姓名" 的列，把这一列的数据填充到 name 字段
    //绑定表头名
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private int age;

    //EasyExcel 在读取时会用 反射 去创建 Student 对象，而它需要调用 无参构造方法（public Student()）
    // 如果Student 类没有无参构造，反射会无法实例化对象。

    public Student(){

    }

    public  Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{"+
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }




}
