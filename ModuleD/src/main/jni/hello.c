//
// Created by 肖方银 on 2017/12/12.
//
#include <jni.h>

#include <android/log.h>
#define LOG_TAG "CLOG"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

JNIEXPORT jint JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_add
  (JNIEnv * env, jobject obj, jint x, jint y){
    return x+y;
  };

char* Jstring2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "GB2312");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
			strencode); // String .getByte("GB2312");
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1); //"\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
	return rtn;
}

JNIEXPORT jstring JNICALL Java_com_ifenglian_module_1d_jni_DataProvider_helloFromC
  (JNIEnv * env, jobject obj, jstring jstr){
     char* cstr = Jstring2CStr(env,jstr);
     strcat(cstr," haha");
     return (*env)->NewStringUTF(env,cstr);
  };