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
 * 如果 使用 Android libs, 添加以下 这个flag;
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
 * 如果没有 定义 Android LOG_TAG, 就使用 这个默认的TAG;
 */
#ifndef LOG_TAG
#define LOG_TAG "wanghai_debug"
#endif

/*
 * 如果没有 定义 MY_TAG, 就使用 这个默认的TAG;
 */
#ifndef MY_TAG
#define MY_TAG "<<-- wanghai-debug -->>"
#endif

///////////////////////////////////////////////
#ifdef BUILD_ANDROID

#ifdef USE_NDK_BUILD

#include <sys/system_properties.h>

#include <android/log.h>

#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

#else //USE_NDK_BUILD

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

#ifdef DEBUG_FLAG
#define MY_DEBUG(fmt, ...) \
    printf(MY_TAG " @%s, line=[%d], " fmt, __FUNCTION__, __LINE__, ##__VA_ARGS__);
#else
    #define MY_DEBUG(fmt, ...)
#endif

#define MY_INFO(fmt, ...) \
    printf(MY_TAG " @%s, line=[%d], " fmt, __FUNCTION__, __LINE__, ##__VA_ARGS__);

#endif //BUILD_ANDROID


#endif //__MY_DEBUG__

