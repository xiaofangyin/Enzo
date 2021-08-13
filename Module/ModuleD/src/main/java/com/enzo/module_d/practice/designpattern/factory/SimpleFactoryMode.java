package com.enzo.module_d.practice.designpattern.factory;

/**
 * 简单工厂模式
 */
public class SimpleFactoryMode {

    public static void main(String[] args) {
        SendFactory factory = new SendFactory();
        factory.produce(MessageSenderType.SMS).send();
        factory.produce(MessageSenderType.MAIL).send();
    }

    /**
     * 一个发送接口，用于发送短信或邮件
     */
    public interface Send {
        void send();
    }

    /**
     * 邮件类实现发送接口，发出邮件
     */
    public static class Mail implements Send {

        @Override
        public void send() {
            System.out.println("this is a Mail message!");
        }
    }

    /**
     * 短信类实现发送接口
     */
    public static class Sms implements Send {
        @Override
        public void send() {
            System.out.println("this is a Sms message!");
        }
    }

    /**
     * 发送工厂类
     */
    public static class SendFactory {
        public Send produce(MessageSenderType type) {
            if (MessageSenderType.SMS.equals(type)) {
                return new Sms();
            } else if (MessageSenderType.MAIL.equals(type)) {
                return new Mail();
            }
            return null;
        }
    }

    /**
     * 发送类型枚举类
     */
    public enum MessageSenderType {
        //短信
        SMS,
        //邮箱
        MAIL
    }
}
