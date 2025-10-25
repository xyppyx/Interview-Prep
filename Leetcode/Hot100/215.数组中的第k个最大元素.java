/*
 * @lc app=leetcode.cn id=215 lang=java
 *
 * [215] 数组中的第K个最大元素
 */

// @lc code=start

import java.util.Queue;

class Solution {

    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;

        // 使用最小堆维护前k个最大的元素
        MinHeap minHeap = new MinHeap(k);

        // 遍历数组，将元素加入最小堆
        for(int i = 0; i < n; i++){
            minHeap.add(nums[i]);
        }

        // 最小堆的堆顶元素即为第k个最大的元素
        return minHeap.getMin();
    }

    private class MinHeap{
        private ArrayList<Integer> data;
        private int capacity;

        public MinHeap(int capacity){
            this.capacity = capacity;
            data = new ArrayList<>();
        }

        public void add(int val){
            if(data.size() < capacity){
                data.add(val);
                siftUp(data.size() - 1);
            }else if(val > data.get(0)){
                data.set(0, val);
                siftDown(0);
            }
        }

        private void siftUp(int index){
            while(index > 0){
                int parent = (index - 1) / 2;
                if(data.get(index) < data.get(parent)){
                    swap(index, parent);
                    index = parent;
                }else{
                    break;
                }
            }
        }

        private void siftDown(int index){
            int size = data.size();
            while(true){
                int left = index * 2 + 1;
                int right = index * 2 + 2;
                int smallest = index;

                if(left < size && data.get(left) < data.get(smallest)){
                    smallest = left;
                }
                if(right < size && data.get(right) < data.get(smallest)){
                    smallest = right;
                }
                if(smallest != index){
                    swap(index, smallest);
                    index = smallest;
                }else{
                    break;
                }
            }
        }

        private void swap(int i, int j){
            int temp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, temp);
        }

        public int getMin(){
            return data.get(0);
        }
    }

    
}
// @lc code=end

