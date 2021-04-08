# 1 泛型

```
将类型进行参数化，类型字面量。
java的类型系统 
go的类型系统

泛型只在编译阶段有效。
编译之后，jvm会去掉泛型。在编译过程中，正确校验泛型结果后，jvm会将泛型的相关信息擦掉。并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，泛型信息不会进入到运行时阶段。
```



## 1 泛型类

```
通过泛型可以完成对一组类的操作对外开放相同的接口。最典型的就是各种容器类，如：List、Set、Map。

泛型的类型参数只能是类类型（包括自定义类），不能是简单类型

- 如果不传入泛型类型实参的话，在泛型类中使用泛型的方法或成员变量定义的类型可以为任何的类型。
- 不能对确切的泛型类型使用instanceof操作。
```





## 2 泛型接口

```
泛型接口与泛型类的定义及使用基本相同。泛型接口常被用在各种类的生产器中。

未传入泛型实参时，与泛型类的定义相同，在声明类的时候，需将泛型的声明也一起加到类中。
```

```java
// String作为泛型参数，not allow
/*class T22<String> implements T2 {
    public String get() {
        return "t21";
    }
}*/
// String作为泛型参数，not allow
/*class T22<String> implements T2<String> {
    public String get() {
        return "t21";
    }
}*/
class T22 implements T2<String> {
    public String get() {
        return "t21";
    }
}
```







## 3 泛型方法

```
<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。

泛型方法能使方法独立于类而产生变化。能用就用
```

```java
static <T> T instance(Class<T> cls)
            throws IllegalAccessException, InstantiationException {
        return cls.newInstance();
    }

//    static T set(T t) {return t}; // cannot resolve symbol 'T'
```



##  4 泛型通配符

用 ? 通配符来表未知类型。



## 5 泛型方法与可变参数

```java
static <T> void print(T... args) {
    for (T t : args) {
        System.out.println(t);
    }
}
print(1, "aa", true, 1.01, new int[]{2, 2});
```

## 6 静态方法与泛型

```
如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法。
```

```java
class T1<T> {
//    static void b(T t) {} // cannot be refrenced from static context
    static <T> void b(T t) {}
```

## 7 泛型上下边界

```
上边界，即传入的类型实参必须是指定类型的子类型

用在泛型方法、泛型类
```

```java
// 上边界，必须是子类型
static <T> void c1(T1<? extends Number> t1) {
    t1.f1();
}
// 下边界，必须是父类型
static <T> void c2(T1<? super Integer> t1) {
    t1.f1();
}

class T3<T extends Number> {
T3<Integer> t3 = new T3<>();
//T3<String> t31 = new T3<>(); // is not within its bound
```

## 8 泛型数组

```
不能创建一个确切的泛型类型的数组
```

```java
//        List<String>[] ls = new ArrayList<String>[10]; // generic array creation
List<?>[] ls = new ArrayList<?>[10];
List<String>[] ls1 = new ArrayList[10];

//        List<String>[] lsa = new List<String>[10]; // Not really allowed.
Object o = ls1;
Object[] oa = (Object[]) o;
List<Integer> li = new ArrayList<Integer>();
li.add(new Integer(3));
oa[1] = li; // Unsound, but passes run time store check
String s = ls1[1].get(0); // Run-time error: ClassCastException.
// 这种情况下，由于JVM泛型的擦除机制，在运行时JVM是不知道泛型信息的，所以可以给oa[1]赋上一个ArrayList而不会出现异常，
// 但是在取出数据的时候却要做一次类型转换，所以就会出现ClassCastException
```

