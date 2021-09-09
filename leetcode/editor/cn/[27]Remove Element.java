//Given an array nums and a value val, remove all instances of that value in-pla
//ce and return the new length. 
//
// Do not allocate extra space for another array, you must do this by modifying 
//the input array in-place with O(1) extra memory. 
//
// The order of elements can be changed. It doesn't matter what you leave beyond
// the new length. 
//
// Clarification: 
//
// Confused why the returned value is an integer but your answer is an array? 
//
// Note that the input array is passed in by reference, which means a modificati
//on to the input array will be known to the caller as well. 
//
// Internally you can think of this: 
//
// 
//// nums is passed in by reference. (i.e., without making a copy)
//int len = removeElement(nums, val);
//
//// any modification to nums in your function would be known by the caller.
//// using the length returned by your function, it prints the first len element
//s.
//for (int i = 0; i < len; i++) {
//    print(nums[i]);
//} 
//
// 
// Example 1: 
//
// 
//Input: nums = [3,2,2,3], val = 3
//Output: 2, nums = [2,2]
//Explanation: Your function should return length = 2, with the first two elemen
//ts of nums being 2.
//It doesn't matter what you leave beyond the returned length. For example if yo
//u return 2 with nums = [2,2,3,3] or nums = [2,2,0,0], your answer will be accept
//ed.
// 
//
// Example 2: 
//
// 
//Input: nums = [0,1,2,2,3,0,4,2], val = 2
//Output: 5, nums = [0,1,4,0,3]
//Explanation: Your function should return length = 5, with the first five eleme
//nts of nums containing 0, 1, 3, 0, and 4. Note that the order of those five elem
//ents can be arbitrary. It doesn't matter what values are set beyond the returned
// length.
// 
//
// 
// Constraints: 
//
// 
// 0 <= nums.length <= 100 
// 0 <= nums[i] <= 50 
// 0 <= val <= 100 
// 
// Related Topics 数组 双指针 
// 👍 782 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 使用一个指针：notEqualIndex 来记录当前和 val 不相同的index 开始为 0
     * 遍历数组： 当发现 与 val 不同的值时，需要保留，且移动至 notEqualIndex 的位置 ，并且将 notEqualIndex 的指向一个 用来存储的空间
     * 注意：由于 遍历到 数组最后 一个与 val 不相同的数时也会将 notEqualIndex ++ ，所以返回 数组 size 的时候，不需要 进行 +1 了
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        int notEqualIndex = 0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] != val) {
                nums[notEqualIndex] = nums[i];
                notEqualIndex++;
            }
        }
        return notEqualIndex;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
