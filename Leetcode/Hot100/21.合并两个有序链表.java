/*
 * @lc app=leetcode.cn id=21 lang=java
 *
 * [21] 合并两个有序链表
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
    private boolean comp(ListNode a, ListNode b) {
        return a.val < b.val;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null && list2 == null)return null;
        else if(list1 == null)return list2;
        else if(list2 == null)return list1;

        ListNode ans = new ListNode(0, null);
        ListNode tmp = ans;
        ListNode h1 = list1, h2 = list2;
        while(h1 != null && h2 != null){
            if(comp(h1, h2)){
                tmp.next = h1;
                tmp = h1;
                h1 = h1.next;
            }else{
                tmp.next = h2;
                tmp = h2;
                h2 = h2.next;
            }
        }

        if(h1 == null && h2 == null){
            return ans.next;
        }else if(h1 == null){
            while(h2 != null){
                tmp.next = h2;
                tmp = h2;
                h2 = h2.next;
            }
            return ans.next;
        }else if(h2 == null){
            while(h1 != null){
                tmp.next = h1;
                tmp = h1;
                h1 = h1.next;
            }
            return ans.next;
        }else{
            return ans.next;
        }
    }
}
// @lc code=end

