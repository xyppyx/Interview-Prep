/*
 * @lc app=leetcode.cn id=283 lang=java
 *
 * [283] 移动零
 */

// @lc code=start
class Solution {
    public void moveZeroes(int[] nums) {
        int size = 0;
        for(int x : nums){
            if(x != 0){
                nums[size++] = x;
            }
        }
        while(size < nums.length){
            nums[size++] = 0;
        }
    }
}
// @lc code=end

