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

    /**
     * 下方hashset应用的方法
     * @param received -输入的短信内容
     * @return message -不重复的短信内容
     */

    static List<Message> process(List<Message> received) {
        //按sequence去除重复消息
        Set<Integer> set = new HashSet<>();
        List<Message> result = new ArrayList<>();
        for(Message message:received){
            if(set.add(message.sequence)){
                result.add(message);
            }
        }
        return result;
    }

    public static class Message {
        public final int sequence;
        public final String text;
        public Message(int sequence, String text) {
            this.sequence = sequence;
            this.text = text;
        }
    }

    public static void main(String[] args) {

        /**
         * 基本hashset、treeset的几个方法名字差不多
         *
         * Hashset
         * .add() return boolean
         * .isEmpty()
         * .size()
         * .clear()
         * .contains(Object) return boolean
         * .toArray()
         * .remove(object) return boolean
         */

        //hashset实例，如果加入重复的元素，重复的不会被储存
        //不标明hashset里面是什么也是可以的 Hashset hashset= new Hashset<>();

        Set<String> hashset = new HashSet<>();

        hashset.add("Sony");

        // false，添加失败，因为元素已存在
        System.out.println(hashset.add("Sony"));

        //iterator

        for (String s : hashset) {
            System.out.println(s);
        }

        //应用：使用hashset去掉重复内容

        List<Message> received = List.of(
                new Message(1, "Hello!"),
                new Message(2, "发工资了吗？"),
                new Message(2, "发工资了吗？"),
                new Message(3, "去哪吃饭？"),
                new Message(3, "去哪吃饭？"),
                new Message(4, "Bye")
        );

        List<Message> displayMessages = process (received);
        for (Message message : displayMessages) {
            System.out.println(message.text);
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
         * List 接口类，有序链表，允许添加重复元素
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
        //允许添加null
        array.add(null);
        //null
        System.out.println(array.get(1));

        //toArray方法，但会丢失类型信息
        List<Integer> listFast = List.of(1,2,3);
        //Object[] arrayNew = listFast.toArray();

        Integer[] arrayNew = listFast.toArray(new Integer[listFast.size()]);

        //array变成list
        Integer[] arrayNum = { 1, 2, 3 };
        List<Integer> list = List.of(arrayNum);




        /**
         * Vector 与arraylist的区别是vector线程安全并且，但也因此arraylist的查询效率比vector要高
         *
         */
        List <String> listVector = new Vector<>();

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
         * | `boolean offer(E e)`         | 向队尾添加元素（推荐）           | 增    | O(1)  |
         * | `E add(E e)`           | 向队尾添加元素，若失败抛异常        | 增    | O(1)  |
         * | `boolean poll()`             | 获取并移除队头元素（为空返回 null）  | 删    | O(1)  |
         * | `E remove()`           | 获取并移除队头元素（为空抛异常）      | 删    | O(1)  |
         * | `boolean peek()`             | 获取队头元素但不移除（为空返回 null） | 查    | O(1)  |
         * | `E element()`          | 获取队头元素但不移除（为空抛异常）     | 查    | O(1)  |
         * | `isEmpty()`          | 判断队列是否为空              | 查    | O(1)  |
         * | `size()`             | 返回队列中元素个数             | 查    | O(1)  |
         * | `contains(Object o)` | 判断是否包含指定元素            | 查    | O(n)  |
         * | `clear()`            | 清空队列                  | 工具   | O(1)  |
         * | `iterator()`         | 返回队列迭代器               | 遍历   | O(n)  |
         */

        Queue<String> queue = new LinkedList<>();
        System.out.println("Below is queue:");
        System.out.println(queue.offer("A"));
        //offer失败时抛false
        if(queue.offer("A")){
            System.out.println("added");
        }
        queue.offer("a");
        //poll失败时候抛null
        String head =queue.poll();
        System.out.println(head);
        //add失败时抛异常
        try{
            queue.add("A");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        queue.clear();
        // 添加3个元素到队列:
        queue.offer("apple");
        queue.offer("pear");
        queue.offer("banana");
        // 队首永远都是apple，因为peek()不会删除它:
        System.out.println(queue.peek());
        // apple
        System.out.println(queue.peek());

        //获取并删除
        // apple
        System.out.println(queue.poll());
        // pear
        System.out.println(queue.poll());

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

        /**
         * Priority Queue
         * PriorityQueue和Queue的区别在于，它的出队顺序与元素的优先级有关
         * 对PriorityQueue调用remove()或poll()方法，返回的总是优先级最高的元素
         * 使用PriorityQueue，我们就必须给每个元素定义“优先级”
         * 要放入的元素并没有实现Comparable接口的话PriorityQueue允许我们提供一个Comparator对象来判断两个元素的顺序
         * 以下是定义优先级的示例
         */

        /*
          class UserComparator implements Comparator<User> {
              public int compare(User u1, User u2) {
                  if (u1.number.charAt(0) == u2.number.charAt(0)) {
                      // 如果两人的号都是A开头或者都是V开头,比较号的大小:
                      return u1.number.compareTo(u2.number);
                  }
                  if (u1.number.charAt(0) == 'V') {
                      // u1的号码是V开头,优先级高:
                      return -1;
                  } else {
                      return 1;
                  }
              }
          }

          class User {
              public final String name;
              public final String number;

              public User(String name, String number) {
                  this.name = name;
                  this.number = number;
              }

              public String toString() {
                  return name + "/" + number;
              }
          }
         */


        /**
         * Deque
         * 既可以添加到队尾，也可以添加到队首
         * 既可以从队首获取，又可以从队尾获取。
         */

        /**
         *  	             Queue 	                                  Deque
         * 添加元素到队尾 	add(E e) / offer(E e) 	addLast(E e) / offerLast(E e)
         * 取队首元素并删除 	E remove() / E poll() 	E removeFirst() / E pollFirst()
         * 取队首元素但不删除 	E element() / E peek() 	E getFirst() / E peekFirst()
         * 添加元素到队首 	无 	addFirst(E e) / offerFirst(E e)
         * 取队尾元素并删除 	无 	E removeLast() / E pollLast()
         * 取队尾元素但不删除 	无 	E getLast() / E peekLast()
         */


    }


}
