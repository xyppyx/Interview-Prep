/*
 * @lc app=leetcode.cn id=3 lang=java
 *
 * [3] 无重复字符的最长子串
 */

// @lc code=start

import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        Set<Character> set = new HashSet<Character>();
        
        int l = 0, r = 0;
        while(r < s.length()){
            if(!set.contains(s.charAt(r))){
                set.add(s.charAt(r));
                ans = Math.max(ans, r - l + 1);
                ++r;
            }else{
                while(set.contains(s.charAt(r))){
                    set.remove(s.charAt(l));
                    ++l;
                }
            }
        }
        return ans;
    }
}
// @lc code=end

