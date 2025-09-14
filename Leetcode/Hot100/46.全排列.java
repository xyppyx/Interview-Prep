/*
 * @lc app=leetcode.cn id=46 lang=java
 *
 * [46] 全排列
 */

// @lc code=start

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    private List<List<Integer>>ans = new ArrayList<List<Integer>>();

    private void dfs(int[]nums, Set<Integer> set, List<Integer> res){
        if(res.size() == nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i : nums){
            if(set.contains(i))continue;
            set.add(i);
            res.add(i);
            dfs(nums, set, res);
            res.removeLast();
            set.remove(i);
        }
    }

    public List<List<Integer>> permute(int[] nums) {

        List<Integer> res = new ArrayList<Integer>();
        Set<Integer> set = new HashSet<Integer>();
        dfs(nums, set, res);

        return ans;
    }
}
// @lc code=end

