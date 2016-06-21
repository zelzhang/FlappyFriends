LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andengine_shared
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	D:\androidApp\FlappyFriends\andEngine\src\main\jni\Android.mk \
	D:\androidApp\FlappyFriends\andEngine\src\main\jni\Application.mk \
	D:\androidApp\FlappyFriends\andEngine\src\main\jni\build.sh \
	D:\androidApp\FlappyFriends\andEngine\src\main\jni\src\BufferUtils.cpp \
	D:\androidApp\FlappyFriends\andEngine\src\main\jni\src\GLES20Fix.c \

LOCAL_C_INCLUDES += D:\androidApp\FlappyFriends\andEngine\src\main\jni
LOCAL_C_INCLUDES += D:\androidApp\FlappyFriends\andEngine\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
