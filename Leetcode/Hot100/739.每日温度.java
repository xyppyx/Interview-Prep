/*
 * @lc app=leetcode.cn id=739 lang=java
 *
 * [739] 每日温度
 */

// @lc code=start

import java.util.Deque;
import java.util.List;

class Solution {

    public int[] dailyTemperatures(int[] tem) {
        int n = tem.length;
        int[] ans = new int[n];
        Deque<Integer> stk = new ArrayDeque<>();

        for(int i = 0; i < n; ++i){
            while(!stk.isEmpty() && tem[i] > tem[stk.getLast()]){
                int j = stk.pollLast();
                ans[j] = i - j;
            }
            stk.addLast(i);
        }
        return ans;
    }
}
// @lc code=end

