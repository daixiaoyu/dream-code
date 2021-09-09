//给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。 
//
// 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。 
//
// 
//
// 示例 1: 
//
// 给定数组 nums = [1,1,2], 
//
//函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。 
//
//你不需要考虑数组中超出新长度后面的元素。 
//
// 示例 2: 
//
// 给定 nums = [0,0,1,1,1,2,2,3,3,4],
//
//函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
//
//你不需要考虑数组中超出新长度后面的元素。
// 
//
// 
//
// 说明: 
//
// 为什么返回数值是整数，但输出的答案是数组呢? 
//
// 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。 
//
// 你可以想象内部操作如下: 
//
// // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
//int len = removeDuplicates(nums);
//
//// 在函数里修改输入数组对于调用者是可见的。
//// 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
//for (int i = 0; i < len; i++) {
//    print(nums[i]);
//}
// 
// Related Topics 数组 双指针 
// 👍 1865 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 已经排序了，则只需要
     * 使用 i 指针记录当前 唯一值的 index
     * 使用 j 指针记录当前 已经遍历到的数组 index
     * <p>
     * 如果 i== j 则表示 当前遍历 到的重复，则不管
     * 如果 i!= j 则表示遍历到了下一个 唯一值，则将 i++,将下一个 唯一值移动到 i++ 中
     *
     * @param nums
     * @return
     */

    public int removeDuplicates(int[] nums) {
        if (nums.length = 0 ) return 0;

        int nowUni = 0;
        for (int j = 1; j < nums.length; i++) {
            if (nums[nowUni] != nums[j]) {
                nowUni++;
                nums[nowUni] == nums[j];
            }
        }
        return nowUni + 1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
