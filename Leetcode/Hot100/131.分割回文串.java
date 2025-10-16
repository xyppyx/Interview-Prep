/*
 * @lc app=leetcode.cn id=131 lang=java
 *
 * [131] 分割回文串
 */

// @lc code=start

import java.util.List;

class Solution {
    private int n;
    List<List<String>> ans = new ArrayList<>();

    private boolean test(String s) {
        if(s.length() == 1)return true;
        int m = s.length();
        for(int i = 0; i < m; i++){
            if(s.charAt(i) != s.charAt(m - i - 1))return false;
        }
        return true;
    }

    private void dfs(List<String> res, int st, String s){
        if(st >= n){
            ans.add(new ArrayList<>(res));
            return;
        }

        for(int i = st; i < n; i++){
            if(test(s.substring(st, i + 1))){
                res.add(s.substring(st, i + 1));
                dfs(res, i + 1, s);
                res.removeLast();
            }
        }
    }

    public List<List<String>> partition(String s) {
        n = s.length();
        dfs(new ArrayList<String>(), 0, s);

        return ans;
    }
}
// @lc code=end

