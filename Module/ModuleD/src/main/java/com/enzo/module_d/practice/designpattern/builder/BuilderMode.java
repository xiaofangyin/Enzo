package com.enzo.module_d.practice.designpattern.builder;

/**
 * 建造者模式
 * 优点:可以使得创建对象赋值变得灵活一些
 */
public class BuilderMode {

    public static void main(String[] args) {
        User user = new User.UserBuilder().password("sss").username("ssss").build();
    }

    public static class User {
        private String username;
        private String password;
        private Integer id;

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", id=" + id +
                    '}';
        }

        public User(String username, String password, Integer id) {
            this.username = username;
            this.password = password;
            this.id = id;
        }

        public static class UserBuilder {
            private String username;
            private String password;
            private Integer id;

            //设计技巧：返回值是该内部类对象为了调用时候实现链式编程
            public UserBuilder username(String username) {
                this.username = username;
                return this;
            }

            public UserBuilder password(String password) {
                this.password = password;
                return this;
            }

            public UserBuilder id(Integer id) {
                this.id = id;
                return this;
            }

            public User build() {
                return new User(this.username, this.password, this.id);
            }
        }
    }
}
