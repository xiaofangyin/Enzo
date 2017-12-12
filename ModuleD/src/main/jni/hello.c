//
// Created by 肖方银 on 2017/12/12.
//
#include <jni.h>

JNIEXPORT jint JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_add
  (JNIEnv * env, jobject obj, jint x, jint y){
    return x+y;
  };