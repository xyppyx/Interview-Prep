/*
 * @lc app=leetcode.cn id=347 lang=java
 *
 * [347] 前 K 个高频元素
 */

// @lc code=start

import java.util.List;
import java.util.Map;

class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        List<Freq> a = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[k];

        for(var num : nums){
            if(map.containsKey(num)){
                map.put(num, map.get(num) + 1);
            }else{
                map.put(num, 1);
            }
        }

        for(var entry : map.entrySet()){
            a.add(new Freq(entry.getKey(), entry.getValue()));
        }

        Heap heap = new Heap(a);

        for(int i = 0; i < k; i++){
            ans[i] = heap.peek().v;
            heap.remove();
        }

        return ans;
    }

    private class Freq {
        public int v, freq;

        public Freq(int v, int freq) {
            this.v = v;
            this.freq = freq;
        }
    }

    private class Heap{
        private List<Freq> arr;

        public Heap(List<Freq> a){
            arr = new ArrayList<>(a);

            build();
        }

        private void down(int p){
            int size = arr.size();
            int largest = p;
            int left = p * 2 + 1;
            int right = p * 2 + 2;

            if(left < size && arr.get(left).freq > arr.get(largest).freq){
                largest = left;
            }

            if(right < size && arr.get(right).freq > arr.get(largest).freq){
                largest = right;
            }

            if(largest != p){
                swap(p, largest);
                down(largest);
            }
        }

        private void swap(int i, int j){
            Freq tmp = arr.get(i);
            arr.set(i, arr.get(j));
            arr.set(j, tmp);
        }

        private void build(){
            for(int i = arr.size() / 2 - 1; i >= 0; i--){
                down(i);
            }
        }

        public void remove(){
            swap(0, arr.size() - 1);
            arr.removeLast();
            down(0);
        }

        public Freq peek(){
            return arr.getFirst();
        }
    }
}
// @lc code=end

