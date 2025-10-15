/*
 * @lc app=leetcode.cn id=118 lang=golang
 *
 * [118] 杨辉三角
 */

// @lc code=start
package main

func generate(n int) [][]int {

	if n == 0 {
		return [][]int{}
	}
	if n == 1 {
		return [][]int{{1}}
	}
	if n == 2 {
		return [][]int{{1}, {1, 1}}
	}

	ans := make([][]int, n)
	ans[0] = []int{1}
	ans[1] = []int{1, 1}

	for i := 2; i < n; i++ {
		tmp := make([]int, i+1)
		tmp[0] = 1
		tmp[i] = 1

		for j := 1; j < i; j++ {
			tmp[j] = ans[i-1][j-1] + ans[i-1][j]
		}

		ans[i] = tmp
	}

	return ans
}

// @lc code=end
