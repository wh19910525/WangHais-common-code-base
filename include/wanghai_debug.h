#ifndef __WANGHAI_DEBUG__
#define __WANGHAI_DEBUG__

#include "wanghai_c_code_interface.h"

///////////////////////////////////////////////
/*
 * 全局控制 debug 开关;
 */
//#define ENABLE_DEBUG
//#undef ENABLE_DEBUG

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
 * 如果要 重新 定义 LOG_TAG, 就 把 以下的代码, 放到 include <wanghai_debug.h> 之前;
 *
 *   #undef LOG_TAG
 *   #define LOG_TAG "xxx"
 */
#ifndef LOG_TAG
#define LOG_TAG "wanghai_debug"
#endif

/*
 * 如果要 重新 定义 MY_TAG, 就 把 以下的代码, 放到 include <wanghai_debug.h> 之前;
 *
 *   #undef MY_TAG
 *   #define MY_TAG "<<-- xxx-debug -->>"
 */
#ifndef MY_TAG
#define MY_TAG "<<-- wanghai-debug -->>"
#endif

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

/*
 * 在 Android 里, 使用 ALOGE;
 */
#define print_interface ALOGE

#endif //BUILD_ANDROID

/*
 * 缺省 使用 printf 打印
 */
#ifndef print_interface
#define print_interface printf
#endif

/*
 * 强制 打印接口;
 */
#define MY__INFO(fmt, ...) \
    ((void)print_interface(MY_TAG " @%s, line:[%d], " fmt, __func__, __LINE__, ##__VA_ARGS__))

/*
 * 条件控制 打印接口;
 */
#define MY__INFO_IF(cond, fmt, ...) \
    ( cond ? (MY__INFO(fmt, ##__VA_ARGS__)) : (void)0 )

/*
 * 开关控制 打印接口;
 */
#ifdef ENABLE_DEBUG
#define MY__DEBUG(fmt, ...) MY__INFO(fmt, ##__VA_ARGS__)
#else
#define MY__DEBUG(fmt, ...) //
#endif

/*
 * 定义 常用调试接口 别名
 */

/* 开关控制 打印接口 */
#define DBG         MY__DEBUG
#define DEBUG       MY__DEBUG
#define D           MY__DEBUG
#define dbg         MY__DEBUG
#define my_dbg      MY__DEBUG
#define oem_dbg     MY__DEBUG
#define OEM_DBG     MY__DEBUG
#define OEM_DEBUG   MY__DEBUG
#define trance      MY__DEBUG
#define TRANCE      MY__DEBUG


/* 强制 打印接口 */
#define INFO        MY__INFO
#define I           MY__INFO
#define info        MY__INFO
#define my_info     MY__INFO
#define oem_info    MY__INFO
#define OEM_INFO    MY__INFO
#define PRINT       MY__INFO
#define print       MY__INFO

/* 条件控制 打印接口 */
#define info_if     MY__INFO_IF
#define INFO_IF     MY__INFO_IF
#define print_if    MY__INFO_IF
#define PRINT_IF    MY__INFO_IF
#define log_if      MY__INFO_IF
#define LOG_IF      MY__INFO_IF
#define oem_info_if MY__INFO_IF

#endif //__WANGHAI_DEBUG__
