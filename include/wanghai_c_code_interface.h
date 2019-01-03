#ifndef __WANGHAI_COMMON_C_INTERFACE__
#define __WANGHAI_COMMON_C_INTERFACE__

#include <unistd.h>
#include <sys/file.h>

#include <errno.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <time.h>

#ifdef __cplusplus
extern "C" {
#endif

void markStartTime();
unsigned long markEndTime(char * print_info);

int saveData(const char * filename, unsigned char * data, int datalen);
int readData(const char * filename, unsigned char * data, int datalen);

int SystemProperties(const char *key, char *value, const char *default_value);


#ifdef __cplusplus
}
#endif

#endif //__WANGHAI_COMMON_C_INTERFACE__

