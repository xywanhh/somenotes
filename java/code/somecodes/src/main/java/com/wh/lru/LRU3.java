package com.wh.lru;

import java.util.HashMap;

public class LRU3 {
    public static void main(String[] args) {
        LRUNodeCache<Integer,String> lru = new LRUNodeCache<Integer,String>(3);

        lru.put(1, "a");    // 1:a
        System.out.println(lru.toString());
        lru.put(2, "b");    // 2:b 1:a
        System.out.println(lru.toString());
        lru.put(3, "c");    // 3:c 2:b 1:a
        System.out.println(lru.toString());
        lru.put(4, "d");    // 4:d 3:c 2:b
        System.out.println(lru.toString());
        lru.put(1, "aa");   // 1:aa 4:d 3:c
        System.out.println(lru.toString());
        lru.put(2, "bb");   // 2:bb 1:aa 4:d
        System.out.println(lru.toString());
        lru.put(5, "e");    // 5:e 2:bb 1:aa
        System.out.println(lru.toString());
        lru.get(1);         // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(11);     // 1:aa 5:e 2:bb
        System.out.println(lru.toString());
        lru.remove(1);      //5:e 2:bb
        System.out.println(lru.toString());
        lru.put(1, "aaa");  //1:aaa 5:e 2:bb
        System.out.println(lru.toString());
    }
}

class LRUNodeCache<K, V> {
    class CacheNode {
        CacheNode pre;
        CacheNode next;
        K key;
        V val;
    }

    private int currentCacheSize;
    private int capacity;
    private HashMap<K, CacheNode> map;
    private CacheNode first;
    private CacheNode last;

    public LRUNodeCache(int size) {
        currentCacheSize = 0;
        this.capacity = size;
        map = new HashMap<K, CacheNode>(size);
    }

    public void put(K key, V val) {
        CacheNode node = map.get(key);
        if (node == null) {
            if (map.size() >= capacity) {
                map.remove(last.key);
                removeLast();
            }
            node = new CacheNode();
            node.key = key;
        }
        node.val = val;
        moveToHead(node);
        map.put(key, node);
    }

    public V get(K key) {
        CacheNode node = map.get(key);
        if (node == null) {
            return null;
        }
        moveToHead(node);
        return node.val;
    }

    public CacheNode remove(K key) {
        CacheNode node = map.get(key);
        if (node != null) {
            if (node.pre != null) {
                node.pre.next = node.next;
            }
            if (node.next != null) {
                node.next.pre = node.pre;
            }
            if (node == first) {
                first = node.next;
            }
            if (node == last) {
                last = node.pre;
            }
        }
        return map.remove(key);
    }

    private void removeLast() {
        if (last != null) {
            last = last.pre;
            if (last == null) {
                first = null;
            } else {
                last.next = null;
            }
        }
    }

    private void moveToHead(CacheNode node) {
        if (first == node) {
            return;
        }
        if (node.next != null) {
            node.next.pre = node.pre;
        }
        if (node.pre != null) {
            node.pre.next = node.next;
        }
        if (node == last) {
            last = node.pre;
        }
        if (first == null || last == null) {
            first = last = node;
            return;
        }
        node.next = first;
        first.pre = node;
        first = node;
        first.pre = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        CacheNode node = first;
        while (node != null) {
            builder.append(String.format("%s:%s ", node.key, node.val));
            node = node.next;
        }
        return builder.toString();
    }

    public void clear() {
        first = null;
        last = null;
        map.clear();
    }
}
