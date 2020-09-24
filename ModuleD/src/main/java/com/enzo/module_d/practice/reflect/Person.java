package com.enzo.module_d.practice.reflect;

public class Person {

    int age;
    private String name;

    public Person(String name){
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void hello() {
        System.out.println("person hello...");
    }
}
