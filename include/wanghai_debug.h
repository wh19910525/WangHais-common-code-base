#ifndef __MY_DEBUG__
#define __MY_DEBUG__

#include "wanghai_c_code_interface.h"

///////////////////////////////////////////////
/*
 * 控制 debug 开关;
 */
#define DEBUG_FLAG
//#undef  DEBUG_FLAG

/*
 * 如果 使用 Aosp里的 libs, 添加以下 这个flag;
 *
 * LOCAL_CFLAGS += -DBUILD_ANDROID
 *
 */

/*
 * 如果 使用NDK编译, 就要使用 NDk里 提供的 libs, 需要 添加以下 这个flag;
 *
 * LOCAL_CFLAGS += -DUSE_NDK_BUILD
 *
 */

/*
 * 如果要 重新 定义 Android LOG_TAG, 就 把 以下的代码, 放到 include <wanghai_debug.h> 的 下面;
 *
 *   #ifndef LOG_TAG
 *   #define LOG_TAG "new_tag"
 *   #endif
 */
#undef LOG_TAG
#define LOG_TAG "wanghai_debug"
/*
 * 如果要 重新 定义 MY_TAG, 就 把 以下的代码, 放到 include <wanghai_debug.h> 的 下面;
 *
 *   #ifndef MY_TAG
 *   #define MY_TAG "<<-- wanghai-debug -->>"
 *   #endif
 */
#undef MY_TAG
#define MY_TAG "<<-- wanghai-debug -->>"

///////////////////////////////////////////////
#ifdef BUILD_ANDROID

#ifdef USE_NDK_BUILD

//使用 NDK编译:
#include <sys/system_properties.h>

#include <android/log.h>

#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#else //USE_NDK_BUILD

//使用 AOSP编译:
#include <utils/Log.h>

#include <cutils/properties.h>

#endif //USE_NDK_BUILD

#ifdef DEBUG_FLAG
#define MY_DEBUG(fmt, ...) \
    ALOGE(MY_TAG " @%s, line=[%d], " fmt, __FUNCTION__, __LINE__, ##__VA_ARGS__);
#else
    #define MY_DEBUG(fmt, ...) //
#endif

#define MY_INFO(fmt, ...) \
    ALOGE(MY_TAG " @%s, line=[%d], " fmt, __FUNCTION__, __LINE__, ##__VA_ARGS__);

#else //BUILD_ANDROID

//使用 printf 打印:
#ifdef DEBUG_FLAG
#define MY_DEBUG(fmt, ...) \
    printf(MY_TAG " @%s, line=[%d], " fmt "\n", __FUNCTION__, __LINE__, ##__VA_ARGS__);
#else
    #define MY_DEBUG(fmt, ...)
#endif

#define MY_INFO(fmt, ...) \
    printf(MY_TAG " @%s, line=[%d], " fmt "\n", __FUNCTION__, __LINE__, ##__VA_ARGS__);

#endif //BUILD_ANDROID

/*
 * 常用调试接口:
 */

/* 带开关的 调试接口 */
#define DBG         MY_DEBUG
#define DEBUG       MY_DEBUG
#define D           MY_DEBUG
#define dbg         MY_DEBUG
#define my_dbg      MY_DEBUG

/* 调试接口 */
#define INFO        MY_INFO
#define I           MY_INFO
#define info        MY_INFO
#define my_info     MY_INFO

#endif //__MY_DEBUG__
