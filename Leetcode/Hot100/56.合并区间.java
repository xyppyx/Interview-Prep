/*
 * @lc app=leetcode.cn id=56 lang=java
 *
 * [56] 合并区间
 */

// @lc code=start
class Solution {
    private class pair{
        public int fir;
        public int sec;

        pair(int a, int b){
            fir = a;
            sec = b;
        }
    }

    public int[][] merge(int[][] intervals) {
        int n = intervals.length;
        List<int[]>ans = new ArrayList<int[]>();
        if(n <= 1)return intervals;

        pair[] pairs = new pair[n];
        for(int i = 0; i < n; i++){
            pairs[i] = new pair(intervals[i][0], intervals[i][1]);
        }
        Arrays.sort(pairs, (p1, p2) -> {
            if(p1.fir != p2.fir)
                return p1.fir - p2.fir;
            return p1.sec - p2.sec;
        });

        for(int i = 0; i < n; i++){
            int tmp = i;
            int ansl = pairs[i].fir, ansr = pairs[i].sec;
            while(tmp + 1 < n && ansr >= pairs[tmp + 1].fir){
                if(ansr < pairs[tmp + 1].sec){
                    ansr = pairs[tmp + 1].sec;
                }
                ++tmp;
            }
            i = tmp;
            ans.add(new int[]{ansl, ansr});
        }
        return ans.toArray(new int[ans.size()][]);
    }
}
// @lc code=end

