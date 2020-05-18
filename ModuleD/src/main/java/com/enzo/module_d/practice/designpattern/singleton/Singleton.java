package com.enzo.module_d.practice.designpattern.singleton;

public class Singleton {

    private Singleton() {
    }

    private static class SingletonBuild {
        private static Singleton value = new Singleton();
    }

    public Singleton getInstance() {
        return SingletonBuild.value;
    }
}
