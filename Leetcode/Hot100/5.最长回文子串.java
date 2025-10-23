/*
 * @lc app=leetcode.cn id=5 lang=java
 *
 * [5] 最长回文子串
 */

// @lc code=start
class Solution {
    public String longestPalindrome(String s) {
        int n = s.length();
        int mx = 0, st = 0, ed = 0;

        boolean[][] dp = new boolean[n][n];

        for(int i = 0; i < n; i++)dp[i][i] = true;
        for(int i = 0; i + 1 < n; i++){
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if(dp[i][i + 1]){
                mx = 2;
                st = i;
                ed = i + 1;
            }
        }

        for(int len = 3; len <= n; len++){
            for(int i = 0; i + len - 1 < n; i++){
                int j = i + len - 1;
                dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
                if(dp[i][j] && j - i + 1 > mx){
                    mx = j - i + 1;
                    st = i;
                    ed = j;
                }
            }
        }

        return s.substring(st, ed + 1);
    }
}
// @lc code=end

