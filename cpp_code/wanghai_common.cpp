#include "../include/wanghai_debug.h"

using namespace std;

///////////////// 统计 程序执行的 时间戳 ////////////////
static clock_t start_record_time, end_record_time;
static unsigned long total_cost_time;

void markStartTime()
{
    //开始记录
    start_record_time = clock();
}

unsigned long markEndTime(char * print_info)
{
    //结束记录
    end_record_time = clock();

    //cost = (double)(end - begin)/CLOCKS_PER_SEC;

    //printf("constant CLOCKS_PER_SEC is: %ld, time cost is: %lf secs\n", CLOCKS_PER_SEC, cost);
    total_cost_time = (unsigned long)(end_record_time - start_record_time);

    MY_DEBUG(" ----------> [%s], cost time = [%ld] us\n", print_info, total_cost_time);
    printf("{%s}, cost time = [%ld] us\n", print_info, total_cost_time);

    return total_cost_time;
}

///////////////////////////////////////////

/*
 * 保存 数据 到 文件;
 */
int saveData(const char * filename, unsigned char * data, int datalen){
    MY_DEBUG("time=[%s, %s]\n", __DATE__, __TIME__);

    int ret = -1;
    int file_fd = ::open(filename, O_RDWR | O_CREAT, 0755);
    if (file_fd < 0)
    {
        MY_DEBUG("Unable to open file[%s]:%s\n", filename, strerror(errno));
        return -1;
    }

    ret = ::write(file_fd, data, datalen);
    if(ret < 0){
        MY_DEBUG("Unable to write file[%s]:%s\n", filename, strerror(errno));
        return -2;
    }

    ::close(file_fd);

    return ret;
}

/*
 * 从文件里 读取 数据;
 */
int readData(const char * filename, unsigned char * data, int datalen){
    MY_DEBUG("time=[%s, %s], buf addr=0x%X\n", __DATE__, __TIME__, data);

    int ret = -1;

    //MY_DEBUG("Test[0]=%d, raw[rawlen/2]=%d, raw[rawlen]=%d\n", data[0], data[datalen/2], data[datalen-1]);

#ifndef O_BINARY
#  define O_BINARY  0
#endif
    int file_fd = ::open(filename, O_RDONLY | O_BINARY);
    if (file_fd < 0)
    {
        MY_DEBUG("Unable to open file[%s]:%s\n", filename, strerror(errno));
        return -1;
    }else{
        MY_DEBUG("Open file[%s], success.\n", filename);
    }

    bzero(data, sizeof(data));

    ret = ::read(file_fd, data, datalen);
    if(ret > 0){
        MY_DEBUG("data len=%d\n", ret);
    }

    ::close(file_fd);

    return ret;
}

#ifdef BUILD_ANDROID
/*
 * 由于 NDK里 只有 __system_property_get,
 *     为了统一, 所以我们就直接实现了 一个类似 property_get 函数;
 */
int SystemProperties(const char *key, char *value, const char *default_value)
{
    int len;

    len = __system_property_get(key, value);
    if(len > 0) {
        return len;
    }

    if(default_value) {
        len = strlen(default_value);
        if (len >= PROP_VALUE_MAX) {
            len = PROP_VALUE_MAX - 1;
        }
        memcpy(value, default_value, len);
        value[len] = '\0';
    }
    return len;
}
#endif //BUILD_ANDROID

