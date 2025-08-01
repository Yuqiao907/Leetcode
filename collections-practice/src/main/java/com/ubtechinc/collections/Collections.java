package com.ubtechinc.collections;

import java.util.*;

/**
 * 集合类的概念：可存储多种数据类型，也可以存储基本数据类型，对象，最重要的是，它的长度可变，删减相对容易
 * 与之相比，数组：只能存储基本的数据类型，而且长度一开始就被定义，不可变，增删改不易
 * <p>
 * 集合类主要实现两个接口，Collections和Maps
 * Collections里面基本都是元素的储存，Maps里面是键值对
 * Collections中经常使用的有Set, List, Queue
 */


/**
 *Collections接口相关的数据结构特性及查、增、删、改的调用
 * @author MacBook Air
 * @date 2025/7/31 3:15
 */

public class Collections {

    /**
     * Set接口类
     */

    /**
     * Hashset, 快速查找元素，基于hash算法实现
     *从存储架构上来说：it is nothing but a hashmap with the same value for every key
     *
     * 看源码就能发现Hashset里基本都是在调用hashmap的方法，hashset基于hashmap实现（源码如下），所以底层的实现逻辑和hashmap一样
     *private transient HashMap<E,Object> map;
     * private static final Object PRESENT = new Object();
     *
     * public boolean add(E e) {
     *     return map.put(e, PRESENT) == null;
     * }
     *
     * 不同的是hashset的值是作为hashmap的key被存进去的，而值是一个设定好的无法改变常量，所以如果要储存key-value就不能用hashset
     * 从存储架构上来说：it is nothing but a hashmap with the same value for every key
     * 由于hashmap的key不允许重复（不能有两个相同的slot但是被分配到相同的slot的哈希冲突是允许的），所以hashset的值也不会重复
     *
     * <p>
     * 复习一下hashing的概念：
     * Hash function h: Mapping from U to the slots
     * with arrays, key k maps to slot A[ k]
     * with hash tables, key k maps or “hashes” to slot T[h[k]].
     * 简单来说就是通过hash function得到一个hash value分配给key A，h[ k] is the hash value of key k，相当于index
     * <p>
     * 这个hashvalue又会有collision的问题 (multiple key apply to the same slot since having same hash value)
     * 解决方法包括chaining（store to same slot in linkedlist)
     * 或者是 open addressing (consistent procedure to restore to nearby free slot)
     * <p>
     * 查找删除非常迅速，基本都是O(1)
     *
     */

    /**
     * treeset,其与treemap的关系与hashset--hashmap的关系是一样的，其同样的原因不能有重复。
     * 不同的是它不能有null，因为要比较
     *
     */


