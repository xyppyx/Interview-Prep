/*
 * @lc app=leetcode.cn id=1 lang=golang
 *
 * [1] 两数之和
 */

// @lc code=start
package main

func twoSum(nums []int, target int) []int {
	ans := []int{}
	numMap := make(map[int]int)
	for i, num := range nums {
		if val, exists := numMap[target-num]; exists {
			ans = append(ans, val, i)
			break
		}
		numMap[num] = i
	}
	return ans
}

// @lc code=end
