/*
 * @lc app=leetcode.cn id=84 lang=java
 *
 * [84] 柱状图中最大的矩形
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;

class Solution {
    // 枚举每个柱子作为矩形的高度, 找到其宽度的最大值
    public int largestRectangleArea(int[] h) {
        int n = h.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0;
        for (int i = 0; i <= n; i++) {
            int height = (i == n) ? 0 : h[i];

            // 维护一个单调递增栈
            // 新增高度小于栈顶高度时，说明找到了栈顶高度的右边界
            while (!stack.isEmpty() && height < h[stack.peek()]) {
                int heightOfBar = h[stack.pop()];
                // 计算宽度(找左边界)
                // 如果栈为空，说明当前弹出的柱子是最小的，宽度为i
                // 如果栈不为空，宽度为当前索引i减去栈顶元素索引再减1
                int widthOfBar = stack.isEmpty() ? i : i - stack.peek() - 1;

                maxArea = Math.max(maxArea, heightOfBar * widthOfBar);
            }
            stack.push(i);
        }
        return maxArea;
    }
}
// @lc code=end

