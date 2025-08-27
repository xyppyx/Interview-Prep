/*
 * @lc app=leetcode.cn id=238 lang=java
 *
 * [238] 除自身以外数组的乘积
 */

// @lc code=start
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int[] pre = new int[n];
        int[] suf = new int[n];

        pre[0] = nums[0];
        for(int i = 1; i < n; i++){
            pre[i] = pre[i - 1] * nums[i];
        }

        suf[n - 1] = nums[n - 1];
        for(int i = n - 2; i >= 0; i--){
            suf[i] = suf[i + 1] * nums[i];
        }

        ans[0] = suf[1];
        for(int i = 1; i < n - 1; i++){
            ans[i] = pre[i - 1] * suf[i + 1];
        }
        ans[n - 1] = pre[n - 2];
        return ans;
    }
}
// @lc code=end

