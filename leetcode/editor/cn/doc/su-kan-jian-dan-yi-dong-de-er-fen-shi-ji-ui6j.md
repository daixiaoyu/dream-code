### 解题思路
![image.png](https://pic.leetcode-cn.com/1620796489-FnsJcD-image.png)
每次对比都是**左侧元素**和**中间元素**对比，**共三种情况**：
**1.左 == 中：**此时最小值**可能在[左，mid]中**，**也可能在[mid + 1, r]中**，更新一下结果 ，左 = 左 + 1
**2.左 < 中：**此时最小值**仅可能**是**左或在[mid + 1，r]中**，更新一下结果 ，左 = mid + 1
**3.左 > 中：**此时最小值**仅可能**是**中或在[l，mid - 1]中**，更新一下结果 ，右 = mid - 1

**三种情况例子：**
**1.**[3，**1**，3，3，3]或[3，3，3，3，**2**]，3 == 3(nums[0] == nums[2])，最小值1在[左，mid]中 **或** 最小值2在[mid + 1, r]中
**2.**[**1**，2，3，4，5]或[1，2，3，4，**0**]，1 < 3(nums[0] < nums[2])，最小值1是左 **或** 最小值0在[mid + 1，r]中
**3.**[3，4，**2**，3，3]或[3，**1**，2，3，3]，3 > 2(nums[0] > nums[2])，最小值2是中 **或** 最小值1在在[l，mid - 1]中

### 代码

```java
class Solution {
    public int minArray(int[] numbers) {
        int n = numbers.length;
        int result = numbers[0];
        int l = 0, r = n - 1;
        while(l <= r){
            int mid = (l + r + 1) >> 1;
            if(numbers[l] == numbers[mid]){
                result = Math.min(result, numbers[mid]); 
                l++;
            }else if(numbers[l] < numbers[mid]){
                result = Math.min(result, numbers[l]); 
                l = mid  + 1;
            }else{
                result = Math.min(result, numbers[mid]);
                r = mid  - 1;
            }
        }
        return result;
    }
}
```