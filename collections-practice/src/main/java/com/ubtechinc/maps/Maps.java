package com.ubtechinc.maps;

import java.util.*;

/**
 * Maps接口相关的数据结构特性及查、增、删、改的调用
 * Maps里面主要是键值对的存储，常见的有Hashmap，TreeMap，Hashtable
 *
 * @author MacBook Air
 * @date 2025/7/31 3:15
 */
public class Maps {

    /**
     *
     *
     * 特点总结：
     * Hashtable：
     * •  迭代顺序：无序（Random）
     * •  效率：操作效率高，时间复杂度为 O(1)
     * •  是否允许 null：不允许 null key/value
     * •  线程安全性：线程安全(多个线程同时访问时，不会出现数据不一致、覆盖、丢失等问题)，但效率不如 ConcurrentHashMap，已逐渐被弃用
     *    同步（Synchronized）：是实现线程安全的一种方式（通过加锁控制访问顺序），这里不是自动更新的意思，线程安全不等于自动更新，但可以保证数据不产生冲突
     * •  底层实现：Buckets（数组 + 链表）
     *
     * •  适用场景：早期多线程应用，但现在推荐使用 ConcurrentHashMap 替代
     *
     * TreeMap：
     * •  迭代顺序：按 key 的自然顺序（Sorted） 排序
     * •  效率：操作效率略低，时间复杂度为 O(log n)（基于红黑树）
     *
     * 稍微复习一下这里：
     * The question is, how many times can you divide N by   2   until you have   1  ?
     * In a formula this would be this:  1   = N /   2** x
     * Observe   2 **x   = n, log2   (2  **x)   = log2 n,  x = log n iterations carried out
     *
     * •  是否允许 null：不允许 null key（否则会抛出 NullPointerException）
     * ，允许 null value
     * •  实现接口：Map, SortedMap, NavigableMap
     * •  线程安全性：非线程安全，需自己同步
     * •  底层实现：红黑树（BST）。它不是数组+红黑树这样的模式，它自己就是一个红黑树，每个节点保存一个键值对Entry<key,value>
     * •  适用场景：需要保持 key 有序时（如字典排序、区间查找）
     * •  备注：有额外维护排序的成本
     *
     *
     * HashMap：
     * •  迭代顺序：无序（Random）
     * •  效率：操作效率高，get/put/remove/containsKey 时间复杂度为 O(1)
     * •  是否允许 null：允许 1个 null key 和多个 null values
     * •  线程安全性：非线程安全（这里线程和锁有一个大致印象，先不深入了解，下方有一个简单的例子），可通过 Collections.synchronizedMap(new HashMap()) 实现同步
     * •  底层实现：Buckets（数组 + 链表+红黑树）
     *    HashMap 底层是数组 + 链表，每个 key 通过 hashCode() 计算一个桶（slot）的位置，如果多个 key 冲突，就在该桶后面挂上一个链表，也就是之前所学的chaining
     *    如果某个桶（bucket）中的链表长度超过一定值（说明冲突太多），那么链表会被转换成红黑树，提升查找效率从 O(n) → O(log n)；
     *    如果这个桶后来又变短（，可能还会退回链表，这种优化是局部的，不是整个 HashMap
     * •  适用场景：最常用、效率最高的 Map 类型，适用于单线程、无顺序要求的情况
     * •  备注：高效（Efficient）
     *
     * LinkedHashMap：
     * •  迭代顺序：按插入顺序排序
     * •  效率：操作效率与 HashMap 相同，时间复杂度为 O(1)
     * •  是否允许 null：允许 null key 和 null value
     * •  线程安全性：非线程安全，可以使用 Collections.synchronizedMap(...) 实现同步
     * •  底层实现：HashMap + 双向链表（doubly linked list of buckets，有prev和next指针可以来回跑）
     * •  适用场景：比treemap节省资源，需要保持插入顺序的 Map，如缓存（可配合 accessOrder 构造 LRU 缓存）
     * •  备注：有序又高效，结合了 HashMap 和 TreeMap 的优点
     */

