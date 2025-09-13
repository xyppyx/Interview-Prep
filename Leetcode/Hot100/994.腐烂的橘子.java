/*
 * @lc app=leetcode.cn id=994 lang=java
 *
 * [994] 腐烂的橘子
 */

// @lc code=start

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    private int n = 0;
    private int m = 0;
    private int ans = 0;
    private int[] dx = {0, -1, 0, 1};
    private int[] dy = {-1, 0, 1, 0};
    private Queue<int[]> queue = new ArrayDeque<>();

    private boolean check(int x, int y){
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    public int orangesRotting(int[][] grid) {
        if(grid == null || grid.length == 0)return 0;
        n = grid.length;
        m = grid[0].length;

        int[][] times = new int[n][m];
        
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(grid[i][j] != 2)continue;
                queue.offer(new int[]{i, j});
            }
        }

        while(!queue.isEmpty()){
            int[] top = queue.poll();
            for(int i = 0; i < 4; i++){
                int nx = top[0] + dx[i];
                int ny = top[1] + dy[i];
                if(check(nx, ny) && grid[nx][ny] == 1 && times[nx][ny] == 0){
                    times[nx][ny] = times[top[0]][top[1]] + 1;
                    ans = Math.max(ans, times[nx][ny]);
                    queue.offer(new int[]{nx, ny});
                }
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                if(times[i][j] == 0 && grid[i][j] == 1){
                    ans = -1;
                    break;
                }
            }
        }

        return ans;
    }
}
// @lc code=end

