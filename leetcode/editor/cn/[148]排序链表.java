//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。 
//
// 进阶： 
//
// 
// 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
// 
//
// 示例 2： 
//
// 
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 5 * 104] 内 
// -105 <= Node.val <= 105 
// 
// Related Topics 排序 链表 
// 👍 1112 👎 0


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
    public ListNode sortList(ListNode head) {
        return helper(head);
    }

    public ListNode helper(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        /**
         *  1 2 3 4    或    1 2 3 4 5
         *
         * 场景1：同一个起点的话，且都从 1 开始： slow 会是 指向 3
         *
         * 场景2：如果是 虚拟节点开始的话 如 0 ，则 slow 会指向  2 ，一般用于截断的场景 ，但是这样会创建一个虚拟节点浪费内存
         *
         * 场景3：如果fast 先指向 一个next 的话，则 slow 会指向 2 ，可以节约内存，缺点是都会 改变头节点的 指向
         *
         */

        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode rightStart = slow.next;
        // 截断
        slow.next = null;
        // 分治
        ListNode left = sortList(head);
        ListNode right = sortList(rightStart);
        // 合并
        return merge(left, right);

    }

    public ListNode merge(ListNode left, ListNode right) {
        //虚拟头节点
        ListNode h = new ListNode(0);
        ListNode res = h;

        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }

        // 将 left 或者 right 中剩余的 拼接在结果中
        h.next = left != null ? left : right;
        return res.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)


class Solution {
    public ListNode sortList(ListNode head) {

        if (head == null || head.next == null)
            return head;

        // 取中间 = slow
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode tmp = slow.next;

        // 截断
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);


        ListNode h = new ListNode(0);
        ListNode res = h;

        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;
        return res.next;
    }
}