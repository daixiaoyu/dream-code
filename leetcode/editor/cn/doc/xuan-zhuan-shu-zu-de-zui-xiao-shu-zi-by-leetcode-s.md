### ðº è§é¢é¢è§£  
![...ferã11 æè½¬æ°ç»çæå°æ°å­.mp4](9b605d4b-03aa-434f-b30f-62580cbc9381)

### ð æå­é¢è§£
#### åè¨

æ¬é¢åã[154. å¯»æ¾æè½¬æåºæ°ç»ä¸­çæå°å¼ II](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/)ãå®å¨ç¸åï¼æ¯ã[153. å¯»æ¾æè½¬æåºæ°ç»ä¸­çæå°å¼](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array/)ãçå»¶ä¼¸ãè¯»èå¯ä»¥åå°è¯ç¬¬ 153 é¢ï¼ä½ä¼å¨æè½¬æ°ç»ä¸­è¿è¡äºåæ¥æ¾çæè·¯ï¼åæ¥å°è¯è§£å³æ¬é¢ã

#### æ¹æ³ä¸ï¼äºåæ¥æ¾

**æè·¯ä¸ç®æ³**

ä¸ä¸ªåå«éå¤åç´ çååºæ°ç»å¨ç»è¿æè½¬ä¹åï¼å¯ä»¥å¾å°ä¸é¢å¯è§åçæçº¿å¾ï¼

