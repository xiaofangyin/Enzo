package com.enzo.module_d.practice.reflect;

import java.lang.reflect.Constructor;

public class ConstructorTest {

    public static void main(String[] args) {
        try {
            Class<Person> personClass = (Class<Person>) Class.forName("com.enzo.module_d.practice.reflect.Person");
            Constructor<Person>[] constructors = (Constructor<Person>[]) personClass.getConstructors();
            for (Constructor constructor : constructors) {
                System.out.println("构造方法：" + constructor.getName());
            }

            //调用构造器的 newInstance() 方法创建对象
            Constructor c = personClass.getConstructor(String.class);
            Person person = (Person) c.newInstance("小明");
            System.out.println("name: " + person.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
