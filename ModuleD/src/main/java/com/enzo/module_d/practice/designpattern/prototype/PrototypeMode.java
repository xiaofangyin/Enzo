package com.enzo.module_d.practice.designpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 原型模式:
 * 设计过程中发现一个类中具有100多个属性，
 * 但是每次实际需要变换的属性只有几个这个时候我们就要一种设计模式使得我们设计的对象跟之前对象保持一样的属性值，
 * 这样我们只需要修改部分属性值就可以达到效果，这个时候就需要一种设计模式--原型模式。
 */
public class PrototypeMode {

    public static void main(String[] args) throws CloneNotSupportedException {
        Person person = new Person();
        person.setName("zhangsan");
        person.setAge(15);
        Person person1 = (Person) person.clone();
        System.out.println(person == person1);
        person.setAge(10);
        System.out.println(person.getAge());
        System.out.println(person1.getAge());
    }

    //实现两个接口
    public static class Person implements Cloneable, Serializable {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        //重写Cloneable的clone方法，该方法内部实现序列化和反序列化
        @Override
        protected Object clone() throws CloneNotSupportedException {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(bo);
                objectOutputStream.writeObject(this);
                byte[] bytes = bo.toByteArray();
                ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(bs);
                Object object = objectInputStream.readObject();
                return object;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
