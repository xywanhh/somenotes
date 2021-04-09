package com.wh.lru;

import java.util.HashMap;
import java.util.Map;

// 链表 + 容器 实现LRU缓存
public class LRU1 {
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(3);
        cache.put(1, 1);//【1】左边是最近使用的
        System.out.println(cache);
        cache.put(2, 2);//【2，1】
        System.out.println(cache);
        cache.put(3, 3);//【3，2，1】
        System.out.println(cache);
        cache.get(1);//【1，3，2】
        System.out.println(cache);
        cache.put(4, 3);//【4，1，3】
        System.out.println(cache);
//        LRUCache lruCache = new LRUCache(3);
//        lruCache.put(1, 1);
//        System.out.println(lruCache.toString());
//        lruCache.put(2, 2);
//        System.out.println(lruCache.toString());
//        lruCache.put(3, 3);
//        System.out.println(lruCache.toString());
//        lruCache.get(2);
//        System.out.println(lruCache.toString());
//        lruCache.put(4, 4);
//        System.out.println(lruCache.toString());
    }
}

class LRUCache {
    // 双向链表节点定义
    class Node {
        int key;
        int val;
        Node pre;
        Node next;
    }
    // 缓存容量
    private int capacity;
    // 保存链表的头节点、尾节点
    private Node first;
    private Node last;
    // 保存key->node映射的容器
    private Map<Integer, Node> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int val) {
        // 先看是否存在
        Node node = map.get(key);
        if (node == null) {
            node = new Node();
            node.key = key;
            node.val = val;
            if (map.size() == capacity) {
                removeLast();
            }
            addToHead(node); // 添加到头部
            map.put(key, node);
        } else {
            node.val = val;
            moveToHead(node); // 移动到头部
        }
    }

    private void moveToHead(Node node) {
        if (node == null) {
            return;
        } else if (node == last) {
            // 尾节点
            last.pre.next = null;
            last = last.pre;
        } else {
            // 中间节点
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }
        // node作为头节点
        node.pre = first.pre; // node.pre = null;
        node.next = first;
        first.pre = node;
        first = node;
    }

    private void addToHead(Node node) {
        if (map.isEmpty()) {
            first = node;
            last = node;
        } else {
            // 新节点作为头节点
            node.next = first;
            first.pre = node;
            first = node;
        }
    }

    private void removeLast() {
        map.remove(last.key);
        Node preNode = last.pre;
        // 修改last的位置
        if (preNode != null) {
            preNode.next = null;
            last = preNode;
        }
    }

    @Override
    public String toString() {
        return map.keySet().toString();
    }
}
