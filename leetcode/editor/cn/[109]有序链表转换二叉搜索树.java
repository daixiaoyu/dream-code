//给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。 
//
// 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。 
//
// 示例: 
//
// 给定的有序链表： [-10, -3, 0, 5, 9]
//
//一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5
// 
// Related Topics 深度优先搜索 链表 
// 👍 513 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
class Solution {

    /**
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        return helper(head);
    }


    public TreeNode helper(ListNode head) {

        if (head == null) {
            return null;
        } else if (head.next == null) {
            // 这里也比较重要，最后中间只剩一个节点时候则返回作为 叶子 节点
            return new TreeNode(head.val);
        }

        ListNode mid = head;
        ListNode fast = head;
        // midPre 用来保存mid 前面那一个
        ListNode midPre = null;
        while (fast != null && fast.next != null) {
            midPre = mid;
            mid = mid.next;
            fast = fast.next.next;
        }

        // 这里截断 的目的是为了 让左边的的子链表 变短
        midPre.next = null;
        TreeNode root = new TreeNode(mid.val);
        root.left = helper(head);
        root.right = helper(mid.next);

        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

// 解法一  递归 找中间节点
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        //1. 特例处理
        if (head == null) {
            return null;
        } else if (head.next == null) {
            return new TreeNode(head.val);
        }
        //2. 利用快慢指针找链表中间节点
        ListNode slow = head, fast = head;
        ListNode pre = null;
        while (fast != null && fast.next != null) {
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


// 解法二

