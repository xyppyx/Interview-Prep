/*
 * @lc app=leetcode.cn id=24 lang=java
 *
 * [24] 两两交换链表中的节点
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
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)return head;
        ListNode ans = new ListNode(0, head);
        ListNode cur = head.next, pre = head, tmp = ans;

        while(true){
            tmp.next = cur;
            pre.next = cur.next;
            cur.next = pre;
            if(pre.next == null || pre.next.next == null)break;

            tmp = pre;
            pre = pre.next;
            cur = pre.next;
        }


        return ans.next;
    }
}
// @lc code=end

