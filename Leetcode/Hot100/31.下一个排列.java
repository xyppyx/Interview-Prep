/*
 * @lc app=leetcode.cn id=31 lang=java
 *
 * [31] 下一个排列
 */

// @lc code=start
class Solution {
    public void nextPermutation(int[] nums) {
        int idx = 0;
        int n = nums.length;

        // 核心思路, 从后向前先找到第一个降序的位置idx - 1, 然后再从后向前找到第一个比该位置数大的数交换(增大, 但最小化增大)，最后将该位置后的数反转(最小化变大的增量)
        // 最小化变大的增量

        // 从后往前找到第一个降序的位置
        for(int i = n - 1; i > 0; i--){
            if(nums[i] > nums[i - 1]){
                idx = i;
                break;
            }
        }

        if(idx == 0){
            reverse(0, n - 1, nums);
        }else{// 找到第一个降序位置后，再从后往前找到第一个比该位置数大的数, 交换，然后将该位置后的数反转
            int k = idx;
            for(int i = n - 1; i > idx; i--){
                if(nums[i] > nums[idx - 1]){
                    k = i;
                    break;
                }
            }

            swap(idx - 1, k, nums);
            reverse(idx, n - 1, nums);
        }
    }

    private void swap(int i, int j, int[]a){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private void reverse(int start, int end, int[] a){
        while(start < end){
            swap(start, end, a);
            start++;
            end--;
        }
    }
}
// @lc code=end

