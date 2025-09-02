/*
 * @lc app=leetcode.cn id=226 lang=java
 *
 * [226] 翻转二叉树
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
    private TreeNode rotate(TreeNode node){
        if(node == null)return null;
        if(node.left == null && node.right == null)return node;
        rotate(node.left);
        rotate(node.right);
        TreeNode tmp = node.left;
        node.left = node.right;
        node.right = tmp;
        return node;
    }

    public TreeNode invertTree(TreeNode root) {
        return rotate(root);
    }
}
// @lc code=end

