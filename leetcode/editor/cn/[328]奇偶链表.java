//给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。 
//
// 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。 
//
// 示例 1: 
//
// 输入: 1->2->3->4->5->NULL
//输出: 1->3->5->2->4->NULL
// 
//
// 示例 2: 
//
// 输入: 2->1->3->5->6->4->7->NULL 
//输出: 2->3->6->7->1->5->4->NULL 
//
// 说明: 
//
// 
// 应当保持奇数节点和偶数节点的相对顺序。 
// 链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。 
// 
// Related Topics 链表 
// 👍 416 👎 0


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

    // 1->2->3->4->5->NULL
    public ListNode oddEvenList(ListNode head) {
        if (null == head) {
            return head;
        }
        ListNode newHead = new ListNode(0, head);
        ListNode rightStart = new ListNode(0);
        ListNode rightNow = rightStart;

        while (head != null && head.next != null) {
            ListNode next = head.next;
            head.next = next.next;
            next.next = null;
            rightNow.next = next;
            rightNow = rightNow.next;
            // 如果head 的下一个已经为空了，则说明到结尾了，不再移动
            if (head.next != null) {
                head = head.next;
            }
        }
        //head 可能为空
        head.next = rightStart.next;
        return newHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)


class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        // 偶数节点的开头
        ListNode evenHead = head.next;
        // 奇数节点 以及 偶数的每个节点
        ListNode odd = head, even = evenHead;

        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}