    /**
     * 基础数据结构特性回顾：
     *
     * | 数据结构                  | 搜索（Search）  | 插入（Insert）      | 删除（Delete）        | 特性说明                       |
     * | --------------------- | ----------- | --------------- | ----------------- | -------------------------- |
     * | **ArrayList**         | O(n) ，索引查找O（1）       | O(1) *(尾部)*     | O(n) ,集体右移*(中间)*       | 动态数组，查找慢但添加快（尾部）           |
     * | **LinkedList**        | O(n)        | O(1) *(头/尾)*,中间先搜再删O(n)    | O(1) *(头/尾)*      | 链表结构，适合频繁插入删除              |
     * | **HashMap**           | O(1) *(平均)* | O(1) *(平均)*     | O(1) *(平均)*       | 哈希冲突时最差可达 O(n)，支持 null key |
     * | **Hashtable**         | O(1) *(平均)* | O(1) *(平均)*     | O(1) *(平均)*       | 线程安全但性能差，已被淘汰              |
     * | **ConcurrentHashMap** | O(1) *(平均)* | O(1) *(平均)*     | O(1) *(平均)*       | 多线程下高性能的 HashMap 替代品       |
     * | **TreeMap**           | O(logn)    | O(logn)        | O(logn)          | 有序，基于红黑树                   |
     * | **HashSet**           | O(1) *(平均)* | O(1) *(平均)*     | O(1) *(平均)*       | 基于 HashMap                 |
     * | **TreeSet**           | O(logn)    | O(logn)        | O(logn)          | 基于 TreeMap，自动排序            |
     * | **PriorityQueue**     | O(n) *(查找)* | O(logn) *(添加)* | O(logn) *(删除最值)* | 堆结构，适用于 top-k 场景           |
     * | **Stack**             | O(1)        | O(1)            | O(1)              | 后进先出                       |
     * | **Queue**             | O(1)        | O(1)            | O(1)              | 先进先出                       |
     */

    /**
     * 总体的集合类接口的特点总结：
     * | 结构    | 实现类                            | 特点                   |
     * | ----- | ------------------------------ | -------------------- |
     * | Map   | `HashMap`                      | 无序、快速查找、允许 null key  |
     * | Map   | `TreeMap`                      | 按 key 有序、O(logn) 性能 |
     * | List  | `ArrayList`                    | 查找快(索引查找）、增删慢（中间）          |
     * | List  | `LinkedList`                   | 查找慢、插入删除快            |
     * | Set   | `HashSet`                      | 无序去重                 |
     * | Set   | `TreeSet`                      | 自动排序去重               |
     * | Queue | `LinkedList` / `PriorityQueue` | FIFO / 小顶堆           |
     */


    public static class Student {
        public String name;
        public int score;

        public  Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return String.format("{%s: score=%d}", name, score);
        }

        public static void main(String[] args) {

            /**
             * hashtable实例
             * .put(key,value)
             * .get(key)
             * .containsValue(value)
             * .containsKey(key)
             * Enumeration aaa = xxx.elements();
             * enumeration.nextElement()
             * .isEmpty()
             * .size()
             *
             *
             */

            Hashtable<String, String> companies = new Hashtable<>();

            //insert to map, put(key,value)
            companies.put("Sony", "Japan");

            // get(key) method is used to retrieve Objects from Hashtable
            companies.get("Google");

            //remove all entries or all mapping from Hashtable and then re-use it like a new Hashtable
            //companies.clear();

            //check if it contains a particular value
            boolean checkValue = companies.containsValue("Japan");

            //check if it contains a particular key
            boolean checkKey = companies.containsKey("Google");

            //traverse hashtable in java
            // Hashtable enumeration Example
            // hashtable.elements() return enumeration of all hashtable values
            Enumeration<String> enumeration = companies.elements();

            while (enumeration.hasMoreElements()) {
                System.out.println("hashtable values: " + enumeration.nextElement());
            }

            // use isEmpty method of hashtable to check emptiness of hashtable
            System.out.println("Is companies hashtable empty: " + companies.isEmpty());

            // use hashtable.size() method to find size of hashtable in Java
            System.out.println("Size of hashtable in Java: " + companies.size());

            /**
             * Hashmap实例，调用方法名和hashtable差不多
             * 未特别标明则全部为O(1)
             * put(K key, V value) 插入/修改键值
             *  get(Object key) 获取 key 对应的 value
             *
             *  remove(Object key) 删除键值
             *  containsKey(Object key)
             *  containsValue(Object value)
             *  keySet() 返回所有 key 的集合
             *  Set<K> O(n)  values() 返回所有 value 的集合O(n)
             *  entrySet() 返回所有 Map.Entry<K,V> 的集合 O(n)
             *  clear() 清空所有键值对
             *  size() 返回映射大小
             *  isEmpty()
             *  putIfAbsent(K key, V value) 若 key 不存在才放入
             *  replace(K key, V value) 替换已有 key 对应的 value
             */

            HashMap<String, String> map = new HashMap<>();
            map.put("apple", "fruit");
            map.get("apple");
            map.remove("apple");
            map.put("pear", "good");
            //good
            System.out.println(map.get("pear"));
            map.put("pear", "bad");
            //bad,只能有唯一key，再加值会被冲掉
            System.out.println(map.get("pear"));

            //hashmap iterator实例
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("apple", "fruit");

            //如果要同时遍历键值
            Iterator<Map.Entry<String, String>> transverse = hashMap.entrySet().iterator();
            while (transverse.hasNext()) {
                //Entry是Map里面的静态接口，需要import
                Map.Entry<String, String> entry = transverse.next();
                //此处getKey()，getValue()是Map.entry里面的方法
                System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());

            }
            //只遍历值
            for (String key : hashMap.keySet()) {
                String value = hashMap.get(key);
                System.out.println(key + " = " + value);
            }
            //对应8.4有一个练习可以做一下


