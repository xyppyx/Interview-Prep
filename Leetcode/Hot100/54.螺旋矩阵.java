/*
 * @lc app=leetcode.cn id=54 lang=java
 *
 * [54] 螺旋矩阵
 */

// @lc code=start
import java.util.ArrayList;

class Solution {
    private int[] dx = {0, 1, 0, -1};
    private int[] dy = {1, 0, -1, 0};
    private int n, m;
    private boolean[][] use = new boolean[11][11];

    private boolean isValid(int x, int y){
        return 0 <= x && x < n && 0 <= y && y < m && use[x][y] == false;
    }

    private void dfs(int x, int y, int dir, List<Integer> ans, int[][] matrix){
        while(isValid(x + dx[dir], y + dy[dir])){
            ans.add(matrix[x + dx[dir]][y + dy[dir]]);
            use[x + dx[dir]][y + dy[dir]] = true;
            //System.out.println(matrix[x][y]);
            x += dx[dir];
            y += dy[dir];
        }
        ++dir;
        dir %= 4;
        if(ans.size() == m * n)return;
        dfs(x, y, dir, ans, matrix);
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<Integer>();
        n = matrix.length;
        m = matrix[0].length;

        ans.add(matrix[0][0]);
        use[0][0] = true;
        dfs(0, 0, 0, ans, matrix);

        return ans;
    }
}
// @lc code=end

