/*
 * @lc app=leetcode.cn id=148 lang=java
 *
 * [148] 排序链表
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
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null)return head;
        
        ListNode mid = findMid(head);
        head = sortList(head);
        mid = sortList(mid);

        return merge(head, mid);
    }

    //找到当前链表的中间节点并断开
    private ListNode findMid(ListNode head){
        ListNode fast = head, slow = head, pre = head;
        while(fast != null && fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null;
        return slow;
    }

    private ListNode merge(ListNode h1, ListNode h2){
        ListNode ans = new ListNode();
        ListNode cur = ans;

        while(h1 != null && h2 != null){
            if(h1.val < h2.val){
                cur.next = h1;
                h1 = h1.next;
            }else{
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }

        if(h1 != null){
            cur.next = h1;
        }else if(h2 != null){
            cur.next = h2;
        }

        return ans.next;
    }
}
// @lc code=end

