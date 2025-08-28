/*
 * @lc app=leetcode.cn id=48 lang=java
 *
 * [48] 旋转图像
 */

// @lc code=start
class Solution {
    private int tmp;

    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for(int i = 0; i < n - 1; i++){
            for(int j = i + 1; j < n; j++){
                //swap(matrix[i][j], matrix[j][i]);
                tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }

        for(int j = 0; j < n / 2; j++){
            for(int i = 0; i < n; i++){
                tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = tmp;
            }
        }
    }
}
// @lc code=end

