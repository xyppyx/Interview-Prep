/*
 * @lc app=leetcode.cn id=70 lang=golang
 *
 * [70] 爬楼梯
 */

// @lc code=start
package main

func climbStairs(n int) int {
	if n == 1 {
		return 1
	} else if n == 2 {
		return 2
	} else {
		tag, tmp1, tmp2 := 0, 1, 2

		for idx := 3; idx < n; idx++ {
			if tag == 0 {
				tmp1 = tmp1 + tmp2
				tag = 1
			} else {
				tmp2 = tmp1 + tmp2
				tag = 0
			}
		}

		return tmp1 + tmp2
	}
}

// @lc code=end
