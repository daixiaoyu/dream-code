//请完成一个函数，输入一个二叉树，该函数输出它的镜像。 
//
// 例如输入： 
//
// 4 
// / \ 
// 2 7 
// / \ / \ 
//1 3 6 9 
//镜像输出： 
//
// 4 
// / \ 
// 7 2 
// / \ / \ 
//9 6 3 1 
//
// 
//
// 示例 1： 
//
// 输入：root = [4,2,7,1,3,6,9]
//输出：[4,7,2,9,6,3,1]
// 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 1000 
//
// 注意：本题与主站 226 题相同：https://leetcode-cn.com/problems/invert-binary-tree/ 
// Related Topics 树 
// 👍 130 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode mirrorTree(TreeNode root) {

    }
}
//leetcode submit region end(Prohibit modification and deletion)












class Solution {
    public TreeNode mirrorTree(TreeNode root) {
        help(root);
        return root;
    }

    public void help(TreeNode node) {
        if (null == node) {
            return;
        }
        TreeNode left = node.left;

        node.left = node.right;
        node.right = left;

        help(node.left);
        help(node.right);
    }
}


class Solution {
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;
        TreeNode tmp = root.left;
        root.left = mirrorTree(root.right);
        root.right = mirrorTree(tmp);
        return root;
    }
}



class Solution {
    public TreeNode mirrorTree(TreeNode root) {
        if(root == null) return null;
        //防止方法栈溢出
        Stack<TreeNode> stack = new Stack<>() {{ add(root); }};
        while(!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 将层放入栈中，待会 再去 更换子级
            if(node.left != null) stack.add(node.left);
            if(node.right != null) stack.add(node.right);

            // 将当前的左右更换
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
        }
        return root;
    }
}

