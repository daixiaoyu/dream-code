//Given a sorted array of distinct integers and a target value, return the index
// if the target is found. If not, return the index where it would be if it were i
//nserted in order. 
//
// 
// Example 1: 
// Input: nums = [1,3,5,6], target = 5
//Output: 2
// Example 2: 
// Input: nums = [1,3,5,6], target = 2
//Output: 1
// Example 3: 
// Input: nums = [1,3,5,6], target = 7
//Output: 4
// Example 4: 
// Input: nums = [1,3,5,6], target = 0
//Output: 0
// Example 5: 
// Input: nums = [1], target = 0
//Output: 0
// 
// 
// Constraints: 
//
// 
// 1 <= nums.length <= 104 
// -104 <= nums[i] <= 104 
// nums contains distinct values sorted in ascending order. 
// -104 <= target <= 104 
// 
// Related Topics 数组 二分查找 
// 👍 835 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int ans = nums.length;
        int right = ans - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target){
                return mid;
            }

            if ( nums[mid] > target) {
                right = mid - 1;
                ans = mid;
            }

            if ( nums[mid] < target) {
                left = mid + 1;
            }
        }

        return ans;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
