#ifndef __WANGHAI_COMMON_C_INTERFACE__
#define __WANGHAI_COMMON_C_INTERFACE__

void markStartTime();
unsigned long markEndTime(char * print_info);

int saveData(const char * filename, unsigned char * data, int datalen);
int readData(const char * filename, unsigned char * data, int datalen);

int SystemProperties(const char *key, char *value, const char *default_value);

#endif //__WANGHAI_COMMON_C_INTERFACE__

