package wanghai.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by wanghai on 2018/7/19.
 */

public class Utils {

    /*
     * 把 字符串数组 转换为 字符串ArrayList;
     */
    public static List<String> stringArrayToStringList(String [] StringArray){
        List<String> list_string = new ArrayList<String>(StringArray.length);
        Collections.addAll(list_string, StringArray);

        return list_string;
    }

    /*
     * 把 YUV420sp 数据转换为 Only Y;
     */
    public static byte[] transYUV420spToOnlyY(byte[] buff, int height, int width) {
        byte[] only_y = new byte[width * height];
        int index = 0;
        System.arraycopy(buff, index, only_y, 0, width * height);

        return only_y;
    }

    /*
     * Byte数组 转换为 字符串;
     *
     *      如果 数据里有'\0'就停止;
     */
    public static String byteArrayToString(byte[] b){
        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < b.length; i++)
        {
            if (b[i] == '\0')
            {
                break;
            }
            strbuf.append((char) b[i]);
        }

        return strbuf.toString();
    }

}
