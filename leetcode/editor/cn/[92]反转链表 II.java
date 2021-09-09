//给你单链表的头指针 head 和两个整数 left 和 right ，其中 left <= right 。请你反转从位置 left 到位置 right 的链
//表节点，返回 反转后的链表 。
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4,5], left = 2, right = 4
//输出：[1,4,3,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [5], left = 1, right = 1
//输出：[5]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目为 n 
// 1 <= n <= 500 
// -500 <= Node.val <= 500 
// 1 <= left <= right <= n 
// 
//
// 
//
// 进阶： 你可以使用一趟扫描完成反转吗？ 
// Related Topics 链表 
// 👍 878 👎 0


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
 *
 * 1 2 3 4 5
 *   2   4
 *
 *
 *
 */

class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }

        // 虚拟头结点
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        // 保存开始反转节点 left 的前一个节点
        ListNode pre = newHead;
        for (int i = 1; i < left; i++) {
            pre = pre.next;
        }

        // 开始旋转的开头
        ListNode start = pre.next;
        // 旋转链表的结尾
        ListNode end = start;

        for (int i = 0; i < right - left; i++) {
            end = end.next;
        }

        ListNode cur = end.next;

        // 截断
        pre.next = null;
        end.next = null;

        // 反转链表
        reverge(start);

        pre.next = end;
        start.next = cur;

        return newHead.next;

    }

    public void reverge(ListNode head) {
        ListNode tail = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = tail;
            tail = head;
            head = next;
        }
    }

}
//leetcode submit region end(Prohibit modification and deletion)
