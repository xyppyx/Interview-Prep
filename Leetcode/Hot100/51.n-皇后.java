/*
 * @lc app=leetcode.cn id=51 lang=java
 *
 * [51] N 皇后
 */

// @lc code=start

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    private boolean[] row;
    private boolean[] col;
    private Map<Integer, Boolean> left = new HashMap<>();
    private boolean[] right;
    private List<List<String>> ans = new ArrayList<>();

    private int toRight(int x, int y){
        return x + y;
    }

    private int toLeft(int x, int y){
        return x - y;
    }

    private boolean test(int x, int y){
        return row[x] != true 
               && col[y] != true
               && right[toRight(x, y)] != true 
               && left.getOrDefault(toLeft(x, y), false) != true;
    }

    private void set(int x, int y){
        row[x] = true;
        col[y] = true;
        right[toRight(x, y)] = true;
        left.put(toLeft(x, y), true);
    }

    private void reset(int x, int y){
        row[x] = false;
        col[y] = false;
        right[toRight(x, y)] = false;
        left.put(toLeft(x, y), false);
    }

    private void dfs(int num, int n, List<String> res){
        if(num >= n){
            ans.add(new ArrayList<>(res));
            return;
        }

        char[] tmp = new char[n];
        for(int i = 0; i < n; i++)tmp[i] = '.';

        for(int i = 0; i < n; i++){
            if(test(num, i)){
                tmp[i] = 'Q';
                set(num, i);
                res.add(new String(tmp));
                dfs(num + 1, n, res);
                reset(num, i);
                res.removeLast();
                tmp[i] = '.';
            }
        }
    }

    public List<List<String>> solveNQueens(int n) {
        row = new boolean[n];
        col = new boolean[n];
        right = new boolean[2 * n];
        dfs(0, n, new ArrayList<String>());
        return ans;
    }
}
// @lc code=end