            /**
             * hashmap线程不安全的例子:两个线程同时写入 HashMap，出现了覆盖、冲突或写丢了的情况
             *
             * Map<Integer, String> map = new HashMap<>();
             *
             *         Thread t1 = new Thread(() -> {
             *             for (int i = 0; i < 1000; i++) {
             *                 map.put(i, "Thread1-" + i);
             *             }
             *         });
             *
             *         Thread t2 = new Thread(() -> {
             *             for (int i = 1000; i < 2000; i++) {
             *                 map.put(i, "Thread2-" + i);
             *             }
             *         });
             *
             *         t1.start();
             *         t2.start();
             *         t1.join();
             *         t2.join();
             *
             *         // 预期是 2000 个 key，但由于线程不安全，实际数量可能少
             *         System.out.println("Map size: " + map.size());
             *     }
             */

            /**
             * enum map
             * 如果作为key的对象是enum类型，以使用Java集合库提供的一种EnumMap
             * 它在内部以一个非常紧凑的数组存储value，并且根据enum类型的key直接定位到内部数组的索引，并不需要计算hashCode()
             * 不但效率最高，而且没有额外的空间浪费
             *
             */
            //EnumMap<K, V> 要求K是enum类型，而这里是String，所以会被标黄
            //Map<String, Integer> enumMap = new EnumMap<>(Maps.class);

            //如果要通过map调用enummap的话必须是Map<Maps, xxx> enumMap = new EnumMap<>(Maps.class);这样,并且声明enum枚举类，或者调用库里面已经存在的类
            //enum声明有它要求的格式
            enum defineMap {
                A("a"), B("b"), C("c");

                private final String value;

                defineMap(String value) {
                    this.value = value;
                }

                public String getValue() {
                    return value;
                }
            }


            // 正确的泛型声明
            Map<defineMap, String> enumMapNew = new EnumMap<>(defineMap.class);
            enumMapNew.put(defineMap.B, "b");
            System.out.println(enumMapNew.get(defineMap.B));

            /**
             * Treemap
             * treemap接口继承自comparable。使用TreeMap时，放入的Key必须实现Comparable接口。
             * String、Integer这些类已经实现了Comparable接口，因此可以直接作为Key使用。作为Value的对象则没有任何要求。
             * 如果作为Key的class没有实现Comparable接口，那么，必须在创建TreeMap时同时指定一个自定义排序算法
             * 使用TreeMap时，对Key的比较需要正确实现相等、大于和小于逻辑,所以compare类中一定要定义好大于等于小于的情况
             */

            Map<Student, Integer> mapTree = new TreeMap<>(new Comparator<Student>() {
                @Override
                public int compare(Student p1, Student p2) {
                    if (p1.score == p2.score) {
                        return 0;
                    }
                    return p1.score > p2.score ? -1 : 1;
                }
                // 这里，匿名类结束要有 `})`
            });


            mapTree.put(new Student("Tom", 77), 1);
            mapTree.put(new Student("Bob", 66), 2);
            mapTree.put(new Student("Lily", 99), 3);
            for (Student key : mapTree.keySet()) {
                System.out.println(key);
            }


        }


    }
}
