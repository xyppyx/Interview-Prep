/*
 * @lc app=leetcode.cn id=889 lang=java
 *
 * [889] 根据前序和后序遍历构造二叉树
 */

// @lc code=start
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = preorder.length;
        int[] index = new int[n + 1];
        for (int i = 0; i < n; i++) {
            index[postorder[i]] = i;
        }
        return dfs(preorder, 0, n, 0, index); // 左闭右开区间
    }

    // 注意 postR 可以省略
    private TreeNode dfs(int[] preorder, int preL, int preR, int postL, int[] index) {
        if (preL == preR) { // 空节点
            return null;
        }
        if (preL + 1 == preR) { // 叶子节点
            return new TreeNode(preorder[preL]);
        }
        int leftSize = index[preorder[preL + 1]] - postL + 1; // 左子树的大小
        TreeNode left = dfs(preorder, preL + 1, preL + 1 + leftSize, postL, index);
        TreeNode right = dfs(preorder, preL + 1 + leftSize, preR, postL + leftSize, index);
        return new TreeNode(preorder[preL], left, right);
    }
}
// @lc code=end

