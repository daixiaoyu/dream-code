//给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。 
//
// 你应当 保留 两个分区中每个节点的初始相对位置。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,4,3,2,5,2], x = 3
//输出：[1,2,2,4,3,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [2,1], x = 2
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 200] 内 
// -100 <= Node.val <= 100 
// -200 <= x <= 200 
// 
// Related Topics 链表 双指针 
// 👍 393 👎 0


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
    public ListNode partition(ListNode head, int x) {
        //特判
        if (head == null) {
            return head;
        }

        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode node1 = newHead;

        ListNode node2 = new ListNode(0);
        ListNode node2Head = node2;

        while (node1 != null && node1.next != null) {
            if (node1.next.val >= x) {
                ListNode next = node1.next;
                node1.next = next.next;
                node2.next = next;
                node2 = next;
                node2.next = null;
            } else {
                node1 = node1.next;
            }
        }
        node1.next = node2Head.next;
        return newHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
