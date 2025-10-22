/*
 * @lc app=leetcode.cn id=4 lang=golang
 *
 * [4] 寻找两个正序数组的中位数
 */

// @lc code=start
package main

import "math"

func findMedianSortedArrays(a, b []int) float64 {
	if len(a) > len(b) {
		a, b = b, a
	}

	m, n := len(a), len(b)
	// 循环不变量：a[left] <= b[j+1]
	// 循环不变量：a[right] > b[j+1]
	left, right := -1, m
	for left+1 < right { // 开区间 (left, right) 不为空
		i := left + (right-left)/2
		j := (m+n+1)/2 - i - 2
		if a[i] <= b[j+1] {
			left = i // 缩小二分区间为 (i, right)
		} else {
			right = i // 缩小二分区间为 (left, i)
		}
	}

	// 此时 left 等于 right-1
	// a[left] <= b[j+1] 且 a[right] > b[j'+1] = b[j]，所以答案是 i=left
	i := left
	j := (m+n+1)/2 - i - 2
	ai := math.MinInt
	if i >= 0 {
		ai = a[i]
	}
	bj := math.MinInt
	if j >= 0 {
		bj = b[j]
	}
	ai1 := math.MaxInt
	if i+1 < m {
		ai1 = a[i+1]
	}
	bj1 := math.MaxInt
	if j+1 < n {
		bj1 = b[j+1]
	}
	max1 := max(ai, bj)
	min2 := min(ai1, bj1)
	if (m+n)%2 > 0 {
		return float64(max1)
	}
	return float64(max1+min2) / 2
}

// @lc code=end
