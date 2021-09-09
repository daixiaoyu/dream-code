package api;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class TopSort {


    public static void main(String[] args) {
        int[] array = {5, 4, 3, 2, 1};
        TopSort topSort = new TopSort();
        topSort.topSort(array);
        Arrays.stream(array).forEach(r -> System.out.println(r));
    }

    public void topSort(int[] array) {
        int temp;
        int n = array.length;

        for (int i = 0; i < n ; ++i) {

            for (int j = n - 1; j > i; --j) {

                if (array[j] < array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }

        }
    }
}
