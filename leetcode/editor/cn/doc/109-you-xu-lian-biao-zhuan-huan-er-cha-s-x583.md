# [109. 有序链表转换二叉搜索树](https://leetcode-cn.com/problems/convert-sorted-list-to-binary-search-tree/)
***
### 思路
其实蛮简单的，就是找链表中间节点作为根节点，然后再找中点两边的子链表的中点，一直递归下去直到子链表为空

1. 特例处理：如果head为空返回空，如果head.next为空返回值为head.val的树节点
2. 利用快慢指针找链表中间节点（slow每次走一步，fast每次走两步，循环停止时slow指向中间节点）
同时记录一下slow的前一个节点pre，这是为后面的断开操作做准备
3. 创建树的根节点，把slow的值赋给它，并断开链表中间节点和左边子链表的连接
4. 递归链表中间节点左右两边的子链表，找子链表的中间节点，再找子链表的子链表的中间节点，如此循环往复，直到符合特例处理的条件递归返回

### 代码
```java []
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        //1. 特例处理
        if (head == null){
            return null;
        }else if(head.next == null){
            return new TreeNode(head.val);
        }
        //2. 利用快慢指针找链表中间节点
        ListNode slow = head, fast = head;
        ListNode pre = null;
        while( fast != null && fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        //3. 创建树的根节点，并断开相应连接
        TreeNode root = new TreeNode(slow.val);
        pre.next = null;

        //4. 递归链表中间节点左右两边的子链表
        root.left = sortedListToBST(head);
        root.right = sortedListToBST(slow.next);
        return root;
    }
}
```

```python3 []
class Solution:
    def sortedListToBST(self, head: ListNode) -> TreeNode:
        if not head:
            return None
        elif not head.next:
            return TreeNode(head.val)
        
        pre, slow, fast = None, head, head
        while fast and fast.next:
            pre = slow
            slow = slow.next
            fast = fast.next.next
        
        root = TreeNode(slow.val)
        pre.next = None

        root.left = self.sortedListToBST(head)
        root.right = self.sortedListToBST(slow.next)
        return root
```
**复杂度分析**
- 时间复杂度：*O(n)*，链表遍历
- 空间复杂度：*O(logn)*，平衡二叉树的高度*O(logn)*，也就是递归过程中栈的最大深度，即需要的空间
