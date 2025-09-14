/*
 * @lc app=leetcode.cn id=78 lang=java
 *
 * [78] 子集
 */

// @lc code=start

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class Solution {
    private List<List<Integer>> ans = new ArrayList<List<Integer>>();

    private void dfs(int[] nums, int start, List<Integer> res){
        if(start == nums.length){
            ans.add(new ArrayList<>(res));
            return;
        }

        res.add(nums[start]);
        dfs(nums, start + 1, res);
        res.removeLast();

        dfs(nums, start + 1, res);
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<Integer> res = new ArrayList<Integer>();
        dfs(nums, 0, res);
        return ans;
    }
}
// @lc code=end

