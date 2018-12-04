/*******************************************************
 *******************************************************
 **
 ** Created By           : wanghai(frank)
 ** Created Date         : 2017-11-17
 ** Description          : 通过反射[android.os.SystemProperties] 
 **                            读取 Android 的 系统属性;
 **
 *******************************************************
 *******************************************************/

package wanghai.util.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SystemProperties {

    public static String get(String key) {
        init();

        String value = "";

        try {
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String get(String key, String def) {

        init();

        String value = null;

        try {
            value = (String) mGetMethod_Has_Def.invoke(mClassType, key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static boolean getBoolean(String key, boolean def) {
        init();

        boolean value = false;

        try {
            value = (boolean) mGetBooleanMethod.invoke(mClassType, key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getInt(String key, int def) {
        init();

        int value = 0;

        try {
            value = (int) mGetIntMethod.invoke(mClassType, key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void set(String key,String value){
        try {
            init();
            mSetMethod.invoke(mClassType, key,value);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // -------------------------------------------------------------------
    private static Class<?> mClassType = null;

    private static Method mGetMethod_Has_Def = null;

    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;
    private static Method mGetBooleanMethod = null;

    private static Method mSetMethod = null;

    private static void init() {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");

                /*
                 * 获取 系统属性;
                 */
                mGetMethod_Has_Def = mClassType.getDeclaredMethod("get", String.class, String.class);

                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
                mGetBooleanMethod = mClassType.getDeclaredMethod("getBoolean", String.class, boolean.class);

                /*
                 * 设置 系统属性;
                 */
                mSetMethod = mClassType.getDeclaredMethod("set", String.class,String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
