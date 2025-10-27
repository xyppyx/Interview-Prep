/*
 * @lc app=leetcode.cn id=763 lang=java
 *
 * [763] 划分字母区间
 */

// @lc code=start

import java.util.Map;

class Solution {
    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> last = new HashMap<>();
        int n = s.length();
        List<Integer>ans = new ArrayList<>();

        for(int i = n - 1; i >= 0; i--){
            if(!last.containsKey(s.charAt(i))){
                last.put(s.charAt(i), i);
            }
        }
        
        for(int i = 0; i < n; i++){
            int l = last.get(s.charAt(i));

            for(int j = i + 1; j < l; j++){
                int newl = last.get(s.charAt(j));
                if(newl < l)continue;
                else l = newl;
            }

            ans.add(l - i + 1);
            i = l;
        }

        return ans;
    }
}
// @lc code=end

