/*
 * @lc app=leetcode.cn id=543 lang=java
 *
 * [543] 二叉树的直径
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
    private int max_depth = 0;

    private int depth(TreeNode node){
        if(node == null)return -1;

        int l = depth(node.left) + 1;
        int r = depth(node.right) + 1;
        max_depth = Math.max(max_depth, l + r);
        return Math.max(l, r);
    }

    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return max_depth;
    }
}
// @lc code=end

