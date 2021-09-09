package api;

import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {

    @Test
    public void test1() {
        QuickSort quickSort = new QuickSort();

        int[] array = {5, 4, 3, 2, 1};
        quickSort.MySort(array);
        Arrays.stream(array).forEach(r -> System.out.println(r));
    }


    public void MySort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public void quickSort(int[] array, int leftIndex, int rightIndex) {
        //递归结束条件
        if (leftIndex >= rightIndex) {
            return;
        }
        // 左边的挖空保留
        int baseValue = array[leftIndex];

        int leftInner = leftIndex;
        int rightInner = rightIndex;

        while (leftInner < rightInner) {
            while (rightInner > leftInner && array[rightIndex] > baseValue) {
                // 大于base的都直接跳过，不操作
                rightInner--;
            }
            // 当找到了一个 小于 base 的数值时，需要放到 left 中去
            // 并且这是右边的数值就空出来了
            if (rightInner > leftInner) {
                array[leftInner] = array[rightInner];
            }

            while (leftInner < rightInner && array[leftInner] < baseValue) {
                // 左边的小于base 的都直接跳过，不操作
                leftInner++;
            }

            if (leftInner >= rightInner) {
                // 当左右指标都重合的时候，说明左右都已经和base  分隔完成
                // 将 刚才挖出来的 base 放到 分隔点
                array[leftInner] = baseValue;
            }
        }
        quickSort(array, leftIndex, leftInner - 1);
        quickSort(array, leftInner + 1, rightIndex);

    }


}








