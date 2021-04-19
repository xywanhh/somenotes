# 1 集合

```
集合和数组的比较：
1. 数组能存对象和基本数据类型。集合只能存对象，不能存基本数据类型(int,long,boolean...)
2. 数组和集合存的都是对象的引用（内存地址）
3. 数组无法获取实际元素个数，length()返回数组容量。集合size()返回存的元素个数
4. 数组只支持顺序存取（数据操作和实际存储）
5. 集合支持更多的操作


```

![img](https://img2018.cnblogs.com/blog/402670/201911/402670-20191117185306543-1130864512.png)	

## List集合

```
有序
允许重复

ArrayList
数组实现
ArrayList自动扩充机制


LinkedList
双向链表实现
利用LinkedList可以实现栈、队列、双向队列

Vector
数组实现，线程安全
```



## Set集合

```
无序
不允许重复
允许null

HashSet
TreeSet 可扩展排序
LinkedHashSet extends HashSet  有序
EnumSet

```



comparable和comparator区别



## Map集合

```
key-value方式存储
Key唯一
value可以重复，可null

HashMap
Hashtable  线程安全，不允许null
TreeMap	   可定制排序
LinkedHashMap	存储有序
Properties  只支持String
```



