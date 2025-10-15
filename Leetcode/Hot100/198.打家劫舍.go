/*
 * @lc app=leetcode.cn id=198 lang=golang
 *
 * [198] 打家劫舍
 */

// @lc code=start
package main

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func rob(nums []int) int {
	n := len(nums)

	// 1. 处理边界情况
	if n == 0 {
		return 0
	}
	if n == 1 {
		return nums[0]
	}

	// 修正: 必须为每一维分配内存。
	dp := make([][]int, n)
	for i := 0; i < n; i++ {
		dp[i] = make([]int, 2)
	}

	dp[0][0] = 0
	dp[0][1] = nums[0]

	for i := 1; i < n; i++ {
		dp[i][0] = max(dp[i-1][0], dp[i-1][1])
		dp[i][1] = dp[i-1][0] + nums[i]
	}

	return max(dp[n-1][0], dp[n-1][1])
}

// @lc code=end
