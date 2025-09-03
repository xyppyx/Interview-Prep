/*
 * @lc app=leetcode.cn id=114 lang=java
 *
 * [114] 二叉树展开为链表
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
    private TreeNode getChain(TreeNode root){
        if(root == null)return null;

        TreeNode lt = getChain(root.left);
        TreeNode rt = getChain(root.right);

        if(lt != null){
            lt.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        
        if(rt != null)return rt;
        else if(lt != null)return lt;
        else return root;
    }

    public void flatten(TreeNode root) {
        getChain(root);
    }
}
// @lc code=end

