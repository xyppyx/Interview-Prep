/*
 * @lc app=leetcode.cn id=152 lang=java
 *
 * [152] 乘积最大子数组
 */

// @lc code=start
class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int[] max = new int[n];
        int[] min = new int[n];

        int ans = nums[0];
        max[0] = nums[0];
        min[0] = nums[0];

        for(int i = 1; i < n; i++){
            if(nums[i] < 0){
                max[i] = Math.max(nums[i], nums[i] * min[i - 1]); 
                min[i] = Math.min(nums[i], nums[i] * max[i - 1]); 
                ans = Math.max(ans, max[i]);
            }else if(nums[i] >= 0){
                max[i] = Math.max(nums[i], nums[i] * max[i - 1]); 
                min[i] = Math.min(nums[i], nums[i] * min[i - 1]); 
                ans = Math.max(ans, max[i]);
            }
        }

        return ans;
    }
}
// @lc code=end

