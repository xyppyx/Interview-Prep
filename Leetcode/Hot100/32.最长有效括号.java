/*
 * @lc app=leetcode.cn id=32 lang=java
 *
 * [32] 最长有效括号
 */

// @lc code=start
class Solution {
    public int longestValidParentheses(String s) {
        int dp[] = new int[s.length()];
        int maxans = 0;
        for (int i = 1; i < s.length(); i++) {
            //只对)进行结算
            if (s.charAt(i) == ')') {
                //直接匹配
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } 
                //))型,和前一个i - dp[i - 1] - 1位置的(匹配
                else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
            }
            maxans = Math.max(maxans, dp[i]);
        }
        return maxans;
    }
}
// @lc code=end

