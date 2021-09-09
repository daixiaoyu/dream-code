//给定一个单链表 L：L0→L1→…→Ln-1→Ln ， 
//将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→… 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 示例 1: 
//
// 给定链表 1->2->3->4, 重新排列为 1->4->2->3. 
//
// 示例 2: 
//
// 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3. 
// Related Topics 链表 
// 👍 573 👎 0


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
class Solution {
    public void reorderList(ListNode head) {
        if (null == head) {
            return;
        }
        ListNode newHead = new ListNode(0, head);
        ListNode slow = newHead;
        ListNode fast = newHead;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode rightStart = slow.next;

        // 切断源头
        slow.next = null;
        slow = newHead.next;
        rightStart = rever(rightStart);

        while (slow != null && rightStart != null) {
            ListNode slowNext = slow.next;
            ListNode rightNext = rightStart.next;

            slow.next = rightStart;
            rightStart.next = slowNext;

            slow = slowNext;
            rightStart = rightNext;
        }
    }

    public ListNode rever(ListNode head) {
        ListNode tail = null;
        ListNode newHead = null;
        while (head != null) {
            ListNode nextTemp = head.next;
            head.next = tail;
            tail = head;
            newHead = head;
            head = nextTemp;
        }
        return newHead;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
