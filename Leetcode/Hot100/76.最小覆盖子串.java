/*
 * @lc app=leetcode.cn id=76 lang=java
 *
 * [76] 最小覆盖子串
 */

// @lc code=start
class Solution {
    public String minWindow(String S, String t) {
        int delta[] = new int['z' + 1];//子串中字母x的数量-t中字母x的数量
        int less = 0;//子串缺多少种字母
        int ansl = -1, ansr = S.length();
        for(char c : t.toCharArray()){
            if(delta[c] == 0)
                ++less;
            --delta[c];
        }

        char[] s = S.toCharArray();

        int l = 0;
        for(int r = 0; r < s.length; r++){
            char c = s[r];
            ++delta[c];
            if(delta[c] == 0)--less;
            while(less == 0){
                if(r - l < ansr - ansl){
                    ansr = r;
                    ansl = l;
                }
                char x = s[l];
                if(delta[x] == 0)++less;
                --delta[x];
                ++l;
            }
        }

        return ansl < 0 ? "" : S.substring(ansl, ansr + 1);
    }
}
// @lc code=end

