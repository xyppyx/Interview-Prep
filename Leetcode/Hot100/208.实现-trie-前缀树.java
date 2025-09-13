/*
 * @lc app=leetcode.cn id=208 lang=java
 *
 * [208] 实现 Trie (前缀树)
 */

// @lc code=start

import java.util.ArrayList;

class Trie {

    private class Node{
        public Node[] children;
        public boolean isEnd;

        public Node(boolean isEnd, char c){
            children = new Node[26];
            this.isEnd = isEnd;
        }

        public Node(){
            children = new Node[26];
            isEnd = false;
        }
    }

    Node root = null;

    public Trie() {
        root = new Node();
    }
    
    public void insert(String word) {
        Node cur = root;
        for(char c : word.toCharArray()){
            int index = c - 'a';
            if (cur.children[index] == null) { 
                cur.children[index] = new Node();
            }
            cur = cur.children[index];
        }
        cur.isEnd = true;
    }
    
    public boolean search(String word) {
        Node cur = root;
        for(char c : word.toCharArray()){
            int index = c - 'a';
            if(cur.children[index] == null){
                return false;
            }
            cur = cur.children[index];
        }

        if(cur.isEnd){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean startsWith(String prefix) {
        Node cur = root;
        for(char c : prefix.toCharArray()){
            int index = c - 'a';
            if(cur.children[index] == null){
                return false;
            }
            cur = cur.children[index];
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
// @lc code=end

