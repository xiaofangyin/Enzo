package com.enzo.module_d.practice.designpattern.factory;

/**
 * 工厂方法模式：
 * 工厂方法模式与简单工厂模式差不多，区别在于工厂方法模式的工厂类，
 * 由多个方法组成，每个方法用来生产一个相应的对象。而简单工厂模式，
 * 是在一个方法中，根据传入的参数不同也生产不同的对象。
 * 所以他们的局限性也都一样。
 */
public class FactoryMethodMode {

    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        factory.produce().sendSms();
        factory.produce().sendEmail();
    }

    /**
     * 一个发送接口，用于发送短信或邮件
     */
    public interface Send {
        void sendSms();

        void sendEmail();
    }

    /**
     * 邮件类实现发送接口，发出邮件
     */
    public static class SendImpl implements Send {

        @Override
        public void sendSms() {
            System.out.println("this is a Sms message!");

        }

        @Override
        public void sendEmail() {
            System.out.println("this is a Mail message!");

        }
    }

    /**
     * 发送工厂类
     */
    public static class SendFactory {
        public Send produce() {
            return new SendImpl();
        }
    }
}
