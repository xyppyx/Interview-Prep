/*
 * @lc app=leetcode.cn id=169 lang=java
 *
 * [169] 多数元素
 */

// @lc code=start
class Solution {
    public int majorityElement(int[] nums) {
        // 摩尔投票法
        // 时间复杂度 O(n)，空间复杂度 O(1)
        // 在一个数组中，如果某个元素的出现次数超过了数组长度的一半
        // 那么这个元素与其他所有元素一一配对，最后仍然会剩下至少一个该元素。

        // 初始化候选人 x 和票数 votes
        int x = 0, votes = 0;
        for (int num : nums){
            // 当票数为 0 时，更新候选人 x
            if (votes == 0) x = num;
            // 根据当前元素是否为候选人，更新票数 votes
            // 相同则加一，不同则减一
            votes += num == x ? 1 : -1;
        }

        // 返回最终的候选人 x
        return x;
    }
}
// @lc code=end

