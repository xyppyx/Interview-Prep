/*
 * @lc app=leetcode.cn id=102 lang=java
 *
 * [102] 二叉树的层序遍历
 */

// @lc code=start

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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        if(root == null)return ans;
        else q.offer(root);

        while(!q.isEmpty()){
            List<Integer> tmp = new ArrayList<Integer>();
            int cur_cnt = q.size();
            for(int i = cur_cnt; i >= 1; i--){
                TreeNode node = q.poll();
                tmp.add(node.val);
                if(node.left != null)q.offer(node.left);
                if(node.right != null)q.offer(node.right);
            }
            ans.add(tmp);
        }

        return ans;
    }
}
// @lc code=end

