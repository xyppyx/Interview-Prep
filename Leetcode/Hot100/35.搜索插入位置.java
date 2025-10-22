/*
 * @lc app=leetcode.cn id=35 lang=java
 *
 * [35] 搜索插入位置
 */

// @lc code=start
class Solution {
    public int searchInsert(int[] nums, int tar) {
        int l = 0, r = nums.length - 1;

        while(l <= r){
            int mid = (l + r) >> 1;
            if(nums[mid] == tar)return mid;

            if(nums[mid] <= tar){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return l;
    }
}
// @lc code=end

