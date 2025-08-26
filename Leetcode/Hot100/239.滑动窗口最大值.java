/*
 * @lc app=leetcode.cn id=239 lang=java
 *
 * [239] 滑动窗口最大值
 */

// @lc code=start

import java.util.Deque;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new ArrayDeque<Integer>();
        int[] ans = new int[nums.length - k + 1];
        
        for(int i = 0; i < k; i++){
            while(!dq.isEmpty() && dq.peekLast() < nums[i])
                dq.removeLast();
            dq.addLast(nums[i]);
        }
        ans[0] = dq.peekFirst();

        for(int i = k; i < nums.length; i++){
            if(dq.peekFirst() == nums[i - k])
                dq.removeFirst();
            while(!dq.isEmpty() && dq.peekLast() < nums[i])
                dq.removeLast();
            dq.addLast(nums[i]);
            ans[i - k + 1] = dq.peekFirst();
        }

        return ans;
    }
}
// @lc code=end

