//Given a non-empty array of decimal digits representing a non-negative integer,
// increment one to the integer. 
//
// The digits are stored such that the most significant digit is at the head of 
//the list, and each element in the array contains a single digit. 
//
// You may assume the integer does not contain any leading zero, except the numb
//er 0 itself. 
//
// 
// Example 1: 
//
// 
//Input: digits = [1,2,3]
//Output: [1,2,4]
//Explanation: The array represents the integer 123.
// 
//
// Example 2: 
//
// 
//Input: digits = [4,3,2,1]
//Output: [4,3,2,2]
//Explanation: The array represents the integer 4321.
// 
//
// Example 3: 
//
// 
//Input: digits = [0]
//Output: [1]
// 
//
// 
// Constraints: 
//
// 
// 1 <= digits.length <= 100 
// 0 <= digits[i] <= 9 
// 
// Related Topics 数组 
// 👍 637 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 先转换为 数字，+1 之后，再转换为数组 ？
     * 转换： 逆序 % 10 
     *
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] = digits[i] % 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    
    
}
//leetcode submit region end(Prohibit modification and deletion)
