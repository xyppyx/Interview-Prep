/*
 * @lc app=leetcode.cn id=300 lang=golang
 *
 * [300] 最长递增子序列
 */

// @lc code=start
package main

import "sort"

func lengthOfLIS(nums []int) int {
	g := []int{}
	for _, x := range nums {
		j := sort.SearchInts(g, x)
		if j == len(g) { // >=x 的 g[j] 不存在
			g = append(g, x)
		} else {
			g[j] = x
		}
	}
	return len(g)
}

// n^2 dp
// func lengthOfLIS(nums []int) int {

// 	ans := 1
// 	n := len(nums)
// 	dp := make([]int, n)
// 	dp[0] = 1

// 	for i := 1; i < n; i++ {
// 		dp[i] = 1
// 		for j := 0; j < i; j++ {
// 			if nums[i] > nums[j] {
// 				dp[i] = max(dp[i], dp[j]+1)
// 			}
// 		}
// 		ans = max(ans, dp[i])
// 	}

// 	return ans
// }

// @lc code=end
