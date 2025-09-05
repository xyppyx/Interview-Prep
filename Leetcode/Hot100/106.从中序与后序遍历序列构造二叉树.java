/*
 * @lc app=leetcode.cn id=106 lang=java
 *
 * [106] 从中序与后序遍历序列构造二叉树
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
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    //记录某个节点在中序遍历中的下标
    private void prepare(int[] inorder){
        for(int i = 0 ; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
    }

    //获取中序遍历区间[inl, inr]
    //后序遍历区间[postl, postr]
    //对应的树的根节点
    private TreeNode dfs(int[] postorder, int inl, int inr, int postl, int postr){
        if(inl > inr)return null;

        int rightSize = inr - map.get(postorder[postr]);

        TreeNode left = dfs(postorder, inl, inr - rightSize - 1, postl, postr - rightSize - 1);
        TreeNode right = dfs(postorder, inr - rightSize + 1, inr, postr - rightSize, postr - 1);

        return new TreeNode(postorder[postr], left, right);
    }

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        prepare(inorder);
        return dfs(postorder, 0, inorder.length - 1, 0, inorder.length - 1);
    }
}
// @lc code=end

