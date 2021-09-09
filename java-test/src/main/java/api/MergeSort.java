package api;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeSort {
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        //int a = in.nextInt();
        //System.out.println(a);
        Map<Character, String> result = parseData("AmeituanBv5.0CbaiduDandroid");
        System.out.println(result);
    }


    public static Map<Character, String> parseData(String data) {

        Map<Character, String> map = new HashMap(5);

        StringBuilder cache = new StringBuilder();
        Character start = null;

        for (int i = 0; i < data.length(); i++) {
            // 开头

            Character now = data.charAt(i);
            if (i == 0) {
                start = now;
                //判断是否是大写
            } else if (now>='A' && now<='Z') {
                map.put(start, cache.toString());
                cache = new StringBuilder();
                start = now;
            }else {
                cache.append(now);
            }

        }

        return map;
    }

}