![fig1](https://assets.leetcode-cn.com/solution-static/jianzhi_11/1.png)

å¶ä¸­æ¨ªè½´è¡¨ç¤ºæ°ç»åç´ çä¸æ ï¼çºµè½´è¡¨ç¤ºæ°ç»åç´ çå¼ãå¾ä¸­æ åºäºæå°å¼çä½ç½®ï¼æ¯æä»¬éè¦æ¥æ¾çç®æ ã

æä»¬èè**æ°ç»ä¸­çæåä¸ä¸ªåç´  *x***ï¼å¨æå°å¼å³ä¾§çåç´ ï¼å®ä»¬çå¼ä¸å®é½å°äºç­äº *x*ï¼èå¨æå°å¼å·¦ä¾§çåç´ ï¼å®ä»¬çå¼ä¸å®é½å¤§äºç­äº *x*ãå æ­¤ï¼æä»¬å¯ä»¥æ ¹æ®è¿ä¸æ¡æ§è´¨ï¼éè¿äºåæ¥æ¾çæ¹æ³æ¾åºæå°å¼ã

å¨äºåæ¥æ¾çæ¯ä¸æ­¥ä¸­ï¼å·¦è¾¹çä¸º ![\itlow ](./p__it_low_.png) ï¼å³è¾¹çä¸º ![\ithigh ](./p__it_high_.png) ï¼åºé´çä¸­ç¹ä¸º ![\itpivot ](./p__it_pivot_.png) ï¼æå°å¼å°±å¨è¯¥åºé´åãæä»¬å°ä¸­è½´åç´  ![\textit{numbers}\[\textit{pivot}\] ](./p__textit{numbers}_textit{pivot}__.png)  ä¸å³è¾¹çåç´  ![\textit{numbers}\[\textit{high}\] ](./p__textit{numbers}_textit{high}__.png)  è¿è¡æ¯è¾ï¼å¯è½ä¼æä»¥ä¸çä¸ç§æåµï¼

ç¬¬ä¸ç§æåµæ¯ ![\textit{numbers}\[\textit{pivot}\]<\textit{numbers}\[\textit{high}\] ](./p__textit{numbers}_textit{pivot}____textit{numbers}_textit{high}__.png) ãå¦ä¸å¾æç¤ºï¼è¿è¯´æ ![\textit{numbers}\[\textit{pivot}\] ](./p__textit{numbers}_textit{pivot}__.png)  æ¯æå°å¼å³ä¾§çåç´ ï¼å æ­¤æä»¬å¯ä»¥å¿½ç¥äºåæ¥æ¾åºé´çå³åé¨åã

![fig2](https://assets.leetcode-cn.com/solution-static/jianzhi_11/2.png)

ç¬¬äºç§æåµæ¯ ![\textit{numbers}\[\textit{pivot}\]>\textit{numbers}\[\textit{high}\] ](./p__textit{numbers}_textit{pivot}____textit{numbers}_textit{high}__.png) ãå¦ä¸å¾æç¤ºï¼è¿è¯´æ ![\textit{numbers}\[\textit{pivot}\] ](./p__textit{numbers}_textit{pivot}__.png)  æ¯æå°å¼å·¦ä¾§çåç´ ï¼å æ­¤æä»¬å¯ä»¥å¿½ç¥äºåæ¥æ¾åºé´çå·¦åé¨åã

![fig3](https://assets.leetcode-cn.com/solution-static/jianzhi_11/3.png)

ç¬¬ä¸ç§æåµæ¯ ![\textit{numbers}\[\textit{pivot}\]==\textit{numbers}\[\textit{high}\] ](./p__textit{numbers}_textit{pivot}__==_textit{numbers}_textit{high}__.png) ãå¦ä¸å¾æç¤ºï¼ç±äºéå¤åç´ çå­å¨ï¼æä»¬å¹¶ä¸è½ç¡®å® ![\textit{numbers}\[\textit{pivot}\] ](./p__textit{numbers}_textit{pivot}__.png)  ç©¶ç«å¨æå°å¼çå·¦ä¾§è¿æ¯å³ä¾§ï¼å æ­¤æä»¬ä¸è½è½æå°å¿½ç¥æä¸é¨åçåç´ ãæä»¬å¯ä¸å¯ä»¥ç¥éçæ¯ï¼ç±äºå®ä»¬çå¼ç¸åï¼æä»¥æ è®º ![\textit{numbers}\[\textit{high}\] ](./p__textit{numbers}_textit{high}__.png)  æ¯ä¸æ¯æå°å¼ï¼é½æä¸ä¸ªå®çãæ¿ä»£åã![\textit{numbers}\[\textit{pivot}\] ](./p__textit{numbers}_textit{pivot}__.png) ï¼å æ­¤æä»¬å¯ä»¥å¿½ç¥äºåæ¥æ¾åºé´çå³ç«¯ç¹ã

![fig4](https://assets.leetcode-cn.com/solution-static/jianzhi_11/4.png)

å½äºåæ¥æ¾ç»ææ¶ï¼æä»¬å°±å¾å°äºæå°å¼æå¨çä½ç½®ã

```C++ [sol1-C++]
class Solution {
public:
    int minArray(vector<int>& numbers) {
        int low = 0;
        int high = numbers.size() - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (numbers[pivot] < numbers[high]) {
                high = pivot;
            }
            else if (numbers[pivot] > numbers[high]) {
                low = pivot + 1;
            }
            else {
                high -= 1;
            }
        }
        return numbers[low];
    }
};
```

```Java [sol1-Java]
class Solution {
    public int minArray(int[] numbers) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            if (numbers[pivot] < numbers[high]) {
                high = pivot;
            } else if (numbers[pivot] > numbers[high]) {
                low = pivot + 1;
            } else {
                high -= 1;
            }
        }
        return numbers[low];
    }
}
```

```Python [sol1-Python3]
class Solution:
    def minArray(self, numbers: List[int]) -> int:
        low, high = 0, len(numbers) - 1
        while low < high:
            pivot = low + (high - low) // 2
            if numbers[pivot] < numbers[high]:
                high = pivot 
            elif numbers[pivot] > numbers[high]:
                low = pivot + 1
            else:
                high -= 1
        return numbers[low]
```

```C [sol1-C]
int minArray(int* numbers, int numbersSize) {
    int low = 0;
    int high = numbersSize - 1;
    while (low < high) {
        int pivot = low + (high - low) / 2;
        if (numbers[pivot] < numbers[high]) {
            high = pivot;
        } else if (numbers[pivot] > numbers[high]) {
            low = pivot + 1;
        } else {
            high -= 1;
        }
    }
    return numbers[low];
}
```

```golang [sol1-Golang]
func minArray(numbers []int) int {
	low := 0
	high := len(numbers) - 1
	for low < high {
		pivot := low + (high - low) / 2
        if numbers[pivot] < numbers[high] {
            high = pivot
        } else if numbers[pivot] > numbers[high] {
            low = pivot + 1
        } else {
            high--
        }
	}
	return numbers[low]
}
```

```JavaScript [sol1-JavaScript]
var minArray = function(numbers) {
    let low = 0;
    let high = numbers.length - 1;
    while (low < high) {
        const pivot = low + Math.floor((high - low) / 2);
        if (numbers[pivot] < numbers[high]) {
            high = pivot;
        } else if (numbers[pivot] > numbers[high]) {
            low = pivot + 1;
        } else {
            high -= 1;
        }
    }
    return numbers[low];
};
```

**å¤æåº¦åæ**

* æ¶é´å¤æåº¦ï¼å¹³åæ¶é´å¤æåº¦ä¸º ![O(\logn) ](./p__O_log_n__.png) ï¼å¶ä¸­ *n* æ¯æ°ç» ![\itnumbers ](./p__it_numbers_.png)  çé¿åº¦ãå¦ææ°ç»æ¯éæºçæçï¼é£ä¹æ°ç»ä¸­åå«ç¸ååç´ çæ¦çå¾ä½ï¼å¨äºåæ¥æ¾çè¿ç¨ä¸­ï¼å¤§é¨åæåµé½ä¼å¿½ç¥ä¸åçåºé´ãèå¨æåæåµä¸ï¼å¦ææ°ç»ä¸­çåç´ å®å¨ç¸åï¼é£ä¹ ![\texttt{while} ](./p__texttt{while}_.png)  å¾ªç¯å°±éè¦æ§è¡ *n* æ¬¡ï¼æ¯æ¬¡å¿½ç¥åºé´çå³ç«¯ç¹ï¼æ¶é´å¤æåº¦ä¸º *O(n)*ã

* ç©ºé´å¤æåº¦ï¼*O(1)*ã