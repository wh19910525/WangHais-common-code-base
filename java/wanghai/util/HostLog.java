/*******************************************************
 *******************************************************
 **
 ** Created By           : wanghai(frank)
 ** Created Date         : 2017-11-17
 ** Description          : 格式化输出Log;
 **
 *******************************************************
 *******************************************************/

package android.util;

public class HostLog {

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    //控制输出内容;
    public static boolean isOutput_ClassName = true;
    public static boolean isOutput_MethodName = true;
    public static boolean isOutput_LineNumber = true;

    public static boolean isEnable = true;//Log output flag;

    private static boolean isDebuggable() {
        return isEnable;
    }

    public static void setDebugEnable(boolean isEnable) {
        HostLog.isEnable = isEnable;
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    /*
     * Log的输出, 受开关控制;
     */
    public static void debug(String msg, Object... arguments) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
            System.out.println(logInfo);
        }
    }

    public static void d(String msg, Object... arguments) {
        if (isDebuggable()) {
            getMethodNames(new Throwable().getStackTrace());
            String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
            System.out.println(logInfo);
        }
    }

    /*
     *强制输出Log;
     */
    public static void error(String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
        System.out.println(logInfo);
    }

    public static void e(String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
        System.out.println(logInfo);
    }

    public static void info(String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
        System.out.println(logInfo);
    }

    public static void i(String msg, Object... arguments) {
        getMethodNames(new Throwable().getStackTrace());
        String logInfo = createLog(MessageFormatter.arrayFormat(msg, arguments));
        System.out.println(logInfo);
    }

    /*
     * 设置Log的输出格式;
     */
    private static String createLog(String log) {
        String mOutPut_Log = null;

        if(isOutput_ClassName && isOutput_MethodName && isOutput_LineNumber){
            mOutPut_Log = className + ", @" + methodName + ", [line:" + lineNumber + "]==>" + log;
        }
        else
        if(isOutput_ClassName && isOutput_LineNumber){
            mOutPut_Log = className + ", [line:" + lineNumber + "]==>" + log;
        }
      
        return mOutPut_Log;
    }

}
