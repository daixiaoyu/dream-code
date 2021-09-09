package org.gump.test;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.testng.annotations.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class TestCollection {

    @Test
    public void testDeque() {
//        Queue<String> queue =  new ArrayQueue<>(1);

//        ArrayQueue  LinkedList  ArrayDeque

        LinkedList<String> deque = new LinkedList<>();
        int n = 1000000;


        for (int i = 0; i < n; i++) {
            deque.add(String.valueOf(i));
        }

        while (!deque.isEmpty()) {
            System.out.println(deque.poll());
        }

    }

    @Test
    public void testMap() {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        map.put("dai","dai");
    }
}
