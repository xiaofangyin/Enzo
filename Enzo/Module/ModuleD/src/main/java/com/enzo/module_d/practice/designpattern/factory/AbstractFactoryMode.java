package com.enzo.module_d.practice.designpattern.factory;

/**
 * 抽象工厂模式
 * 抽象工厂模式是所有形态的工厂模式中最为抽象和一般性的，
 * 抽象工厂模式可以向客户端提供一个接口，使得客户端在不必指定产品的具体类型的情况下，
 * 创建多个产品族的产品对象。具有比较好的扩展性，但要写较多的类和接口，
 * 前两种工厂模式理解上会困难一些。
 */
public class AbstractFactoryMode {

    public static void main(String[] args) {
        FruitFactory cc = new InnerFruitFactory();
        Fruit apple3 = cc.getApple();
        apple3.get();
        Fruit banana3 = cc.getBanana();
        banana3.get();
    }

    public interface FruitFactory {
        //实例化Apple
        public Fruit getApple();

        //实例化Banana
        public Fruit getBanana();
    }

    public static class InnerApple extends Apple {
        @Override
        public void get() {
            System.out.println("长在室内的苹果");
        }
    }

    public static class InnerBanana extends Banana {
        @Override
        public void get() {
            System.out.println("长在室内的香蕉");
        }
    }

    public static abstract class Apple implements Fruit {

        public abstract void get();
    }

    public static abstract class Banana implements Fruit {

        public abstract void get();
    }

    public interface Fruit {

        void get();
    }

    public static class InnerFruitFactory implements FruitFactory {
        @Override
        public Fruit getApple() {
            return new InnerApple();
        }

        @Override
        public Fruit getBanana() {
            return new InnerBanana();
        }
    }
}
