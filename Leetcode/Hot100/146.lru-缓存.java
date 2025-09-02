/*
 * @lc app=leetcode.cn id=146 lang=java
 *
 * [146] LRU 缓存
 */

// @lc code=start
/**
 * 直接继承 LinkedHashMap 实现
 */
// class LRUCache extends LinkedHashMap<Integer, Integer> {
//     private static final float DEFAULT_LOAD_FACTOR = 0.75f;
//     private final int capacity;

//     public LRUCache(int capacity) {
//         // accessOrder 设置为 true，表示按照访问顺序排序
//         super(capacity, DEFAULT_LOAD_FACTOR, true);
//         this.capacity = capacity;
//     }

//     public int get(int key) {
//         return super.getOrDefault(key, -1);
//     }

//     //重写 removeEldestEntry 方法，当元素个数超过容量时，删除最久未使用的元素
//     @Override
//     protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
//         return size() > capacity;
//     }
// }

class LRUCache {
    private static class Node{
        int key, val;
        Node prev, next;

        Node(int k, int v){
            key = k;
            val = v;
        }
    }

    private final int capacity;
    private final Node dummy = new Node(0, 0); //哨兵节点
    private final Map<Integer, Node> mp = new HashMap<>();

    //把一个节点插入到头部
    private void addFront(Node node){
        node.prev = dummy;
        node.next = dummy.next;
        dummy.next = node;
        node.next.prev = node;
    }

    //把一个节点移动到头部
    private void moveFront(Node node){
        remove(node);
        addFront(node);
    }

    //删除对应节点
    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    //构造函数
    public LRUCache(int capacity) {
        this.capacity = capacity;
        dummy.prev = dummy;
        dummy.next = dummy;
    }
    
    //获取对应的val，并移动node到头部
    //如果没有返回-1
    public int get(int key) {
        if(!mp.containsKey(key))return -1;
        Node node = mp.get(key);
        moveFront(node);
        return node.val;
    }
    
    //插入新键值对
    public void put(int key, int val) {
        if(get(key) != -1){
            Node node = mp.get(key);
            node.val = val;
            moveFront(node);
        }else{
            Node node = new Node(key, val);
            mp.put(key, node);
            addFront(node);
            if(mp.size() > capacity){
                Node last = dummy.prev;
                mp.remove(last.key);
                remove(last);
            }
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
// @lc code=end

