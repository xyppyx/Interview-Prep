/*
 * @lc app=leetcode.cn id=153 lang=golang
 *
 * [153] 寻找旋转排序数组中的最小值
 */

// @lc code=start
package main

func findMin(nums []int) int {

	n := len(nums)
	ans := nums[0]

	l, r := 0, n-1

	for l <= r {
		mid := (l + r) >> 1

		//左半边有序
		if nums[l] <= nums[mid] && nums[mid] <= nums[r] {
			return nums[l]
		} else if nums[l] <= nums[mid] {
			l = mid + 1
		} else {
			r = mid
		}
	}

	return min(ans, nums[l])
}

// @lc code=end
