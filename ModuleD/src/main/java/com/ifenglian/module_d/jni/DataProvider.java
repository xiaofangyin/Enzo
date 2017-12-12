package com.ifenglian.module_d.jni;

/**
 * 文 件 名: DataProvider
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofy@ifenglian.com
 */
public class DataProvider {

    static {
        System.loadLibrary("hello");
    }

    public native int add(int x, int y);

    public native String helloFromC(String str);

    public native int[] getIntArray(int[] array);
}
