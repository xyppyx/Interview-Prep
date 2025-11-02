/*
 * @lc app=leetcode.cn id=75 lang=java
 *
 * [75] 颜色分类
 */

// @lc code=start
class Solution {
    public void sortColors(int[] nums) {
        // 分别指向0，1，2区域
        int low = 0, mid = 0, high = nums.length - 1;
        // mid是活动指针
        while (mid <= high) {
            if (nums[mid] == 0) {  // 说明要交换到0区
                swap(nums, low, mid);
                low += 1;// 0区扩大
                mid += 1;// mid继续前进
            } else if (nums[mid] == 1) {  // 正好
                mid += 1; // 1区扩大
            } else {  // 否则交换到2区
                swap(nums, mid, high);
                high -= 1; // 2区扩大
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
// @lc code=end

