package api;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class ApiTest {
    @Test
    public void testCompute() {
        System.out.println(2 % 2);
    }


    @Test
    public void testCompute1() {
        System.out.println(15 >> 1);
    }


    @Test
    public void testCompute2() {
        int n = 10;
        n |= n >>> 1;
        System.out.println(n);
    }

    @Test
    public void testComputeD() {
        System.out.println(3 / 2);
    }

    @Test
    public void testComputeA() {
        System.out.println(((2 & 1) == 0));
    }


    @Test
    public void testLinkedList() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("c");
        list.add("b");
        String[] strings = list.toArray(new String[list.size()]);
        Arrays.stream(strings).forEach(r -> System.out.println(r));
    }


    @Test
    public void testCompare() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(4);
        list.add(7);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        list.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                System.out.println("o1:" + o1 + ",o2:" + o2);
                System.out.println("o1 - o2 :" + (o1 - o2));
                System.out.println("o2 - o1 :" + (o2 - o1));
                return o2 - o1;
            }
        });

        list.forEach(r -> System.out.println(r));

    }


    public void test() {
        System.out.println("这是一个测试");
    }

}
