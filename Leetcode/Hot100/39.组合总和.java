/*
 * @lc app=leetcode.cn id=39 lang=java
 *
 * [39] 组合总和
 */

// @lc code=start

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private List<List<Integer>> ans = new ArrayList<>();

    private void dfs(int[] candidates, int target, int start, List<Integer> res) {

        if (target == 0) {
            ans.add(new ArrayList<>(res));
            return;
        }
        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            res.add(candidates[i]);
            dfs(candidates, target - candidates[i], i, res);
            res.remove(res.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        dfs(candidates, target, 0, new ArrayList<>());
        return ans;
    }
}
// @lc code=end