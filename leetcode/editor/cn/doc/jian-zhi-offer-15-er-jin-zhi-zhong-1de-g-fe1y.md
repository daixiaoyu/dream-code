# [剑指 Offer 15. 二进制中1的个数](https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/)
***
### 思路
1. 核心思路：n & (n - 1) 会把n中的最后一个1变成0
2. 循环中每次去除n的最后一个1，res记录循环次数，就是1的个数

### 代码
```Python3 []
class Solution:
    def hammingWeight(self, n: int) -> int:
        res = 0
        while n:
            n &= n - 1
            res += 1
        return res
```

```Java []
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int res = 0;
        while(n != 0){
            n &= n - 1;
            res++;
        }
        return res;
    }
}
```

**复杂度分析**
- 时间复杂度：*O(m)*，m为1的个数，因为有几个1就会循环几次
- 空间复杂度：*O(1)*