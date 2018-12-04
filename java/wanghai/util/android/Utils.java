package wanghai.util.android;

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

    /*
     * 为文件名, 添加 外部sdcard的路径;
     */
    public static String createSaveFilePath(String filename) {
        String sdcardPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        String file_name = sdcardPath + "/" + filename;

        return file_name;
    }

    /*
     * 将 预览帧数据 YUV420sp, 保存成图片, 最终保存到sd卡中
     */
    public static boolean savePreviewData(byte[] yuvdata, int width, int height,
            String picture_name, Rect rect, Context context) {

        String filename = createSaveFilePath(picture_name);

        Log.d(LOG_TAG, "decode success, save pic="+filename);

        Bitmap bitmap = null;
        try {

            YuvImage yuvimage = new YuvImage(yuvdata, ImageFormat.NV21, width, height, null);
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

        return BitmapUtils.saveBitmapAsPng(bitmap, filename, context);
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

}
