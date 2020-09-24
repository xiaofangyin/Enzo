package com.enzo.module_d.practice.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodTest {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class personClass = Class.forName("com.enzo.module_d.practice.reflect.Person");

        //获取所有得方法
        Method[] methods = personClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        Person obj = (Person) personClass.newInstance();
        Method method = personClass.getDeclaredMethod("setAge", int.class);
        method.invoke(obj,17);
        System.out.println("age: " + obj.getAge());

        Method hello = personClass.getDeclaredMethod("hello");
        hello.setAccessible(true);
        hello.invoke(obj);
    }
}
