/*
 * @lc app=leetcode.cn id=189 lang=java
 *
 * [189] 轮转数组
 */

// @lc code=start
class Solution {
    public void rotate(int[] nums, int k) {
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k % nums.length - 1);
        reverse(nums, k % nums.length, nums.length - 1);
    }

    private void reverse(int[] nums, int l, int r){
        while(l < r){
            int tmp = nums[l];
            nums[l] = nums[r];
            nums[r] = tmp;
            ++l;
            --r;
        }
    }
}
// @lc code=end

