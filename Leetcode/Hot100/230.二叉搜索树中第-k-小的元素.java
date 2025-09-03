/*
 * @lc app=leetcode.cn id=230 lang=java
 *
 * [230] 二叉搜索树中第 K 小的元素
 */

// @lc code=start

import java.util.HashMap;
import java.util.Stack;

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
/*二叉搜索树中序遍历->递增序列 
class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Stack<TreeNode> stk = new Stack<TreeNode>();
        while(root != null || !stk.isEmpty()){
            while(root != null){
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            --k;
            if(k == 0)break;
            root = root.right;
        }
        return root.val;
    }
}*/

//记录子树节点数
class Solution {
    private Map<TreeNode, Integer> map = new HashMap<TreeNode, Integer>();

    private int getAllCnt(TreeNode root){
        if(root == null)return 0;
        int cnt = getAllCnt(root.left) + getAllCnt(root.right) + 1;
        map.put(root, cnt);
        return cnt;
    }

    private int find(TreeNode root, int k){
        while(root != null){
            int left = map.get(root.left);
            if(left < k - 1){//在右侧
                root = root.right;
                k -= (left + 1);
            }else if(left == k - 1){//在中间
                break;
            }else{//在左侧
                root = root.left;
            }
        }
    
        return root.val;
    }

    public int kthSmallest(TreeNode root, int k) {
        getAllCnt(root);
        map.put(null, 0);
        return find(root, k);
    }
}
// @lc code=end

