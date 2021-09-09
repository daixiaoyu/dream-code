### 解题思路
![image.png](https://pic.leetcode-cn.com/1620630918-PVcEtx-image.png)
一开始暴力写的，两个for虽然也过了，但是时间空间可怜，就用双指针写了一个：

**主要思路**:先排序再从0位置开始循环当前位置、当前位置+1和最后位置这三个数，看和是否为0
**具体思路：**
1.排序
2.for循环从数组0坐标开始遍历
（1）如果当前位置不是数组起始位置0并且当前的i位置的数组值和前一i位置数组值相同，则跳过本次for循环（因为和前一回合的结果重复）
（2）如果当前的i位置数组值都大于0了，直接返回结果（因为数组排序了,当前大于0，则加后面的两个数字总和肯定大于零）
（3）其他情况下，初始化l和r (l = i + 1; r = nums.length - 1;) ,当l < r执行（sum = nums[i] + nums[l] + nums[r]）：
- ①sum = 0：则把此时的三个数加入到结果List中，然后l++,r--（并且需要while()一下当此时新的l位置数组值和r位置数组值不能与之前的l、r位置相同，说明结果是重复的，就继续l++，r--，直到与之前不同或者l >=r 了，结束）
- ②sum > 0: 说明此时的r位置数组值太大，所以r--
- ③sum < 0: 说明此时的l位置数组值太小，所以l++



### 代码

```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int l, r, sum, n = nums.length;
        Arrays.sort(nums);
        
        for(int i = 0; i < n; i++){
            if(i != 0 && nums[i] == nums[i - 1]) continue;
            else if(nums[i] > 0) return result;
            else{
                l = i + 1;
                r = n - 1;
                while(l < r){
                    sum = nums[i] + nums[l] + nums[r];
                    if(sum == 0){
                        result.add(Arrays.asList(nums[i], nums[l++], nums[r--])); 
                        while(l < r && nums[l] == nums[l - 1]) l++;
                        while(l < r && nums[r] == nums[r + 1]) r--;
                    }
                    else if(sum < 0) l++;
                    else r--;              
                }
            }  
        }
        return result;
    }
}
```