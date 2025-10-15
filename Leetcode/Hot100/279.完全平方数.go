/*
 * @lc app=leetcode.cn id=279 lang=golang
 *
 * [279] 完全平方数
 */

// @lc code=start
/*
 * [279] 完全平方数
 * 采用动态规划 (DP) 解决
 */

package main

import (
	"math"
)

// min 是一个辅助函数，用于返回两个整数中的较小值。
func min(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func numSquares(n int) int {
	dp := make([]int, n+1)

	dp[0] = 0

	for i := 1; i <= n; i++ {
		dp[i] = math.MaxInt32

		for j := 1; j*j <= i; j++ {
			jSq := j * j
			dp[i] = min(dp[i], dp[i-jSq]+1)
		}
	}

	return dp[n]
}

// @lc code=end
