/*
 * @lc app=leetcode.cn id=53 lang=java
 *
 * [53] 最大子数组和
 */

// @lc code=start
class Solution {
    public int maxSubArray(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int[] max = new int[nums.length];
        max[0] = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(nums[i] > nums[i] + max[i - 1]){
                max[i] = nums[i];
            }else{
                max[i] = nums[i] + max[i - 1];
            }
        }
        for(int x : max){
            ans = Math.max(ans, x);
        }
        return ans;
    }
}
// @lc code=end

