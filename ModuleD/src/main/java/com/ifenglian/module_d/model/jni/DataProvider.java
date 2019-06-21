package com.ifenglian.module_d.model.jni;

import com.enzo.commonlib.utils.common.ToastUtils;

/**
 * 文 件 名: DataProvider
 * 创 建 人: xiaofangyin
 * 创建日期: 2017/12/12
 * 邮   箱: xiaofangyinwork@163.com
 */
public class DataProvider {
    /**
     *
     *  生成头文件:
     *  目录：/Users/xiaofangyin/AndroidStudioProjects/Enzo/ModuleD/src/main/java
     *  指令：javah com.ifenglian.module_d.model.jni.DataProvider
     *
     *  获取方法签名
     *  目录：/Users/xiaofangyin/AndroidStudioProjects/Enzo/ModuleD/build/intermediates/classes/debug
     *  指令：xiaofydembp:debug xiaofangyin$ javap -s com.ifenglian.module_d.model.jni.DataProvider
     *
     *  交叉编译
     *  目录：/Users/xiaofangyin/AndroidStudioProjects/Enzo/ModuleD/src/main/jni
     *  指令：ndk-build
     */

    static {
        System.loadLibrary("MyJni");
    }

    public native int javaCallCGetInt(int x, int y);

    public native String javaCallCGetString(String str);

    public native int[] javaCallCGetIntArray(int[] array);

    public void cCallJava1() {    //()V
        ToastUtils.showToast("=====helloFromJava=====");
    }

    public int cCallJava2(int x, int y) {   // (II)I
        int result = x + y;
        ToastUtils.showToast("x + y = " + (x + y));
        return result;
    }

    public void cCallJava3(String s) {  //(Ljava/lang/String;)V
        ToastUtils.showToast(s + " 我是java");
    }

    public static void cCallJavaStatic() {  // ()V
        ToastUtils.showToast("=====C调用静态方法=====");
    }

    public native void method1();

    public native void method2();

    public native void method3();

    public native void method4();
}
