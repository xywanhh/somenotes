package com.wh;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class GenericTest {
    static <T> T add(T x, T y) {
        return y;
    }

//    public static void fillNumberList(List<? super Number> list) {
//        list.add(new Integer(0));
//        list.add(new Float(1.0));
//    }
//    public static void fillNumberList(List<? extends Number> list) {
//        list.add(new Integer(0));
//        list.add(new Float(1.0));
//    }
//    public static void fillNumberList(List<?> list) {
//        list.add(new Integer(0));//编译错误
//        list.add(new Float(1.0));//编译错误
//    }
    public static void main(String[] args) {


        // List<?>和List<Object>之间的区别是什么?
        List<?> listOfAnyType;
        List<Object> listOfObject = new ArrayList<Object>();
        List<String> listOfString = new ArrayList<String>();
        List<Integer> listOfInteger = new ArrayList<Integer>();
        listOfAnyType = listOfString; //legal
        listOfAnyType = listOfInteger; //legal
//        listOfObjectType = (List<Object>) listOfString; //compiler error - in-convertible type

        // 泛型方法的类型推断
        int i = GenericTest.add(1, 2); // 这两个参数都是Integer，所以T替换为Integer类型
        Number f= GenericTest.add(1, 1.2); // 这两个参数一个是Integer，另一个是Float，所以取同一父类的最小级，为Number
        Object o = GenericTest.add(1, "asd"); // 这两个参数一个是Integer，另一个是String，所以取同一父类的最小级，为Object

        /**指定泛型的时候*/
        int a = GenericTest.<Integer>add(1, 2);//指定了Integer，所以只能为Integer类型或者其子类
//        int b = GenericTest.<Integer>add(1, 2.2);//编译错误，指定了Integer，不能为Float
        Number c = GenericTest.<Number>add(1, 2.2); //指定为Number，所以可以为Integer和Float

        int i1 = 1;
//        String si1 = (String) i1; // cannot cast int to java.lang.String
        List<Integer> li1 = new ArrayList<>();
        li1.add(11);
//        li1.add("aa"); // not allow
        try {
            // 利用反射突破泛型约束
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

        List l1 = new ArrayList(); // public class ArrayList<E>
        l1.add("aa"); // boolean add(E e);
        l1.add(11);

        // 指定泛型类型，编译期就会进行严格的类型校验
        List<String> l2 = new ArrayList<String>();
//        l2.add(111); // 编译报错

        List l3 = new ArrayList<String>(); // 没有限定
        l3.add(111); // 编译时会给出提示，unchecked call to 'add(E)'
        // Exception in thread "main"
        // java.lang.ClassCastException:
        // java.lang.Integer cannot be cast to java.lang.String
//        System.out.println((String)l3.get(0));

        // 泛型只在编译阶段有效
        List<String> stringArrayList = new ArrayList<String>();
        List<Integer> integerArrayList = new ArrayList<Integer>();
        Class classStringArrayList = stringArrayList.getClass();
        Class classIntegerArrayList = integerArrayList.getClass();
        if(classStringArrayList.equals(classIntegerArrayList)){
            System.out.println("泛型测试" + " 类型相同");
        }
        if(classStringArrayList == classIntegerArrayList) {
            System.out.println("泛型测试" + " 类型相同");
        }

        T1<String> t1 = new T1<String>();
//        T1<String> t1 = new T1<>(); // jdk8 类型推断
        t1.f1();
        t1.f2(11);
        t1.f2("aa");
//        t1.f3(22); // 泛型方法进行类型检查
        t1.f3("bb");
        t1.f21(11);
        t1.f21(true);

        T1 t11 = new T1();
        System.out.println(t11 instanceof T1); // true
//        System.out.println(t11 instanceof T1<String>); // illegal generic type for instanceof

        T21 t21 = new T21();
        System.out.println(t21.get());

        T22 t22 = new T22();
        System.out.println(t22.get());

        // 通配符
        T1<Number> a1 = new T1<>();
        T1<Integer> a2 = new T1<>();
        test1(a1);
//        test1(a2); // cannot be applied
        test2(a1);
        test2(a2);

        try {
            Object obj = instance(Class.forName("com.wh.GenericTest"));
            System.out.println(obj.getClass().getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        print(1, "aa", true, 1.01, new int[]{2, 2});

        T1<String> a3 = new T1<>();
        T1<Object> a4 = new T1<>();
        c1(a1);
        c1(a2);
//        c1(a3);
//        c1(a4);
        c2(a1);
        c2(a2);
//        c2(a3);
        c2(a4); // allow

        c11(a1);
        c11(a2);
//        c11(a3);
//        c11(a4);
        c22(a1);
        c22(a2);
//        c22(a3);
        c22(a4); // allow

        T3<Integer> t3 = new T3<>();
//        T3<String> t31 = new T3<>(); // is not within its bound

//        List<String>[] ls = new ArrayList<String>[10]; // generic array creation
        List<?>[] ls = new ArrayList<?>[10];
        List<String>[] ls1 = new ArrayList[10];

//        List<String>[] lsa = new List<String>[10]; // Not really allowed.
        Object oo = ls1;
        Object[] oa = (Object[]) oo;
        List<Integer> li = new ArrayList<Integer>();
        li.add(new Integer(3));
        oa[1] = li; // Unsound, but passes run time store check
//        String s = ls1[1].get(0); // Run-time error: ClassCastException.
        // 这种情况下，由于JVM泛型的擦除机制，在运行时JVM是不知道泛型信息的，所以可以给oa[1]赋上一个ArrayList而不会出现异常，
        // 但是在取出数据的时候却要做一次类型转换，所以就会出现ClassCastException


    }

    static void test1(T1<Number> t1) {
        t1.f1();
    }
    static void test2(T1<?> t1) {
        t1.f1();
    }

    static <T> T instance(Class<T> cls)
            throws IllegalAccessException, InstantiationException {
        return cls.newInstance();
    }

//    static T set(T t) {return t}; // cannot resolve symbol 'T'

    static <T> void print(T... args) {
        for (T t : args) {
            System.out.println(t);
        }
    }

    // 上边界，必须是子类型
    static <T> void c1(T1<? extends Number> t1) {
        t1.f1();
    }
    // 下边界，必须是父类型
    static <T> void c2(T1<? super Integer> t1) {
        t1.f1();
    }
    // 上边界，必须是子类型
    static void c11(T1<? extends Number> t1) {
        t1.f1();
    }
    // 下边界，必须是父类型
    static void c22(T1<? super Integer> t1) {
        t1.f1();
    }

}

class T1<T> {
//    static void b(T t) {} // cannot be refrenced from static context
    static <T> void b(T t) {}

    void f1() {
        System.out.println("f1");
    }
    // 这个T是一种全新的类型，可以与泛型类中声明的T不是同一种类型。
    <T> void f2(T t) {
        System.out.println(t);
    }
    // 这种泛型E可以为任意类型。可以类型与T相同，也可以不同。
    <E> void f21(E e) {
        System.out.println(e);
    }
    // 这个T就要与类的泛型类型一致
    void f3(T t) {
        System.out.println(t);
    }

}

class T21 implements T2 {
    public String get() {
        return "t21";
    }
}

// String作为泛型参数，not allow
/*class T22<String> implements T2 {
    public String get() {
        return "t21";
    }
}*/
class T22 implements T2<String> {
    public String get() {
        return "t21";
    }
}

interface T2<T> {
    T get();
}

class T3<T extends Number> {

}
