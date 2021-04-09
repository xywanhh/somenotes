# 1 泛型

```
泛型将类型进行参数化，可以使代码的类型范围更加类型，更好在面向对象中实现多态。代码更加通用，框架中常用。
泛型是一种编译时类型确认机制。它提供了编译期的类型安全，确保运行期的类型安全，避免不必要的类型转换异常。

泛型依赖编译器，在编译时先进行类型检查，然后进行类型擦除并且在类型参数出现的地方插入强制转换的指令实现的。
运行时不存在任何类型信息。

前言：
1.5之前，实现多态主要是通过继承实现，但会带来一个问题，编译期无法校验类型，运行时容易出现ClassCastException。
泛型，可以做到在编译期进行类型校验。使程序具有更好的“安全性”和“可读性”。

泛型的实现原理：
编译后类型“擦除”，List<Integer>、List<String>编译后变成List(原始类型)，List<T extends Number>擦除后变成List<Number>。
java的泛型是在编译期实现的，不同于c++的泛型，java的被称为“伪泛型”。


将类型进行参数化，类型字面量。
java的类型系统 
go的类型系统

泛型只在编译阶段有效。
编译之后，jvm会去掉泛型。在编译过程中，正确校验泛型结果后，jvm会将泛型的相关信息擦掉。并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，泛型信息不会进入到运行时阶段。
擦除是为了避免类型膨胀。

T, E or K,V等被广泛认可的类型占位符

阻止Java中的类型未检查的警告?
可以使用@SuppressWarnings("unchecked")注解来屏蔽。
1.8提供了菱形类型推断，就没有这种告警。

List<?>和List<Object>之间的区别是什么?
List<?> listOfAnyType;
List<Object> listOfObject = new ArrayList<Object>();
List<String> listOfString = new ArrayList<String>();
List<Integer> listOfInteger = new ArrayList<Integer>();      
listOfAnyType = listOfString; // legal
listOfAnyType = listOfInteger; // legal
listOfObjectType = (List<Object>) listOfString; // compiler error - in-convertible types


```

**List<Object>与List<?>并不等同，List<Object>是List<?>的子类。还有不能往List<?> list里添加任意对象，除了null。**

```java
public class Test {
	public static void fillNumberList(List<?> list) {
		list.add(new Integer(0));//编译错误
		list.add(new Float(1.0));//编译错误
	}
	public static void fillNumberList(List<? extends Number> list) {
		list.add(new Integer(0));//编译错误
		list.add(new Float(1.0));//编译错误
	}
    public static void fillNumberList(List<? super Number> list) {
		list.add(new Integer(0));
		list.add(new Float(1.0));
	}
    
    public static void printList(List<Object> list) {
    	for (Object elem : list)
        	System.out.println(elem + "");
    }
    public static void printList(List<?> list) {
        for (Object elem: list)
            System.out.print(elem + "");
	}
}


```





```java
// 利用反射可以突破泛型约束，因为运行时泛型被擦除了，只保留了原始类型。
int i1 = 1;
//        String si1 = (String) i1; // cannot cast int to java.lang.String
List<Integer> li1 = new ArrayList<>();
li1.add(11);
//        li1.add("aa"); // not allow
try {
    li1.getClass().getMethod("add", Object.class)
        .invoke(li1, "aa");
    for (Object obj : li1) {
        System.out.println(obj);
    }
} catch (IllegalAccessException e) {
    e.printStackTrace();
} catch (InvocationTargetException e) {
    e.printStackTrace();
} catch (NoSuchMethodException e) {
    e.printStackTrace();
}
```

```java
// 体会下泛型在编译期的作用
Pair<Integer> pair=new Pair<Integer> ();
pair.setValue(3);
Integer integer=pair.getValue();
/**
擦除getValue()的返回类型后将返回Object类型，编译器自动插入Integer的强制类型转换。也就是说，编译器把这个方法调用翻译为两条字节码指令：
1. 对原始方法Pair.getValue的调用
2. 将返回的Object类型强制转换为Integer

此外，存取一个泛型域时，也要插入强制类型转换。
*/
```

```java
泛型相关问题：
1、泛型类型引用传递问题
ArrayList<String> arrayList1 = new ArrayList<Object>(); // 编译错误  
ArrayList<Object> arrayList1 = new ArrayList<String>(); // 编译错误

2、泛型类型变量不能是基本数据类型
没有ArrayList<double>，只有ArrayList<Double>。因为当类型擦除后，ArrayList的原始类中的类型变量（T）替换为Object，但Object类型不能存储double值。

3. 不允许运行时类型查询
ArrayList<String> arrayList=new ArrayList<String>();    
if( arrayList instanceof ArrayList<String>) // not allow, String在编译后被擦除了
if( arrayList instanceof ArrayList<?>)  // allow

4、泛型在静态方法和静态类中的问题
泛型类中的静态方法和静态变量不可以使用泛型类所声明的泛型类型参数
public class Test2<T> {    
    public static T one;   // 编译错误    
    public static T show(T one) { // 编译错误    
        return null;    
    }    
}
因为泛型类中的泛型参数的实例化是在定义泛型类型对象（例如ArrayList<Integer>）的时候指定的，而静态变量和静态方法不需要使用对象来调用。对象都没有创建，如何确定这个泛型参数是何种类型，所以当然是错误的。


public class Test2<T> {    
    public static <T >T show(T one){ // 这是正确的    
        return null;    
    }
}
因为这是一个泛型方法，在泛型方法中使用的T是自己在方法中定义的T，而不是泛型类中的T。
    

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



```java
/**
泛型方法的类型推断：
- 在调用泛型方法的时候，可以指定泛型类型，也可以不指定。
- 在不指定泛型类型的情况下，泛型类型为该方法中的几种参数类型的共同父类的最小级，直到Object。
- 在指定泛型类型的时候，该方法中的所有参数类型必须是该泛型类型或者其子类。
*/
int i = GenericTest.add(1, 2); // 这两个参数都是Integer，所以T替换为Integer类型  
Number f= GenericTest.add(1, 1.2); // 这两个参数一个是Integer，另一个是Float，所以取同一父类的最小级，为Number  
Object o = GenericTest.add(1, "asd"); // 这两个参数一个是Integer，另一个是String，所以取同一父类的最小级，为Object

/**指定泛型的时候*/
int a = GenericTest.<Integer>add(1, 2);//指定了Integer，所以只能为Integer类型或者其子类  
//        int b = GenericTest.<Integer>add(1, 2.2);//编译错误，指定了Integer，不能为Float  
Number c = GenericTest.<Number>add(1, 2.2); //指定为Number，所以可以为Integer和Float
```





##  4 泛型通配符

```
<?> 表示非限定通配符
用 <?> 通配符来表示任意类型。

```





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
两种限定通配符
上边界 <? extends T>， 类型必须是T的子类来设定类型的上界
下边界 <? super T>， 类型必须是T的父类来设定类型的下界

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
Array事实上并不支持泛型

不能创建一个确切的泛型类型的数组

Effective Java一书中建议使用List来代替Array，因为List可以提供编译期的类型安全保证，而Array却不能。
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