    public static void main(String[] args) {

        /**
         * 基本hashset、treeset的几个方法名字差不多
         *
         * Hashset
         * .add()
         * .isEmpty()
         * .size()
         * .clear()
         * .contains(Object)
         * .toArray()
         * .remove(object)
         */

        //hashset实例，如果加入重复的元素，重复的不会被储存
        //不标明hashset里面是什么也是可以的 Hashset hashset= new Hashset<>();

        HashSet<String> hashset = new HashSet<>();

        hashset.add("Sony");
        //iterator
        Iterator<String> tranverse = hashset.iterator();

        while (tranverse.hasNext()) {
            System.out.println(tranverse.next());
        }





        //treeset实例，会自然排序，同样不能重复

        TreeSet <String> treeSet = new TreeSet<>();
        treeSet.add("D");
        treeSet.add("C");
        //打出来会是CD
        System.out.println(treeSet);

        /**
         * 对于treeset的自然排序，有时候我们会想要重新定义，那就会另开一个类去override它输入的对象的compareTo（）方法
         * 具体例子在https://www.cnblogs.com/jyroy/p/11191791.html
         */


        /**
         * List 接口类，有序链表
         */
        //通过List接口的方法.of()，根据给定元素快速创建list,这个list不可变，不可改，不属于 ArrayList、LinkedList、Vector等子类
        List<Integer> listOf = List.of(1, 2, 5);

        //把list变为array这里先不看
        //equals改写先不看




        /**
         * ArrayList，有序，非线程安全,删减不易，进行remove和add的时候会拷贝数组的数据，影响效率，
         * 具体使用可看Maps.java里面的对比
         * <p>
         * | 方法                      | 功能说明           | 操作类型 | 时间复杂度       |
         * | ----------------------- | -------------- | ---- | ----------- |
         * | `add(E e)`              | 添加元素到末尾        | 增    | O(1) *(均摊)* |
         * | `add(int index, E e)`   | 在指定位置插入元素      | 增    | O(n)        |
         * | `remove(Object o)`      | 删除指定元素（第一次出现）  | 删    | O(n)        |
         * | `remove(int index)`     | 删除指定索引位置的元素    | 删    | O(n)        |
         * | `get(int index)`        | 根据索引获取元素       | 查    | O(1)        |
         * | `set(int index, E e)`   | 设置指定索引位置的值     | 改    | O(1)        |
         * | `contains(Object o)`    | 判断是否包含某元素      | 查    | O(n)        |
         * | `indexOf(Object o)`     | 返回第一次出现该元素的索引  | 查    | O(n)        |
         * | `lastIndexOf(Object o)` | 返回最后一次出现该元素的索引 | 查    | O(n)        |
         * | `clear()`               | 清空所有元素         | 工具   | O(1)        |
         * | `size()`                | 返回元素个数         | 查    | O(1)        |
         * | `isEmpty()`             | 判断是否为空         | 查    | O(1)        |
         * | `toArray()`             | 转为数组           | 查    | O(n)        |
         * | `iterator()`            | 获取迭代器          | 遍历   | O(1)        |
         * | `listIterator()`        | 获取支持双向迭代的迭代器   | 遍历   | O(1)        |
         */

        List <String> array = new ArrayList<>();
        array.add("A");

        /**
         * Vector 与arraylist的区别是vector线程安全并且，但也因此arraylist的查询效率比vector要高
         *
         */
        List <String> list = new Vector<>();

        /**
         * Linkedlist基于双向链表实现，链表的特点在这里不赘述了，较之ArrayList更占内存
         *
         */
        LinkedList<String> listNew = new LinkedList<>();
        listNew.add("B");
        int size= listNew.size();
        System.out.println(listNew);






        /**
         * Queue类
         *先进先出，删除都是在队首，添加在队尾，不支持随机访问，不支持根据索引修改中间元素
         *
         */

        /**
         * LinkedList and PriorityQueue
         *
         * | 方法                   | 功能说明                  | 操作类型 | 时间复杂度 |
         * | -------------------- | --------------------- | ---- | ----- |
         * | `offer(E e)`         | 向队尾添加元素（推荐）           | 增    | O(1)  |
         * | `add(E e)`           | 向队尾添加元素，若失败抛异常        | 增    | O(1)  |
         * | `poll()`             | 获取并移除队头元素（为空返回 null）  | 删    | O(1)  |
         * | `remove()`           | 获取并移除队头元素（为空抛异常）      | 删    | O(1)  |
         * | `peek()`             | 获取队头元素但不移除（为空返回 null） | 查    | O(1)  |
         * | `element()`          | 获取队头元素但不移除（为空抛异常）     | 查    | O(1)  |
         * | `isEmpty()`          | 判断队列是否为空              | 查    | O(1)  |
         * | `size()`             | 返回队列中元素个数             | 查    | O(1)  |
         * | `contains(Object o)` | 判断是否包含指定元素            | 查    | O(n)  |
         * | `clear()`            | 清空队列                  | 工具   | O(1)  |
         * | `iterator()`         | 返回队列迭代器               | 遍历   | O(n)  |
         */

        Queue<String> queue = new LinkedList<>();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        String head =queue.poll();
        System.out.println(head);

        Iterator<String> it = queue.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        Queue<String> queueNew = new PriorityQueue<>();
        queueNew.add("a");

        Iterator<String> transverse = queueNew.iterator();
        while (transverse.hasNext()) {
            System.out.println(transverse.next());
        }

    }


}
