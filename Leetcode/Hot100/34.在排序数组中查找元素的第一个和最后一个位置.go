/*
 * @lc app=leetcode.cn id=34 lang=golang
 *
 * [34] 在排序数组中查找元素的第一个和最后一个位置
 */

// @lc code=start
package main

func searchRange(nums []int, tar int) []int {
	l := 0
	r := len(nums) - 1
	ans := []int{-1, -1}

	if len(nums) == 0 || nums[0] > tar || nums[r] < tar {
		return ans
	}

	for l <= r {
		mid := (l + r) / 2
		if nums[mid] >= tar {
			r = mid - 1
		} else {
			l = mid + 1
		}
	}

	if nums[l] != tar {
		return ans
	}
	ans[0] = l

	l = 0
	r = len(nums) - 1

	for l <= r {
		mid := (l + r) / 2
		if nums[mid] >= tar+1 {
			r = mid - 1
		} else {
			l = mid + 1
		}
	}
	ans[1] = l - 1
	return ans
}

// @lc code=end
