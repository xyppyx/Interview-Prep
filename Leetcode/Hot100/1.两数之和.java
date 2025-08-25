/*
 * @lc app=leetcode.cn id=1 lang=java
 *
 * [1] 两数之和
 */

// @lc code=start

import java.util.Map;
import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> mp = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int x = nums[i];
            if(mp.containsKey(target - x)){
                return new int[]{mp.get(target - x), i};
            } else {
                mp.put(x, i);
            }
        }
        return new int[]{-1, -1}; // 如果没有找到，返回一个无效的结果
    }
}
// @lc code=end

