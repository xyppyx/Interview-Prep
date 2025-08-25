/*
 * @lc app=leetcode.cn id=49 lang=java
 *
 * [49] 字母异位词分组
 */

// @lc code=start
class Solution {
    private int getHash(String str){
            int[] count = new int[26];
            for(char c : str.toCharArray()){
                count[c - 'a']++;
            }
            int hash = 0;
            for(int i = 0; i < 26; i++){
                hash = hash * 31 + count[i];
            }
            return hash;
        }

    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ans = new ArrayList<>();
        //哈希值到字符串列表的映射
        Map<Integer, List<String>> map = new HashMap<>();

        for(String str : strs){
            int hash = getHash(str);
            if(!map.containsKey(hash)){
                map.put(hash, new ArrayList<>());
            }
            map.get(hash).add(str);
        }

        for(Map.Entry<Integer, List<String>> entry : map.entrySet()){
            ans.add(entry.getValue());
        }
        return ans;
    }
}
// @lc code=end

