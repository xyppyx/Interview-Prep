/*
 * @lc app=leetcode.cn id=199 lang=java
 *
 * [199] 二叉树的右视图
 */

// @lc code=start

import java.util.LinkedList;
import java.util.Queue;

import javax.swing.tree.TreeNode;

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
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ans = new ArrayList<Integer>();
        if(root == null)return ans;

        Queue<TreeNode> q = new LinkedList<TreeNode>();

        q.offer(root);
        while(!q.isEmpty()){
            int tmp = q.size();
            for(int i = 1; i <= tmp; i++){
                TreeNode top = q.peek();
                q.poll();
                if(top.left != null)q.offer(top.left);
                if(top.right != null)q.offer(top.right);
                if(i == tmp)ans.add(top.val);
            }
        }

        return ans;
    }
}
// @lc code=end

