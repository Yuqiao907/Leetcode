package com.ubtechinc;

/**
 * 创建一个二维数组
 * int[][] matrix = {
 * {1, 2, 3, 4},
 * {5, 6, 7, 8},
 * {9, 10, 11, 12},
 * {13, 14, 15, 16}
 * };
 * 使用多个for循环实现以下条件
 * 1、打印二维数组里的奇数
 * 2、查找特定值 8在二维数组里的位置
 */

/**
 * 关于for continue break的练习
 *
 * @author MacBook Air
 * @date 2025/7/31 2:47
 */

public class ForContinueBreak {


    public static boolean isOdd(int num) {
        return num % 2 != 0;
    }

    /**
     * 如果只是把奇数都打印出来并找出特定值的位置
     *
     * @param num    -target number
     * @param matrix -input matrix
     */
    public static void printAndSearch(int num, int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (isOdd(matrix[i][j])) {
                    System.out.print(matrix[i][j]);
                    System.out.println();
                }
                if (matrix[i][j] == num) {
                    System.out.println("the index is [" + i + "][" + j + "]");

                }
            }
        }

    }

    /**
     * 如果一定要用到continue和break
     *
     * @param num    -target number
     * @param matrix -input matrix
     */
    public static void printAndSearchNew(int num, int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if (matrix[i][j] == num) {
                    System.out.println("the index is [" + i + "][" + j + "]");
                    //break跳出当前循环
                    break;
                }

                if (!isOdd(matrix[i][j])) {
                    //continue跳出当前循环回到for,j++进行下一个循环
                    continue;
                }
                System.out.print(matrix[i][j]);
                System.out.println();
            }
        }
    }


    /**
     * 如果要用带标签的break来跳出外层for循环(continue也可以使用标签这么用）
     * 即打印odd的数但第一次遇到target number报出它的位置后就结束整个循环
     *
     * @param num    -target number
     * @param matrix -input matrix
     */
    public static void printAndSearchBreak(int num, int[][] matrix) {
        out:
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {

                if (matrix[i][j] == num) {
                    System.out.println("the index is [" + i + "][" + j + "]");
                    break out;
                }

                if (!isOdd(matrix[i][j])) {
                    continue;
                }
                System.out.print(matrix[i][j]);
                System.out.println();


            }
        }
    }

    /**
     * 让外层的循环条件表达式的结果受到内层循环体的控制
     *
     * @param num    -target number
     * @param matrix -input matrix
     */
    public static void printAndSearchBreakFlag(int num, int[][] matrix) {
        boolean flag = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == num) {
                    System.out.println("the index is [" + i + "][" + j + "]");
                    flag = true;
                    //退出内层循环
                    break;

                }

                if (!isOdd(matrix[i][j])) {
                    continue;
                }
                System.out.print(matrix[i][j]);
                System.out.println();


            }
            if (flag) {
                //退出外层循环
                break;
            }
        }
    }


    public static void main(String[] args) {

        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 8, 12},
                {13, 14, 8, 16}

        };

        //ForContinue.printAndSearch(8, matrix);
        ForContinueBreak.printAndSearchNew(8, matrix);
        ForContinueBreak.printAndSearchBreak(8, matrix);
        ForContinueBreak.printAndSearchBreakFlag(8, matrix);


    }

}