package collection;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class ApiTest {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    @Test
    public void testLindList() {
        LinkedList<Integer> linkedList = new LinkedList();

        for (int i = 0; i < 5; i++) {
            linkedList.offerFirst(i);
        }

        while (!linkedList.isEmpty()) {
            System.out.println(linkedList.pollLast());
        }
    }

    @Test
    public void testArrayList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
    }

    @Test
    public void testHashMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
    }


    /**
     * hashMap 中的方法
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

}
