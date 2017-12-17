//
// Created by 肖方银 on 2017/12/12.
//
#include <jni.h>

#include <android/log.h>
#define LOG_TAG "CLOG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

char*   Jstring2CStr(JNIEnv*   env,   jstring   jstr)
{
	 char*   rtn   =   NULL;  // 创建了空的字符串
	 jclass   clsstring   =   (*env)->FindClass(env,"java/lang/String"); // 获取到了String类的字节码
	 jstring   strencode   =   (*env)->NewStringUTF(env,"GB2312");// 把编码从char*类型 转换成了 jstring
	 jmethodID   mid   =   (*env)->GetMethodID(env,clsstring,   "getBytes",   "(Ljava/lang/String;)[B");
	 jbyteArray   barr=   (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode); // String .getByte("GB2312");
	 jsize   alen   =   (*env)->GetArrayLength(env,barr);  //  获取到byte数组的长度
	 jbyte*   ba   =   (*env)->GetByteArrayElements(env,barr,JNI_FALSE); // 遍历byte数组  获取到了第一个元素的首地址
	 if(alen   >   0)
	 {
	  rtn   =   (char*)malloc(alen+1);         //"\0"
	  memcpy(rtn,ba,alen);  //  把byte数组 复制到char*内存中  复制的长度 就是byte数组的长度
	  rtn[alen]=0;   //  把char*最后一位改成0
	 }
	 (*env)->ReleaseByteArrayElements(env,barr,ba,0);  // 释放掉了byte数组
	 return rtn;
}

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    javaCallCGetInt
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_javaCallCGetInt
  (JNIEnv * env, jobject obj, jint x, jint y){
    return x+y;
  };

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    javaCallCGetString
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_javaCallCGetString
  (JNIEnv * env, jobject obj, jstring jstr){
     char* cstr = Jstring2CStr(env,jstr);
     strcat(cstr," haha");
     return (*env)->NewStringUTF(env,cstr);
  };

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    javaCallCGetIntArray
 * Signature: ([I)[I
 */
JNIEXPORT jintArray JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_javaCallCGetIntArray
  (JNIEnv * env, jobject obj, jintArray intArray){
     int length = (*env)->GetArrayLength(env,intArray);
     int* element = (*env)->GetIntArrayElements(env, intArray, 0);
     int i = 0;
     for(;i < length;i++){
        *(element + i) += 10;
     }
     return intArray;
  };

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    method1
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_method1
  (JNIEnv * env, jobject obj){
//	Class<?> loadClass = MainActivity.class.getClassLoader().loadClass("com.itheima.ndkcallback.DataProvider");
	//jclass      (*FindClass)(JNIEnv*, const char*);
	// 反射加载类的字节码
	jclass clazz=(*env)->FindClass(env,"com/ifenglian/module_d/jni/DataProvider");

//	Method method = loadClass.getMethod("helloFromJava", (Class[]) null );
	// 第四个参数  方法签名(表示方法返回值和参数)
//	jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
	jmethodID methodID=(*env)->GetMethodID(env,clazz,"cCallJava1","()V");

//	method.invoke(loadClass.newInstance(), (Object[]) null );
	// void        (*CallVoidMethod)(JNIEnv*, jobject, jmethodID, ...);
	(*env)->CallVoidMethod(env,obj,methodID);
};

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    method2
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_method2
  (JNIEnv * env, jobject obj){

};

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    method3
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_method3
  (JNIEnv * env, jobject obj){

};

/*
 * Class:     com_ifenglian_module_d_jni_DataProvider
 * Method:    method4
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_method4
  (JNIEnv * env, jobject obj){

};
