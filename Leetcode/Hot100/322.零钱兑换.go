/*
 * @lc app=leetcode.cn id=322 lang=golang
 *
 * [322] 零钱兑换
 */

// @lc code=start
package main

import "math"

func coinChange(coins []int, amount int) int {
	if amount == 0 {
		return 0
	}

	dp := make([]int, amount+1)

	for i := 1; i <= amount; i++ {
		dp[i] = math.MaxInt32
		for _, v := range coins {
			if i-v >= 0 && dp[i-v] != -1 {
				dp[i] = min(dp[i], dp[i-v]+1)
			}
		}

		if dp[i] == math.MaxInt32 {
			dp[i] = -1
		}
	}

	return dp[amount]
}
