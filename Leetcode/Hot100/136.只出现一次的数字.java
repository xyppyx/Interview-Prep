/*
 * @lc app=leetcode.cn id=136 lang=java
 *
 * [136] 只出现一次的数字
 */

// @lc code=start
class Solution {
    public int singleNumber(int[] nums) {
        int ans = 0;
        for(var num : nums){
            ans ^= num;
        }
        return ans;
    }
}
// @lc code=end

