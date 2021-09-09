//输入一棵二叉树的根节点，求该树的深度。从根节点到叶节点依次经过的节点（含根、叶节点）形成树的一条路径，最长路径的长度为树的深度。 
//
// 例如： 
//
// 给定二叉树 [3,9,20,null,null,15,7]， 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
//
// 返回它的最大深度 3 。 
//
// 
//
// 提示： 
//
// 
// 节点总数 <= 10000 
// 
//
// 注意：本题与主站 104 题相同：https://leetcode-cn.com/problems/maximum-depth-of-binary-tre
//e/ 
// Related Topics 树 深度优先搜索 
// 👍 117 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int maxDepth(TreeNode root) {

    }
}
//leetcode submit region end(Prohibit modification and deletion)


// 递归

class Solution {
    public int maxDepth(TreeNode root) {
        return help(root);
    }

    public int help(TreeNode node) {
        if (null == node) {
            return 0;
        }

        return Math.max(help(node.left), help(node.right)) + 1;
    }
}


class Solution {
    public int maxDepth(TreeNode root) {
        if (null == root) {
            return 0;
        }
        List<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 临时保存下级的指针
        List<TreeNode> tmp;
        int res = 0;

        while (!queue.isEmpty()) {
            tmp = new ArrayList;
            // 将当前queue 中的数据全部遍历完，并将下层的所有node 放入到tmp 中
            for (ListNode node:queue) {
                if (null != node.left) {
                    tmp.add(node.left);
                }
                if (null != node.right) {
                    tmp.add(node.right);
                }
            }
            queue = tmp;
            res++;
        }

        return res;

    }

}












// 层序遍历
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        List<TreeNode> queue = new LinkedList<>() {{
            add(root);
        }}, tmp;
        int res = 0;
        while (!queue.isEmpty()) {
            tmp = new LinkedList<>();
            for (TreeNode node : queue) {
                if (node.left != null) tmp.add(node.left);
                if (node.right != null) tmp.add(node.right);
            }
            queue = tmp;
            res++;
        }
        return res;
    }
}

