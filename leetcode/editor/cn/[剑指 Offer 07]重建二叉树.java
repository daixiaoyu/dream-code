//输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。 
//
// 
//
// 例如，给出 
//
// 前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
//
// 
//
// 限制： 
//
// 0 <= 节点个数 <= 5000 
//
// 
//
// 注意：本题与主站 105 题重复：https://leetcode-cn.com/problems/construct-binary-tree-from-
//preorder-and-inorder-traversal/ 
// Related Topics 树 递归 
// 👍 442 👎 0


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
    public TreeNode buildTree(int[] preorder, int[] inorder) {

    }
}
//leetcode submit region end(Prohibit modification and deletion)


class Solution {
    private Map<Integer, Integer> indexMap;

    /**
     *
     * @param preorder
     * @param inorder
     * @param preorder_left_index
     * @param preorder_right_index
     * @param inorder_left_index
     * @param inorder_right_index
     * @return
     *
     *
     * // 前序遍历 preorder = [3,9,20,15,7]
     * //中序遍历 inorder = [9,3,15,20,7]
     */
    public TreeNode myBuildTree(int[] preorder, int[] inorder, int preorder_left_index, int preorder_right_index, int inorder_left_index, int inorder_right_index) {
        if (preorder_left_index > preorder_right_index) {
            return null;
        }

        // 前序遍历中的第一个节点就是根节点
        int preorder_root_index = preorder_left_index;
        int rootValue = preorder[preorder_root_index];
        // 在中序遍历中定位根节点
        int inorder_root_index = indexMap.get(rootValue);

        // 先把根节点建立出来
        TreeNode root = new TreeNode(rootValue);
        // 得到左子树中的节点数目 = 中序根index - 中序开头index
        int size_left_subtree = inorder_root_index - inorder_left_index;
        // 递归地构造左子树，并连接到根节点
        // 先序遍历中「从 左边界+1 开始的 size_left_subtree」个元素就对应了中序遍历中「从 左边界 开始到 根节点定位-1」的元素
        root.left = myBuildTree(preorder, inorder, preorder_left + 1, preorder_left_index + size_left_subtree, inorder_left_index, inorder_root_index - 1);
        // 递归地构造右子树，并连接到根节点
        // 先序遍历中「从 左边界+1+左子树节点数目 开始到 右边界」的元素就对应了中序遍历中「从 根节点定位+1 到 右边界」的元素
        root.right = myBuildTree(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_root + 1, inorder_right);
        return root;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        // 构造哈希映射，帮助我们快速定位根节点
        indexMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            indexMap.put(inorder[i], i);
        }
        return myBuildTree(preorder, inorder, 0, n - 1, 0, n - 1);
    }
}

