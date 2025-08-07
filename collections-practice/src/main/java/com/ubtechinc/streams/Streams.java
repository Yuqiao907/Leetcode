package com.ubtechinc.streams;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.*;
import java.util.function.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Streams API的运用
 *
 * @author MacBook Air
 * @date 2025/8/4 2:45
 */

public class Streams {
    /**
     * 它只是数据源的一种视图。这里的数据源可以是一个数组，Java容器或I/O channel等。正因如此要得到一个stream通常不会手动创建，而是调用对应的工具方法
     * 不是数据结构，不存储数据，而是对数据源（集合、数组、I/O 等）进行计算。
     * • 惰性求值（Lazy Evaluation）：stream上的操作并不会立即执行，只有等到用户真正需要结果的时候才会执行。
     * • 不可复用：一旦流被消费（终端操作执行），就不能再使用。
     *
     * Stream的操作分为为两类，中间操作(intermediate operations)和结束操作(terminal operations)，二者特点是：
     * 1. 中间操作总是会惰式执行，调用中间操作只会生成一个标记了该操作的新stream，仅此而已。
     * 2. 结束操作会触发实际计算，计算发生时会把所有中间操作积攒的操作以pipeline的方式执行，这样可以减少迭代次数。计算完成之后stream就会失效。
     * 3.返回值为stream的大都是中间操作，否则是结束操作
     *
     * 中间操作常见方法：
     * concat() distinct() filter() flatMap() limit() map() peek()
     * skip() sorted() parallel() sequential() unordered()
     *
     * 结束操作：
     * allMatch() anyMatch() collect() count() findAny() findFirst()
     * forEach() forEachOrdered() max() min() noneMatch() reduce() toArray()
     *
     */

    /**
     * Stream和List也不一样，List存储的每个元素都是已经存储在内存中的某个Java对象
     * 而Stream输出的元素可能并没有预先存储在内存中，而是实时计算出来的，也因此它可以用来“存储”无限个元素
     * 这里的存储打了个引号，是因为元素有可能已经全部存储在内存中，也有可能是根据需要实时计算出来的
     * 这也是为什么要用stream,如果用List表示，即便在int范围内，也会占用巨大的内存，而Stream几乎不占用空间，因为每个元素都是实时计算出来的，用的时候再算
     * <p>
     * Stream的另一个特点是，一个Stream可以轻易地转换为另一个Stream，而不是修改原Stream本身；
     * 一个Stream转换为另一个Stream时，实际上只存储了转换规则，并没有任何计算发生
     * 因此，Stream API的基本用法就是：创建一个Stream，然后做若干次转换，最后调用一个求值方法获取真正计算的结果，示例如下
     */

    /*
      // 创建Stream
      int result = createNaturalStream()
                   // 任意个转换
                  .filter(n -> n % 2 == 0)
                  .map(n -> n * n)
                  .limit(100)
                  // 最终计算结果
                  .sum();
     */

    static class NatualSupplier implements Supplier<Integer> {
        int n = 0;

        @Override
        public Integer get() {
            n++;
            return n;
        }

    }

    //定义supplier
    static class LocalDateSupplier implements Supplier<LocalDate> {
        LocalDate start = LocalDate.of(2020, 1, 1);
        int n = -1;

        @Override
        public LocalDate get() {
            n++;
            return start.plusDays(n);
        }
    }

