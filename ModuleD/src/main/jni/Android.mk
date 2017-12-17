LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MyJni
LOCAL_SRC_FILES := MyJni.c
LOCAL_LDLIBS += -llog

include $(BUILD_SHARED_LIBRARY)
