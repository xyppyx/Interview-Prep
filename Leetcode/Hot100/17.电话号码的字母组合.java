/*
 * @lc app=leetcode.cn id=17 lang=java
 *
 * [17] 电话号码的字母组合
 */

// @lc code=start
class Solution {
    private Map<Character, char[]> map = new HashMap<>();
    private List<String> ans = new ArrayList<String>();

    private void init(){
        map.put('2', new char[]{'a', 'b', 'c'});
        map.put('3', new char[]{'d', 'e', 'f'});
        map.put('4', new char[]{'g', 'h', 'i'});
        map.put('5', new char[]{'j', 'k', 'l'});
        map.put('6', new char[]{'m', 'n', 'o'});
        map.put('7', new char[]{'p', 'q', 'r', 's'});
        map.put('8', new char[]{'t', 'u', 'v'});
        map.put('9', new char[]{'w', 'x', 'y', 'z'});
    }

    private void dfs(char[] digits, int start, char[] res){
        if(start == digits.length){
            ans.add(new String(res));
            return;
        }

        for(char c : map.get(digits[start])){
            res[start] = c;
            dfs(digits, start + 1, res);
        }
    }

    public List<String> letterCombinations(String digits) {
        if(digits.equals(""))return ans;

        init();

        dfs(digits.toCharArray(), 0, new char[digits.length()]);

        return ans;
    }
}
// @lc code=end