    public static void main(String[] args) {

        /**
         * 创建stream
         * 1、直接用Stream.of()静态方法，没啥实质性用途，但测试的时候很方便
         */

        Stream<String> stream = Stream.of("A", "B", "C", "D");
        // forEach()方法相当于内部循环调用，
        // 可传入符合Consumer接口的void accept(T t)的方法引用：
        stream.forEach(System.out::println);


        /**
         * 2、基于一个数组或者Collection，这样该Stream输出的元素就是数组或者Collection持有的元素
         * 把数组变成Stream使用Arrays.stream()方法
         * 对于Collection（List、Set、Queue等），直接调用stream()方法就可以获得Stream
         *
         * 上述创建Stream的方法都是把一个现有的序列变为Stream
         */
        Stream<String> streamOne = stream(new String[]{"A", "B", "C"});
        Stream<String> streamTwo = List.of("X", "Y", "Z").stream();
        streamOne.forEach(System.out::println);
        streamTwo.forEach(System.out::println);

        /**
         * 3、基于supplier创造stream
         * 基于Supplier创建的Stream会不断调用Supplier.get()方法来不断产生下一个元素;
         * 这种Stream保存的不是元素，而是算法，它可以用来表示无限序列。
         */
        //创建Stream还可以通过Stream.generate()方法，它需要传入一个Supplier对象
        //Stream<String> s = Stream.generate(Supplier<String> sp);

        Stream<Integer> natual = Stream.generate(new NatualSupplier());
        // 注意：无限序列必须先变成有限序列再打印:
        natual.limit(20).forEach(System.out::println);


        /**
         * 4、创建Stream的第三种方法是通过一些API提供的接口，直接获得Stream。
         * 例如，Files类的lines()方法可以把一个文件变成一个Stream，每个元素代表文件的一行内容
         * try (Stream<String> lines = Files.lines(Paths.get("/path/to/file.txt"))) {
         *     ...
         * }
         * 另外，正则表达式的Pattern对象有一个splitAsStream()方法，可以直接把一个长字符串分割成Stream序列而不是数组
         * Pattern p = Pattern.compile("\\s+");
         * Stream<String> s = p.splitAsStream("The quick brown fox jumps over the lazy dog");
         * s.forEach(System.out::println);
         *
         */

        /**
         * 关于IntStream、LongStream和DoubleStream
         *
         * Java的泛型不支持基本类型，所以我们无法用Stream<int>这样的类型，会发生编译错误。
         * 为了保存int，只能使用Stream<Integer>，但这样会产生频繁的装箱、拆箱操作。
         * 为了提高效率，Java标准库提供了IntStream、LongStream和DoubleStream这三种使用基本类型的Stream，
         * 它们的使用方法和泛型Stream没有大的区别
         *
         */

        // 将int[]数组变为IntStream:
        IntStream is = stream(new int[]{1, 2, 3});
        // 将Stream<String>转换为LongStream:
        LongStream ls = Stream.of("1", "2", "3").mapToLong(Long::parseLong);


        /**
         * map操作：把一个Stream的每个元素一一对应到应用了目标函数的结果上，转换操作，不触发计算
         */

        Stream<Integer> s = Stream.of(1, 2, 3, 4, 5);
        Stream<Integer> s2 = s.map(n -> n * n);


        /**
         * 这是map的方法定义：
         * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
         *
         * <R>：这是一个泛型声明，表示 map() 方法将返回一个 Stream<R> 类型的流，其中 R 是目标类型。
         *
         * Function<? super T, ? extends R> mapper是一个函数式接口 Function 类型的参数。
         * •  ? super T 表示它可以接受 T 或 T 的父类 作为输入。
         * •  ? extends R 表示它返回 R 或 R 的子类。
         * •  简单说：它表示一个可以把 T 类型“转化为” R 类型的函数。
         *
         * 关于java函数式接口的定义：
         * //@FunctionalInterface：表示这是一个“函数式接口”，也就是只包含一个抽象方法（可以用于 Lambda 表达式）
         * @FunctionalInterface
         * //<T, R>:  T：输入类型   R：输出类型
         * public interface Function<T, R> {
         *     // 抽象方法，表示输出为R，T代表类型，t是那个输入类型对应的具体变量：
         *     R apply(T t);
         * }
         *

         */


        /**
         * map对任何对象有用，以下是示例
         */
        List.of("  Apple ", " pear ", " ORANGE", " BaNaNa ")
                //list直接调用stream()方法就可以获得Stream
                .stream()
                // 去空格，等价于：.map(s -> s.trim()),这是一个将 String 类型（T）转换为 String 类型（R）的函数，也就是 Function<T, R>
                .map(String::trim)
                // 变小写
                .map(String::toLowerCase)
                // 打印
                .forEach(System.out::println);

        String[] array = new String[]{" 2019-12-31 ", "2020 - 01-09 ", "2020- 05 - 01 ", "2022 - 02 - 01",
                " 2025-01 -01"};

        // 作为数组储存调动stream，使用map把String[]转换为LocalDate并打印:
        stream(array)
                .map(string -> string.replaceAll("\\s", ""))
                .map(LocalDate::parse)
                .forEach(System.out::println);

        /**
         * filter操作：转换操作，不触发计算
         * filter()操作，就是对一个Stream的所有元素一一进行测试，不满足条件的就被“滤掉”了，剩下的满足条件的元素就构成了一个新的Stream
         */

        /**
         * //filter()方法接收的对象是Predicate接口对象，它定义了一个test()方法，负责判断元素是否符合条件：
         * @FunctionalInterface
         *         public interface Predicate<T> {
         *             // 判断元素t是否符合条件:
         *             boolean test(T t);
         *         }
         */

        //过滤掉奇数
        IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(n -> n % 2 != 0)
                .forEach(System.out::println);

        //应用代码
        Stream.generate(new LocalDateSupplier())
                //限制只生成一月
                .limit(31)
                //只留下周末
                .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                .forEach(System.out::println);

        //使用filter()过滤出成绩及格的同学，并打印出名字

        List<Person> persons = List.of(new Person("小明", 88), new Person("小黑", 62), new Person("小白", 45),
                new Person("小黄", 78), new Person("小红", 99), new Person("小林", 58));
        persons.stream()
                .filter(person -> person.score >= 60)
                .forEach(System.out::println);

        /**
         * reduce操作：聚合操作
         * map()和filter()都是Stream的转换方法，而Stream.reduce()则是Stream的一个聚合方法，
         * 它可以把一个Stream的所有元素按照聚合函数聚合成一个结果
         */

        //求和
        int sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(0, (acc, n) -> acc + n);
        System.out.println(sum);
        //求积：计算求积时，初始值必须设置为1
        int product = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(1, (acc, n) -> acc * n);
        System.out.println(product);


        /**
         * @FunctionalInterface
         * public interface BinaryOperator<T> {
         *     // Bi操作：两个输入T，一个输出T
         *     T apply(T t, T u);
         * }
         *
         * 用for代码改写一下来理解：
         * Stream<Integer> stream = ...
         * int sum = 0;
         * for (n : stream) {
         *     sum = (sum, n) -> sum + n;
         * }
         *
         *
         * //那当没有初始值的时候，Stream的元素有可能是0个，这样就没法调用reduce()的聚合函数了，因此返回Optional对象，需要进一步判断结果是否存在
         *
         * Optional<Integer> opt = stream.reduce((acc, n) -> acc + n);
         * if (opt.isPresent()) {
         *     System.out.println(opt.get());
         * }
         */

        List<String> props = List.of("profile=native", "debug=true", "logging=warn", "interval=500");
        //把 List<String> 转换成 Stream<String>，这样我们就可以用流式操作（如 .map()、.reduce()）来处理每一项
        Map<String, String> map = props.stream()
                // 把k=v转换为Map[k]=v:
                .map(kv -> {
                    String[] ss = kv.split("\\=", 2);
                    return Map.of(ss[0], ss[1]);
                })
                /**
                 * 把所有Map聚合到一个Map:
                 *                 //.reduce(identity, accumulator) 是一个归约操作。
                 *                 // identity: 初始值，空的 HashMap<String, String>。
                 *                 // (m, kv)：m对应的就是new hashmap
                 *                 // •  m 是累积的结果（一个 Map<String, String>）。
                 *                 // •  kv 是流中的下一个单元素 Map（来自 Map.of(...)）。
                 *                 // •  m.putAll(kv)：把当前这个 kv Map 中的所有键值对放入累积结果 m。
                 *                 // •  return m：返回新的累积结果，供下一次合并使用。
                 */

                .reduce(new HashMap<String, String>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        // 打印结果:
        map.forEach((k, v) -> {
            System.out.println(k + " = " + v);
        });

        /**
         *如果说转换操作不会立即执行，聚合操作是会的
         * Stream<Long> s1 = Stream.generate(new NatualSupplier());
         * Stream<Long> s2 = s1.map(n -> n * n);
         * Stream<Long> s3 = s2.map(n -> n - 1);
         * Stream<Long> s4 = s3.limit(10);
         * s4.reduce(0, (acc, n) -> acc + n);
         * 一旦对s4进行reduce()聚合计算，会不断请求s4输出它的每一个元素，一直请求到s1，最后输出一个Java对象
         */

        /**
         * 如果想要输出为集合那就一定不是转换操作，而是聚合操作，因为集合里对应的是不是stream而是一个具体的java对象
         *
         * 虽然 在上述代码里reduce() 能做到收集到 Map 或 List，但它的语义本意是聚合操作，而不是“保存为容器”。
         * 如果本意是把 Stream 保存成集合，就应该用 .collect(Collectors.toXXX())
         *
         * collector方法存储list或者set：
         * 把Stream的每个元素收集到List的方法是调用collect()并传入Collectors.toList()对象，
         * 它实际上是一个Collector实例，通过类似reduce()的操作，
         * 把每个元素添加到一个收集器中（实际上是ArrayList）。
         * 类似的，collect(Collectors.toSet())可以把Stream的每个元素收集到Set中。
         *
         * 关于将stream的元素转换为list 存储，示例如下：
         *
         */
        Stream<String> streamSample = Stream.of("Apple", "", null, "Pear", "  ", "Orange");
        List<String> list = streamSample
                .filter(str -> str != null && !str.isBlank())
                .collect(toList());

        /**
         * 存储数组对象：
         * 把Stream的元素输出为数组和输出为List类似，我们只需要调用toArray()方法，并传入数组的“构造方法
         */

        List<String> listStore = List.of("Apple", "Banana", "Orange");
        //.toArray(...),方法签名为<A> A[] toArray(IntFunction<A[]> generator);
        //它的签名实际上是IntFunction<String[]>定义的String[] apply(int)，即传入int参数，获得String[]数组的返回值
        //String[]::new等价于len -> new String[len]
        //.toArray(String[]::new) → 把流转换为数组，其中 String[]::new 是一种 构造函数引用，告诉 Java： “请帮我新建一个长度等于流大小的 String[] 数组，然后把流中的元素一个个存进去。”
        String[] stringList = listStore
                .stream()
                .toArray(String[]::new);

        /**
         * 输出为map
         */

        Stream<String> streamMap = Stream.of("APPL:Apple", "MSFT:Microsoft");
        Map<String, String> mapConvert = streamMap
                .collect(Collectors.toMap(
                        // 把冒号前面的提出来映射为key:
                        key -> key.substring(0, key.indexOf(':')),
                        // 冒号后面的映射为value:
                        key -> key.substring(key.indexOf(':') + 1)));
        System.out.println(mapConvert);

        /**
         * 分组输出
         * 关于groupby函数
         */

        List<String> listSample = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        //提供两个函数：一个是分组的key，这里使用s -> s.substring(0, 1)，表示只要首字母相同的String分到一组，第二个是分组的value，这里直接使用Collectors.toList()，表示输出为List
        Map<String, List<String>> groups = listSample.stream()
                .collect(Collectors.groupingBy(str -> str.substring(0, 1), toList()));
        System.out.println(groups);

        /**
         * 运行结果：
         * A=[Apple, Avocado, Apricots],
         *     B=[Banana, Blackberry],
         *     C=[Coconut, Cherry]
         */

        /**
         * stream的sort()操作：只是一个转换操作
         * 要求Stream的每个元素必须实现Comparable接口。如果要自定义排序，传入指定的Comparator
         *
         */
        List<String> sortList = List.of("Apple", "Banana", "Blackberry", "Coconut")
                .stream()
                //如果要自定义排序，传入指定的Comparator,例如：.sorted(String::compareToIgnoreCase)
                .sorted()
                .collect(toList());

        /**
         * 去重，对一个Stream的元素进行去重，没必要先转换为Set，可以直接用distinct()
         */
        List.of("A", "B", "A", "C", "B", "D")
                .stream()
                .distinct()
                .collect(toList());

        /**
         * 截取与跳过
         * .limit() .skip()
         */
        List.of("A", "B", "C", "D", "E", "F")
                .stream()
                // 跳过A, B
                .skip(2)
                // 截取C, D, E
                .limit(3)
                .collect(toList());

        /**
         *另外一中并并合的方法，.concat()，并且是中间操作
         *
         */

        Stream<String> stream1 = List.of("A", "B", "C").stream();
        Stream<String> stream2 = List.of("D", "E").stream();
        // 合并:
        Stream<String> streamConcat = Stream.concat(stream1, stream2);
        System.out.println(streamConcat.collect(toList()));

        /**
         * flatMap()，是指把Stream的每个元素（这里是List）映射为Stream，然后合并成一个新的Stream
         *
         * Stream<List<Integer>> s = Stream.of(
         *         Arrays.asList(1, 2, 3),
         *         Arrays.asList(4, 5, 6),
         *         Arrays.asList(7, 8, 9));
         *
         *
         */

        /**
         * 并行：对Stream的元素进行处理是单线程的，即一个一个元素进行处理。
         * 但是很多时候，我们希望可以并行处理Stream的元素，因为在元素数量非常大的情况，并行处理可以大大加快处理速度。
         * 把一个普通Stream转换为可以并行处理的Stream非常简单，只需要用parallel()进行转换
         *
         * Stream<String> s = ...
         * String[] result = s.parallel()
         *                    // 可以进行并行排序
         *                    .sorted()
         *                    .toArray(String[]::new);
         */

        /**
         * 其他聚合方法
         *
         * 除了reduce()和collect()外，Stream还有一些常用的聚合方法：
         *
         *     count()：用于返回元素个数；
         *     max(Comparator<? super T> cp)：找出最大元素；
         *     min(Comparator<? super T> cp)：找出最小元素。
         *
         * 针对IntStream、LongStream和DoubleStream，还额外提供了以下聚合方法：
         *
         *     sum()：对所有元素求和；
         *     average()：对所有元素求平均数。
         *
         * 还有一些方法，用来测试Stream的元素是否满足以下条件：
         *
         *     boolean allMatch(Predicate<? super T>)：测试是否所有元素均满足测试条件；
         *     boolean anyMatch(Predicate<? super T>)：测试是否至少有一个元素满足测试条件。
         *
         * 最后一个常用的方法是forEach()，它可以循环处理Stream的每个元素，我们经常传入System.out::println来打印Stream的元素：
         * Stream<String> s = ...
         * s.forEach(str -> {
         *     System.out.println("Hello, " + str);
         * });
         */


        /**
         * 拥有一些用户，想要根据性别分类成map<key,value>
         */
        List<User> users = List.of(new User(true,31),new User(false,25),new User(false,45),new User(false,78),new User(false,99),new User(false,58));

        Map<Boolean,List<User>> genderGroups = users.stream()
                .collect(Collectors.groupingBy(user -> user.male, toList()));
                System.out.println(genderGroups);

        /**
         * 拥有两组学生，把这两组学生聚到一个组里,根据学生的分数分组,去掉过于低和过于高的分数
         *
         *
         */
        //person有name还有score
        var studentsOne = List.of(new Person("Tom",30), new Person("Sam",90),
                new Person("Amy",99),new Person("Jeremy",80)).stream();
        var studentsTwo = List.of(new Person("Alice",30), new Person("Krolin",90),
                new Person("April",99),new Person("Elisa",77)).stream();
        var streamMerge = Stream.concat(studentsOne, studentsTwo);

        /*
          Map<Integer,List<Person>> scoreMerge = streamMerge
                          .filter(person -> person.score >=50 && person.score<=95)
                          .collect(Collectors.groupingBy(person -> person.score,Collectors.toList()));
         */

        Map<String,List<Person>> scoreMergeTwo = streamMerge
                .filter(person -> person.score >=50 && person.score<=95)
                //如果我想按照分数段来分应该怎么办
                .collect(Collectors.groupingBy(person -> {
                    int score = person.score;
                    if(score < 50) {return "F";}
                    else if(score < 60) {return "M";}
                    return "Pass";}
                        ));


        /**
         * 拥有一组学生，想要按照名字的首字母分组
         */
        var studentsThree = List.of(new Person("tom",30), new Person("Sam",90),
                new Person("Amy",99),new Person("Jeremy",80));

        Map<String,List<Person>> sortByName = studentsThree.stream()
                .collect(Collectors.groupingBy(person -> person.name.toLowerCase().substring(0,1), toList()));
        System.out.println(sortByName);

        /**
         * 拥有一组学生，想要分数的大小降序排列，并输出为list,并且你想自定义comparator
         */

        Comparator<Person> personComparator = new Comparator<Person>(){

            @Override
            public int compare(Person p1, Person p2) {
                if(p1.score < p2.score) {
                    return 1;}
                else if(p1.score > p2.score) {
                    return -1;
                }
                else {
                    return 0;}
            }
        };

        var studentsFour = List.of(new Person("Tom",30), new Person("Sam",90),
                new Person("Amy",99),new Person("Jeremy",80));
        List<Person> sortStudents = studentsFour.stream()
                .sorted((personComparator))
                .collect(Collectors.toList());

        System.out.println(sortStudents);






        /*
        private static class User {
            boolean male;
            int age;

            public User(boolean male, int age) {
                this.male = male;
                this.age = age;
            }

            public boolean isMale() {
                return male;
            }
        }
         */


    }


    private static class User {
        boolean male;
        int age;

        public User(boolean male, int age) {
            this.male = male;
            this.age = age;
        }

        public boolean isMale() {
            return male;
        }
    }

    private static class Person {
        int score;
        String name;

        public Person(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int getScore() {
            return score;
        }


    }


}










