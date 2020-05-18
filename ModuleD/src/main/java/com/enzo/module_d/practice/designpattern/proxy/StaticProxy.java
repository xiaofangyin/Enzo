package com.enzo.module_d.practice.designpattern.proxy;

/**
 * 静态代理
 * 由程序员创建或者由第三方工具生成，再进行编译；在程序运行之前，代理类的.class文件已经存在了。
 * 静态代理通常只代理一个类，并且要事先知道代理的是什么。示例：假如一个班上的同学要向老师提交作业，
 * 但是老师并不直接收每个人的作业，都是通过把作业交给学习委员，再由学习委员将作业转交给老师。
 * 在这里面就是学习委员代理班上学生上交作业，学习委员就是学生的代理。
 */
public class StaticProxy {

    public static void main(String[] args) {
        //学生自己交作业
        Student student = new Person("槑得说");
        student.submitHomework();

        //学习委员代交作业
        Person person = new Person("有得说");
        Student student2 = new StudentProxy(person);
        student2.submitHomework();
    }

    /**
     * 学生接口
     */
    public interface Student {
        //交作业方法
        void submitHomework();
    }

    /**
     * Person实现学生类,同时也就需要重写学生的交作业方法
     */
    public static class Person implements Student {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public void submitHomework() {
            System.out.println(name + "交作业啦！");
        }
    }

    /**
     * 学生的代理实现学生接口
     */
    public static class StudentProxy implements Student {

        private Person person;

        //构造方法，将学生对象委托给代理对象
        StudentProxy(Student student) {
            //student.getClass()为构造方法传入的对象,判断类是不是相同
            if (student.getClass() == Person.class) {
                //相当于学生给与了学习委员他的属性
                this.person = (Person) student;
            }
        }

        @Override
        public void submitHomework() {
            person.submitHomework();
        }
    }
}
