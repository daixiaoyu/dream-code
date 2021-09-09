//把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 [3,4,5,1,2
//] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。 
//
// 示例 1： 
//
// 输入：[3,4,5,1,2]
//输出：1
// 
//
// 示例 2： 
//
// 输入：[2,2,2,0,1]
//输出：0
// 
//
// 注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-sor
//ted-array-ii/ 
// Related Topics 二分查找 
// 👍 314 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int minArray(int[] numbers) {

    }
}
//leetcode submit region end(Prohibit modification and deletion)


class Solution {
    public int minArray(int[] numbers) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            // 获取中间节点的公式 : 基准 low + (high - low) / 2
            int mid = low + (high - low) / 2;
            // 如果中间 小于 最高 ： 最小肯定在 <= mid  ，于是将最高 赋值为 mid
            if (numbers[mid] < numbers[high]) {
                high = mid;


            } else if (numbers[mid] > numbers[high]) {
                // 如果中间 大于 最高:  mid 是左边 小的，可以不管，直接将 mid +1
                low = mid + 1;


            } else {
                // 如果 mid 等于 最高
                high -= 1;
            }
        }
        return numbers[low];
    }
}




