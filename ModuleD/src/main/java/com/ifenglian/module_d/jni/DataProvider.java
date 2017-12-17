package com.ifenglian.module_d.jni;

import com.ifenglian.commonlib.utils.toast.ToastUtils;

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

    public native int javaCallCGetInt(int x, int y);

    public native String javaCallCGetString(String str);

    public native int[] javaCallCGetIntArray(int[] array);


    //获取方法签名
    //xiaofydembp:debug xiaofangyin$ javap -s com.ifenglian.module_d.jni.DataProvider
    public void cCallJava1() {    //()V
        ToastUtils.showToast("=====helloFromJava=====");
    }

    public int cCallJava2(int x, int y) {   // (II)I
        int result = x + y;
        ToastUtils.showToast("x + y = " + (x + y));
        return result;
    }

    public void cCallJava3(String s) {  //(Ljava/lang/String;)V
        System.out.println(s + "hello");
    }

    public static void cCallJavaStatic() {  // ()V
        System.out.println("helloStatic");
    }

    public native void method1();

    public native void method2();

    public native void method3();

    public native void method4();
}
