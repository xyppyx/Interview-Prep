/*
 * @lc app=leetcode.cn id=124 lang=java
 *
 * [124] 二叉树中的最大路径和
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
    private Map<TreeNode, Integer> map = new HashMap<TreeNode, Integer>();
    private int ans = Integer.MIN_VALUE;

    private int getMax(TreeNode root){
        if(root == null)return 0;
        int left = getMax(root.left);
        int right = getMax(root.right);
        ans = Math.max(ans, left + right + root.val);
        return Math.max(0, Math.max(left, right) + root.val);
    }

    public int maxPathSum(TreeNode root) {
        getMax(root);
        return ans;
    }
}
// @lc code=end

