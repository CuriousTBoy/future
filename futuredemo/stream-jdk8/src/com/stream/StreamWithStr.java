package com.stream;

import com.stream.bean.Student;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Tanyu
 * @Date 2020/5/15 17:39
 * @Description
 * @Version 1.0
 */
public class StreamWithStr {

  public static void main(String[] args) {

    System.out.println("-----------过滤偶数--------------------");
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      list.add(i);
      list.add(i);
    }
    List<Integer> list1 = list.stream().filter(r -> r % 2 == 0).collect(Collectors.toList());
    System.out.println(Arrays.toString(list1.toArray()));

    System.out.println("-----------去重偶数--------------------");
    List<Integer> list2 = list.stream().filter(r -> r % 2 == 0).distinct()
        .collect(Collectors.toList());
    System.out.println(Arrays.toString(list2.toArray()));

    System.out.println("-----------limit--------------------");
    List<Integer> list3 = list.stream().filter(r -> r % 2 == 0).limit(3)
        .collect(Collectors.toList());
    System.out.println(Arrays.toString(list3.toArray()));

    /**
     * 一个流式处理可以分为三个部分：转换成流、中间操作、终端操作
     */

    // 初始化
    List<Student> students = new ArrayList<Student>() {
      {
        add(new Student(20160001, "孔明", 20, 1, "土木工程", "武汉大学"));
        add(new Student(20160002, "伯约", 21, 2, "信息安全", "武汉大学"));
        add(new Student(20160003, "玄德", 22, 3, "经济管理", "武汉大学"));
        add(new Student(20160004, "云长", 21, 2, "信息安全", "武汉大学"));
        add(new Student(20161001, "翼德", 16, 2, "机械与自动化", "华中科技大学"));
        add(new Student(20161002, "元直", 23, 4, "土木工程", "华中科技大学"));
        add(new Student(20161003, "奉孝", 23, 4, "计算机科学", "华中科技大学"));
        add(new Student(20162001, "仲谋", 22, 3, "土木工程", "浙江大学"));
        add(new Student(20162002, "鲁肃", 23, 4, "计算机科学", "浙江大学"));
        add(new Student(20163001, "丁奉", 24, 5, "土木工程", "南京大学"));
      }
    };

    System.out.println("-----------map--------------------");
    List<String> list4 = students.stream().filter(r -> "计算机科学".equals(r.getMajor()))
        .map(Student::getName).collect(Collectors.toList());
    System.out.println(Arrays.toString(list4.toArray()));

    int sum = students.stream().filter(r -> "计算机科学".equals(r.getMajor()))
        .mapToInt(Student::getAge).sum();
    System.out.println(sum);

    System.out.println("-----------flatMap--------------------");
    String[] strs = {"java8", "is", "easy", "to", "use"};
    List<String[]> list5 = Arrays.stream(strs).map(s -> s.split("")).distinct()
        .collect(Collectors.toList());
    System.out.println(Arrays.toString(list5.toArray()));
    String[] strings = list5.get(1);
    System.out.println(Arrays.toString(list5.get(0)));

    List<String> list6 = Arrays.stream(strs).map(s -> s.split("")).flatMap(Arrays::stream)
        .distinct().collect(Collectors.toList());
    String s = list6.get(0);
    System.out.println(Arrays.toString(list6.toArray()));

    System.out.println("-----------allMatch--------------------");
    boolean b = students.stream().allMatch(student -> student.getAge() >= 18);
    System.out.println(b);
    System.out.println("-----------anyMatch--------------------");
    boolean b1 = students.stream().anyMatch(student -> "计算机科学".equals(student.getMajor()));
    System.out.println(b1);
    System.out.println("-----------findFirst--------------------");
    Optional<Student> first = students.stream().filter(student -> "土木工程".equals(student.getMajor()))
        .findFirst();
    System.out.println(first.get());

    System.out.println("-----------findAny--------------------");
    Optional<Student> any = students.stream().filter(student -> "土木工程".equals(student.getMajor()))
        .findAny();
    System.out.println(any.get());

    System.out.println("-----------归约--------------------");
    // 前面例子中的方法
    int totalAge = students.stream()
        .filter(student -> "计算机科学".equals(student.getMajor()))
        .mapToInt(Student::getAge).sum();
    System.out.println(totalAge);

    // 归约操作
    Integer reduce = students.stream().filter(student -> "计算机科学".equals(student.getMajor()))
        .map(Student::getAge).reduce(0, (a, c) -> a + c);
    System.out.println(reduce);

    // 进一步简化
    Integer reduce1 = students.stream().filter(student -> "计算机科学".equals(student.getMajor()))
        .map(Student::getAge).reduce(0, Integer::sum);
    System.out.println(reduce1);

    // 采用无初始值的重载版本，需要注意返回Optional
    Optional<Integer> reduce2 = students.stream()
        .filter(student -> "计算机科学".equals(student.getMajor()))
        .map(Student::getAge).reduce(Integer::sum);
    System.out.println(reduce2.get());

    System.out.println("-----------收集--------------------");

    System.out.println("-----------3.3.1 归约--------------------");

//    求学生的总人数
    Long collect = students.stream().collect(Collectors.counting());
    long count = students.stream().count();

    System.out.println("collect:" + collect + "\n" + "count:" + count);
    //求年龄的最大值和最小值

    // 求最大年龄
    Optional<Student> maxage = students.stream()
        .collect(Collectors.maxBy((s1, s2) -> s1.getAge() - s2.getAge()));
    System.out.println(maxage);

    // 进一步简化
    Optional<Student> maxage1 = students.stream()
        .collect(Collectors.maxBy(Comparator.comparing(student -> student.getAge())));
    System.out.println(maxage1.get());

    //求年龄总和
    Integer sumAge1 = students.stream().collect(Collectors.summingInt(Student::getAge));
    System.out.println(sumAge1);
    //对应的还有summingLong、summingDouble。

    //求年龄的平均值
    Double averagAge1 = students.stream().collect(Collectors.averagingInt(Student::getAge));
    System.out.println(averagAge1);

    //一次性得到元素个数、总和、均值、最大值、最小值
    IntSummaryStatistics collect1 = students.stream()
        .collect(Collectors.summarizingInt(Student::getAge));
    System.out.println(collect1);

    //字符串拼接
    String s1 = students.stream().map(Student::getName).collect(Collectors.joining());
    System.out.println(s1);
    String s2 = students.stream().map(Student::getName).collect(Collectors.joining(","));
    System.out.println(s2);

    System.out.println("-----------3.3.2 分组--------------------");
    Map<String, List<Student>> listMap = students.stream()
        .collect(Collectors.groupingBy(Student::getSchool));

    listMap.forEach((k,v)-> System.out.println("key:"+k+",value:"+v));

    //上面演示的是一级分组，我们还可以定义多个分类器实现 多级分组，比如我们希望在按学校分组的基础之上再按照专业进行分组
    Map<String, Map<String, List<Student>>> listMap1 = students.stream().collect(
        Collectors.groupingBy(Student::getSchool, Collectors.groupingBy(Student::getMajor)));
    listMap1.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
  }

}
