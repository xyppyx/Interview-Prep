/*
 * @lc app=leetcode.cn id=74 lang=golang
 *
 * [74] 搜索二维矩阵
 */

// @lc code=start
package main

func searchMatrix(matrix [][]int, tar int) bool {
	n, m := len(matrix), len(matrix[0])

	if matrix[0][0] > tar {
		return false
	}

	l := 0
	r := n*m - 1

	for l < r {
		mid := (l + r + 1) / 2
		if matrix[mid/m][mid%m] > tar {
			r = mid - 1
		} else {
			l = mid
		}
	}

	return matrix[l/m][l%m] == tar
}

// @lc code=end
