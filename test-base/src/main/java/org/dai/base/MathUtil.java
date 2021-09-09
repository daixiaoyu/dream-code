package org.dai.base;

import java.util.Random;

public class MathUtil {

    public static int random(int range) {
        Random random = new Random();
        int i = random.nextInt(range);
        if (i == 0) {
            i = 1;
        }
        return i;
    }


    public static String random(String pre, int range) {
        int random = random(range);
        return pre + random;
    }
}
