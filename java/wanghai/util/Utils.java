package wanghai.util;

import android.graphics.YuvImage;
import android.graphics.ImageFormat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import android.util.Log;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import android.os.Environment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.graphics.Point;
import android.view.WindowManager;
import android.view.Display;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by wanghai on 2018/7/19.
 */

public class Utils {

    public static String LOG_TAG = "wanghai_debug";

    public static boolean DEBUG_FLAG = SystemProperties.getBoolean("persist.display.Alog", false)
        || SystemProperties.getBoolean("debug.display.Alog", false);

    public static boolean SHOW_DEBUG_MENU = SystemProperties.getBoolean("persist.show.debug.menu", false)
        || SystemProperties.getBoolean("debug.show.debug.menu", false);

    //////////////////////////////////////
    public static void update_static_variable(){
        SHOW_DEBUG_MENU = SystemProperties.getBoolean("persist.show.debug.menu", false)
            || SystemProperties.getBoolean("debug.show.debug.menu", false);
    }

    //////////////////////////////////////
    public static String DEFAULT_PREVIEW_SIZE = SystemProperties.get("persist.defPreviewSize", null);
    public static final String automaticMatchPreviewSize = "Automatic match";
    public static final String[] previewSize = {
        "768x432",
        "640x480",
        automaticMatchPreviewSize
    };

    //////////////////////////////////////
    public static final String Zbar = "Zbar";
    public static final String Zxing = "Zxing";
    public static final String Zbar_add_Zxing = "Zbar+Zxing";
    public static final String automaticSelectDecodeLib = "Automatic select D-lib";
    public static final String[] systemSupportDecodeLibs = {
        Zbar,
        Zxing,
        Zbar_add_Zxing,
        automaticSelectDecodeLib
    };


    //////////////////////////////////////
    public static boolean isDisplayDecodeDateView_Flag = false;
    public static int[] bitmapGrayPixesArray = null;
    public static boolean ENABLE_NEXGO_PRE_HANDLE_DECODE_DATA_FLAG = SystemProperties.getBoolean("debug.use_nexgo_decode_data", true);

    /*
     * 控制 保存 解码图片的flag;
     */
    public static boolean startSaveDecodeData2Picture_Flag = false;

    //////////////////////////////////////
    /*
     * 全屏预览 模式, 预览图像可能被拉升;
     *     预览图像的尺寸和屏幕的尺寸,
     *     在宽高上 的 比例 差距越大, 拉升的越严重;
     */
    public static boolean USE_FULL_SCREEN_PREVIEW = SystemProperties.getBoolean("debug.useFullScreenPreview", true);

    //////////////////////////////////////////////////////
    public static final String ACTION_RESULT_SCANNER = "com.nexgo.scanner.result";
    public static final String ACTION_CLOSE_SCANNER = "com.nexgo.scanner.close";

    //////////////////////////////////////////////////////
    public static final String CODEBAR_TYPE = "type";
    public static final String CODEBAR_VALUE = "value";

    //////////////////////////////////////////////////////
    public enum DecodeExternalePictureStatus {
        START_DECODE,
        END_DECODE,
        NONE
    }

    /*
     * 解码外部图片的时候 设置这个flag;
     */
    public static DecodeExternalePictureStatus isDecodingExternalePicture = DecodeExternalePictureStatus.NONE;

    public static int external_pic_width = 0;
    public static int external_pic_height = 0;

    //////////////////////////////////////////////////////
    public static String SAVE_PIC_DIR = "/decode-picture";

    public static byte[] lastPreviewData = null;
    public static int dataWidth = 0;
    public static int dataHeight = 0;
    public static String fileName = null;

    public enum SavePreviewDataStatus {
        START_SAVE_PREVIEW_DATA,
        END_SAVE_PREVIEW_DATA,
        NONE
    }

    public static SavePreviewDataStatus isSavingPreviewData = SavePreviewDataStatus.NONE;

    public static String createSaveFilePath(String filename) {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String save_previewData_path = sdcardPath + SAVE_PIC_DIR;

        File dir_name = new File(save_previewData_path); 
        if (!dir_name.isDirectory()) 
        { 
            Log.d(LOG_TAG, dir_name + " no exist, so create it.");
            dir_name.mkdir();
        } else {
            Log.d(LOG_TAG, dir_name + " exist.");
        }

        String file_name = save_previewData_path + "/" + filename;

        return file_name;
    }

    /*
     * 将 预览帧数据, 保存成图片, 最终保存到sd卡中
     */
    public static boolean savePreviewData(byte[] data, int width, int height,
            String picture_name, Rect rect, Context context) {

        String filename = createSaveFilePath(picture_name);

        Log.d(LOG_TAG, "decode success, save pic="+filename);

        Bitmap bitmap = null;
        try {

            YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int quality = 100;
            if(rect == null){
                yuvimage.compressToJpeg(new Rect(0, 0, width, height), quality, baos);
            }else{
                ALog.D(LOG_TAG, DEBUG_FLAG, "rect:" + rect);
                yuvimage.compressToJpeg(rect, quality, baos);
            }
            byte[] jdata = baos.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
        }

        return saveBitmapAsPng(bitmap, filename, context);
    }

    public static boolean saveBitmapAsPng(Bitmap bmp, String filename, Context context) {
        //ALog.D(LOG_TAG, DEBUG_FLAG, "file_name="+filename);

        File file = new File(filename);
        if(file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            //设置PNG的话，透明区域不会变成黑色
            bmp.compress(Bitmap.CompressFormat.PNG, 100, ((OutputStream)fileOutputStream));

            fileOutputStream.flush();
            fileOutputStream.close();

            scanFile(context, filename);

            ALog.I(LOG_TAG, "---------- save success ----------");
        }catch(Exception v0) {
            v0.printStackTrace();
            ALog.I(LOG_TAG, "---------- save picture filed --------");
            return false;
        }

        return true;
    }

    /*
     * 更新 单个图片 文件的Uri;
     */
    public static void scanFile(Context ctx, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        ctx.sendBroadcast(scanIntent);
    }

    /*
     * 扫描整个Sdcard;
     */
    public static void scanSdcard(Context context) {
        context.sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

    /*
     * 获取 屏幕分辨率(像素);
     *    宽度 = 硬件屏幕宽度;
     *    高度 = 硬件屏幕高度 - Navigationbar 宽度;
     */
    public static Point getScreenResolutionNoNav(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();

        Point theScreenResolution = new Point();
        display.getSize(theScreenResolution);

        return theScreenResolution;
    }

    /*
     * 把 字符串数组 转换为 字符串ArrayList;
     */
    public static List<String> StringArray_to_StringList(String [] StringArray){
        List<String> list_string = new ArrayList<String>(StringArray.length);
        Collections.addAll(list_string, StringArray);

        return list_string;
    }

    /*
     * 把 YUV420sp 数据转换为 Only Y;
     */
    public static byte[] Trans_YUV420sp_to_OnlyY(byte[] buff, int height, int width) {
        byte[] only_y = new byte[width * height];
        int index = 0;
        System.arraycopy(buff, index, only_y, 0, width * height);

        return only_y;
    }

    /*
     * Byte数组 转换为 字符串;
     *
     *      如果数据里有'\0'就停止;
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
