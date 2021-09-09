//反转一个单链表。 
//
// 示例: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 5->4->3->2->1->NULL 
//
// 进阶: 
//你可以迭代或递归地反转链表。你能否用两种方法解决这道题？ 
// Related Topics 链表 
// 👍 1696 👎 0


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
    public ListNode reverseList(ListNode head) {
        ListNode newHead = null;
        help(head, newHead);
        return newHead;
    }

    public void help(ListNode head, ListNode newHead) {
        if (null == head) {
            return;
        }
        ListNode next = head.next;
        head.next = newHead;
        newHead = head;
        help(next, newHead);
    }
}

//leetcode submit region end(Prohibit modification and deletion)


//
//class Solution {
//    public ListNode reverseList(ListNode head) {
//        ListNode newHead = new ListNode();
//        help(head, newHead);
//        return newHead.next;
//    }
//
//    public void help(ListNode head, ListNode newHead) {
//        if (head == null) {
//            return;
//        }
//        ListNode newNode = new ListNode(head.val);
//        newNode.next = newHead.next;
//        newHead.next = newNode;
//        help(head.next, newHead);
//    }
//}
//
//class Solution {
//    public ListNode reverseList(ListNode head) {
//        ListNode newHead = new ListNode();
//        while (head != null) {
//            ListNode newNode = new ListNode(head.val);
//            newNode.next = newHead.next;
//            newHead.next = newNode;
//            head = head.next;
//        }
//        return newHead.next;
//    }
//}
//
//
//class Solution {
//    public ListNode reverseList(ListNode head) {
//        ListNode prev = null;
//        ListNode curr = head;
//        while (curr != null) {
//            ListNode next = curr.next;
//            curr.next = prev;
//            prev = curr;
//            curr = next;
//        }
//        return prev;
//    }
//}