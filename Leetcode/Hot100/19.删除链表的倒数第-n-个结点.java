/*
 * @lc app=leetcode.cn id=19 lang=java
 *
 * [19] 删除链表的倒数第 N 个结点
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode ans = new ListNode(0, head);
        ListNode pre = ans, cur = ans;
        for(int i = 1; i <= n; i++)cur = cur.next;

        while(cur != null && cur.next != null){
            cur = cur.next;
            pre = pre.next;
        }

        pre.next = pre.next.next;
        return ans.next;
    }
}
// @lc code=end

