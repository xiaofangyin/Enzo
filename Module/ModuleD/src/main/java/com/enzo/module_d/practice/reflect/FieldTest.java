package com.enzo.module_d.practice.reflect;

import java.lang.reflect.Field;

public class FieldTest {

    public static void main(String[] args) {
        try{
            Class personClass = Class.forName("com.enzo.module_d.practice.reflect.Person");
            Person person = (Person) personClass.newInstance();

            //获取公用和私有的所有字段
            Field[] fields = personClass.getDeclaredFields();
            for (Field filed : fields){
                System.out.println(filed.getName());
            }

            //获取指定字段
            Field assignField = personClass.getDeclaredField("age");
            System.out.println("assignField: " + assignField.getName());

            //获取指定字段得值
            person.setName("小黄");
            Field nameField = personClass.getDeclaredField("name");
            nameField.setAccessible(true);
            System.out.println("name: " + nameField.get(person));

            //赋值
            Field ageField = personClass.getDeclaredField("age");
            ageField.set(person,18);
            System.out.println("age: " + ageField.get(person));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
