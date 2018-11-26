/*******************************************************
 *******************************************************
 **
 ** Created By           : wanghai(frank)
 ** Created Date         : 2017-11-17
 ** Description          : 格式化输出Log;
 **
 *******************************************************
 *******************************************************/

package wanghai.util;

import android.util.Log;

public class ALog {

    public static void I(String logtag, String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
        Log.i(logtag, logInfo);
    }

    public static void i(String logtag, String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
        Log.i(logtag, logInfo);
    }

    public static void E(String logtag, String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
        Log.e(logtag, logInfo);
    }

    public static void e(String logtag, String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
        Log.e(logtag, logInfo);
    }

    /*
     *Log的输出, 受开关控制;
     */
    public static void D(String logtag, boolean show_flag, String msg, Object... arguments) {
        if (show_flag) {
            getMethodNames(new Throwable().getStackTrace());
            logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
            Log.d(logtag, logInfo);
        }
    }

    public static void d(String logtag, boolean show_flag, String msg, Object... arguments) {
        if (show_flag) {
            getMethodNames(new Throwable().getStackTrace());
            logInfo = createLog(logtag, MessageFormatter.arrayFormat(msg, arguments));
            Log.d(logtag, logInfo);
        }
    }

/////////////////////////////////

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    //控制输出内容;
    public static boolean isOutput_ClassName = true;
    public static boolean isOutput_MethodName = true;
    public static boolean isOutput_LineNumber = true;

    /*
     * 保存 日志信息;
     */
    private static String logInfo = "";

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    private static String createLog(String logtag, String log) {
        String mOutPut_Log = null;

        if(log.length() == 0){
            mOutPut_Log = className + ", @" + methodName + ", [line:" + lineNumber + "]";
        }else{
            mOutPut_Log = className + ", @" + methodName + ", [line:" + lineNumber + "] ==>" + log;
        }

        return mOutPut_Log;
    }

}
