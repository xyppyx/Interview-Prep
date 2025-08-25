/*
 * @lc app=leetcode.cn id=438 lang=java
 *
 * [438] 找到字符串中所有字母异位词
 */

// @lc code=start

import java.util.Arrays;

class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        int n = s.length();
        int m = p.length();
        int num_s[] = new int[26];
        int num_p[] = new int[26];
        List<Integer> ans = new ArrayList<Integer>();

        for(char c : p.toCharArray()){
            ++num_p[c - 'a'];
        }

        for(int r = 0; r < n; r++){
            ++num_s[s.charAt(r) - 'a'];
            int l = r - m + 1;
            if(l < 0)continue;
            if(Arrays.equals(num_s, num_p))ans.add(l);
            --num_s[s.charAt(l) - 'a'];
        }    
        return ans;
    }
}
// @lc code=end

