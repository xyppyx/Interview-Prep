/*
 * @lc app=leetcode.cn id=33 lang=golang
 *
 * [33] 搜索旋转排序数组
 */

// @lc code=start
package main

func search(nums []int, tar int) int {

	n := len(nums)

	l, r := 0, n-1

	for l <= r {
		mid := (l + r) >> 1

		if nums[mid] == tar {
			return mid
		}

		//左半边有序
		if nums[l] <= nums[mid] {

			if nums[l] <= tar && tar < nums[mid] {
				r = mid - 1
			} else {
				l = mid + 1
			}

		} else { //右半边有序

			if nums[mid] < tar && tar <= nums[r] {
				l = mid + 1
			} else {
				r = mid - 1
			}

		}
	}
	return -1
}

// @lc code=end
