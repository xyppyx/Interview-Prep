/*
 * @lc app=leetcode.cn id=15 lang=java
 *
 * [15] 三数之和
 */

// @lc code=start
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int i = 0; i < nums.length - 2; i++){
            //跳过相同的左边界
            if(i > 0 && nums[i] == nums[i - 1]) continue;
            if(nums[i] > 0) break;

            int l = i + 1, r = nums.length - 1;
            while(l < r){
                int sum = nums[i] + nums[l] + nums[r];
                if(sum == 0){
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    //跳过相同的元素
                    while(l < r && nums[l] == nums[l + 1]) ++l;
                    while(l < r && nums[r] == nums[r - 1]) --r;
                    ++l;
                    --r;
                }else if(sum < 0){
                    ++l;
                }else{
                    --r;
                }
            }
        }
        return res;
    }
}
// @lc code=end

