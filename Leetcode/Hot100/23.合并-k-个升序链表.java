/*
 * @lc app=leetcode.cn id=23 lang=java
 *
 * [23] 合并 K 个升序链表
 */

// @lc code=start
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for(ListNode head : lists){
            if(head != null)pq.offer(head);
        }

        ListNode ans = new ListNode();
        ListNode tmp = ans;
        while(!pq.isEmpty()){
            ListNode node = pq.poll();
            if(node.next != null)pq.offer(node.next);

            tmp.next = node;
            tmp = node;
        }

        return ans.next != null ? ans.next : null;
    }
}
// @lc code=end

