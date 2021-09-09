**解题思路**
* 使用`int[26]`数组表示单词中a-z字母的个数，int数组中值相同的元素，放到同一个组。
* 使用HashMap，key为表征字母个数的这个数组，value为`List<String>`用来存储相同表示的字符串。
* 之后的过程就容易了，依次统计各个字符串的`int[]`，存到HashMap中，相同的分到一组。
* 由于直接使用`int[]`作为HashMap的key值，equals方法比较的是地址值。应该想办法使之比较值。所以要把整型数组转换成字符串（字符串重写了equals的方法）。也可以自己定义一个Key的类，重写equals的方法（我就是这么的死脑筋做的）。

```
class Solution {
    class Key{
        public int[] arr;
        public Key(int[] arr){
            this.arr = arr;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            return Arrays.equals(arr, key.arr);
        }
        @Override
        public int hashCode() {
            return Arrays.hashCode(arr);
        }
    }
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<Key,List<String>> map = new HashMap<>();
        for(String str : strs){
            int[] com = new int[26];
            char[] arr = str.toCharArray();
            for(int i=0;i<arr.length;i++){
                com[arr[i] - 'a']++;
            }
            Key curKey = new Key(com);
            map.computeIfAbsent(curKey,o -> new ArrayList<String>()).add(str);
        }
        List<List<String>> res = new ArrayList<>();
        for(Map.Entry<Key,List<String>> entry : map.entrySet()){
            List<String> cur = new ArrayList();
            for(String str : entry.getValue()){
                cur.add(str);
            }
            res.add(cur);
        }
        return res;
    }
}
```