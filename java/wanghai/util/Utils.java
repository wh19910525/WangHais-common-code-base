/*******************************************************
 *******************************************************
 **
 ** Created By           : wanghai(frank)
 ** Created Date         : 2018-11-17
 ** Description          : java 通用工具类;
 **
 *******************************************************
 *******************************************************/

package wanghai.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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
     * 把 byte数组 转换为 字符串;
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


    /**
     * 把 byte数组 转换为 16进制 字符串
     *
     * @param byteArr 数组;
     *
     * @return 转换后的 16进制 字符串;
     *
     */
    public static String byteArrayToHexString(byte[] byteArr) {
        return byteArrayToHexString(byteArr, false);
    }

    /**
     * 把 byte数组 转换为 16进制 字符串
     *
     * @param byteArr 数组;
     * @param useSpaceToSeparate flag: 是否使用 空格作为分隔符;
     *
     * @return 转换后的 16进制 字符串;
     *
     */
    public static String byteArrayToHexString(byte[] byteArr, boolean useSpaceToSeparate) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < byteArr.length; n++) {
            stmp = Integer.toHexString(byteArr[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            if (useSpaceToSeparate) {
                sb.append(" ");//
            }
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * 把 int数组 转换为 字符串
     *
     * @param intArr 数组;
     * @param useSpaceToSeparate flag: 是否使用 空格作为分隔符;
     *
     * @return 转换后的 字符串;
     *
     */
    public static String intArrayToString(int[] intArr, boolean useSpaceToSeparate){ 
        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < intArr.length; i++)
        {
            strbuf.append(""+intArr[i]);
            if (useSpaceToSeparate) {
                strbuf.append(" ");//
            }
        }

        return strbuf.toString();
    }

    /**
     * 把 float数组 转换为 字符串
     *
     * @param floatArr 数组;
     * @param useSpaceToSeparate flag: 是否使用 空格作为分隔符;
     *
     * @return 转换后的 字符串;
     *
     */
    public static String floatArrayToString(float[] floatArr, boolean useSpaceToSeparate){ 
        StringBuffer strbuf = new StringBuffer();
        for (int i = 0; i < floatArr.length; i++)
        {
            strbuf.append(""+floatArr[i]);
            if (useSpaceToSeparate) {
                strbuf.append(" ");//
            }
        }

        return strbuf.toString();
    }

}
