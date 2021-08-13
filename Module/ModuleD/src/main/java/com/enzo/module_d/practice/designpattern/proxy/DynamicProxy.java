package com.enzo.module_d.practice.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * https://www.cnblogs.com/xiaoluo501395377/p/3383130.html
 * 我们根据加载被代理类的时机不同，将代理分为静态代理和动态代理。
 * 如果我们在代码编译时就确定了被代理的类是哪一个，那么就可以直接使用静态代理；
 * 如果不能确定，那么可以使用类的动态加载机制，在代码运行期间加载被代理的类这就是动态代理
 */
public class DynamicProxy {

    public static void main(String[] args) {
        //    我们要代理的真实对象
        final Subject realSubject = new RealSubject();

        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        Subject subject = (Subject) Proxy.newProxyInstance(
                realSubject.getClass().getClassLoader(),
                realSubject.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Method:" + method);

                        if (method.getName().equals("rent")) {
                            //　　在代理真实对象前我们可以添加一些自己的操作
                            System.out.println("before rent house");
                            //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
                            method.invoke(realSubject, args);
                            //　　在代理真实对象后我们也可以添加一些自己的操作
                            System.out.println("after rent house");
                        } else if (method.getName().equals("hello")) {
                            System.out.println("before hello");
                            method.invoke(realSubject, args);
                            System.out.println("after hello");
                        }
                        return null;
                    }
                });

        System.out.println(subject.getClass().getName());
        subject.rent();
        subject.hello("world");
    }

    public static class RealSubject implements Subject {
        @Override
        public void rent() {
            System.out.println("I want to rent my house");
        }

        @Override
        public void hello(String str) {
            System.out.println("hello: " + str);
        }
    }

    public interface Subject {
        public void rent();

        public void hello(String str);
    }
}
