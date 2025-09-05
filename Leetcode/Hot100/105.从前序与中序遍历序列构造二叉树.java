/*
 * @lc app=leetcode.cn id=105 lang=java
 *
 * [105] 从前序与中序遍历序列构造二叉树
 */

// @lc code=start

import java.util.Map;

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

    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    //记录中序遍历的节点在数组中的位置
    private void prepare(int[] inorder){
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
    }
    //prel, prer 左闭右开，代表前序遍历的范围
    //inl, inr 左闭右开，代表中序遍历的范围
    //返回prel到prer范围内的节点构造的树的根节点
    private TreeNode dfs(int[] preorder, int prel, int prer, int inl, int inr){
        //空节点
        if(prel == prer)return null;
        
        //根节点是prel位置的节点
        //左子树节点个数=中序遍历中根节点位置-中序遍历开始位置
        int leftSize = map.get(preorder[prel]) - inl;
        //递归构造左子树和右子树
        //左子树在前序遍历中的位置是[prel+1, prel+1+leftSize)
        //左子树在中序遍历中的位置是[inl, inl+leftSize)
        //右子树在前序遍历中的位置是[prel+1+leftSize, prer)
        //右子树在中序遍历中的位置是[inl+1+leftSize, inr)
        TreeNode left = dfs(preorder, prel + 1, prel + 1 + leftSize, inl, inl + leftSize);
        TreeNode right = dfs(preorder, prel + 1 + leftSize, prer, inl + 1 + leftSize, inr);
        return new TreeNode(preorder[prel], left, right);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        prepare(inorder);
        return dfs(preorder, 0, preorder.length, 0, inorder.length);
    }
}
// @lc code=end

