
LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional 

LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SRC_FILES += $(call all-java-files-under, ../../java)

LOCAL_PACKAGE_NAME := atest_android_utils

LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)

