/*
 * @lc app=leetcode.cn id=22 lang=java
 *
 * [22] 括号生成
 */

// @lc code=start
class Solution {
    private List<String> ans = new ArrayList<>();

    private void dfs(int left, int right, StringBuilder current) {
        if (left == 0 && right == 0) {
            ans.add(current.toString());
            return;
        }

        if (left > 0) {
            current.append('(');
            dfs(left - 1, right, current);
            current.deleteCharAt(current.length() - 1);
        }

        if (right > left) {
            current.append(')');
            dfs(left, right - 1, current);
            current.deleteCharAt(current.length() - 1);
        }
    }

    public List<String> generateParenthesis(int n) {
        dfs(n, n, new StringBuilder());
        return ans;
    }
}
// @lc code=end

