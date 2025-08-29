/*
 * @lc app=leetcode.cn id=234 lang=java
 *
 * [234] 回文链表
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
    private ListNode findMid(ListNode head){
        ListNode fast = head, slow = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode reverse(ListNode head){
        ListNode tmp = null, pre = null, cur = head;
        while(cur != null){
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    private boolean check(ListNode h1, ListNode h2){
        while(h2 != null){
            if(h1.val != h2.val)return false;
            h1 = h1.next;
            h2 = h2.next;
        }
        return true;
    }

    public boolean isPalindrome(ListNode head) {
        ListNode mid = findMid(head);
        ListNode head2 = reverse(mid);
        return check(head, head2);
    }
}
// @lc code=end

