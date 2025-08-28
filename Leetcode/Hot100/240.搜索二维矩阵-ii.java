/*
 * @lc app=leetcode.cn id=240 lang=java
 *
 * [240] 搜索二维矩阵 II
 */

// @lc code=start
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        // 首先进行边界条件检查，防止空指针异常
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        
        // 从右上角开始搜索
        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                // 找到目标值
                return true;
            } else if (matrix[row][col] > target) {
                // 当前值大于目标，向左移动一列
                col--;
            } else {
                // 当前值小于目标，向下移动一行
                row++;
            }
        }

        // 遍历完成未找到目标值
        return false;
    }
}
// @lc code=end

