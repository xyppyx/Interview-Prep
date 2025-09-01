/*
 * @lc app=leetcode.cn id=2 lang=java
 *
 * [2] 两数相加
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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null){
            return null;
        }else if(l1 == null){
            return l2;
        }else if(l2 == null){
            return l1;
        }

        ListNode ans = new ListNode(0, null);
        ListNode tmp = ans;
        ListNode h1 = l1, h2 = l2;
        
        int sum = 0, tag = 0;
        while(h1 != null && h2 != null){
            sum = h1.val + h2.val + tag;
            if(sum >= 10){
                sum -= 10;
                tag = 1;
            }else{
                tag = 0;
            }
            tmp.next = new ListNode(sum, null);
            tmp = tmp.next;
            h1 = h1.next;
            h2 = h2.next;
        }

        if(h1 == null && h2 == null){
            if(tag == 1){
                tmp.next = new ListNode(tag, null);
            }
            return ans.next;
        }else if(h1 == null){
            while(h2 != null){
                sum = h2.val + tag;
                if(sum >= 10){
                    sum -= 10;
                    tag = 1;
                }else{
                    tag = 0;
                }
                tmp.next = new ListNode(sum, null);
                h2 = h2.next;
                tmp = tmp.next;
            }
        }else if(h2 == null){
            while(h1 != null){
                sum = h1.val + tag;
                if(sum >= 10){
                    sum -= 10;
                    tag = 1;
                }else{
                    tag = 0;
                }
                tmp.next = new ListNode(sum, null);
                h1 = h1.next;
                tmp = tmp.next;
            }
        }
        if(tag == 1){
            tmp.next = new ListNode(tag, null);
        }
        return ans.next;
    }
}
// @lc code=end

