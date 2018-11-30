package wanghai.util.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static wanghai.util.Utils.LOG_TAG;
import static wanghai.util.Utils.DEBUG_FLAG;

public class BitmapUtils {

    /*
     * 将bitmap调整到指定大小;
     */
    public static Bitmap adjustBitmapToSpecifySize(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }

        int height = origin.getHeight();
        int width = origin.getWidth();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);

        if (!origin.isRecycled()) {
            origin.recycle();
        }

        return newBM;
    }

    //按比例缩放
    public static Bitmap scaleBitmap(Bitmap origin, float scale) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(scale, scale);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    public static Bitmap cropBitmap(Bitmap bitmap) {//从中间截取一个正方形
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长

        return Bitmap.createBitmap(bitmap, (bitmap.getWidth() - cropWidth) / 2,
                (bitmap.getHeight() - cropWidth) / 2, cropWidth, cropWidth);
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {//把图片裁剪成圆形
        if (bitmap == null) {
            return null;
        }
        bitmap = cropBitmap(bitmap);//裁剪成正方形
        try {
            Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                        bitmap.getHeight()));
            float roundPx = 0.0f;
            roundPx = bitmap.getWidth();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return circleBitmap;
        } catch (Exception e) {
            return bitmap;
        }
    }

    /*
     * 旋转 bitmap;
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int rotate_angle){

        if(bitmap!=null){
            Matrix m=new Matrix();
            try{
                ALog.D(LOG_TAG, DEBUG_FLAG, "rotate bitmap angle={}", rotate_angle);

                m.setRotate(rotate_angle, bitmap.getWidth()/2, bitmap.getHeight()/2);
                Bitmap bmp2=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                bitmap.recycle();
                bitmap=bmp2;
            }catch(Exception ex){
                ALog.D(LOG_TAG, DEBUG_FLAG, "Create pic failed.");
            }
        }

        return bitmap;
    }

    /*
     * 把bitmap保存为 png;
     */
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

            ALog.I(LOG_TAG, "---------- save success ----------");
        }catch(Exception v0) {
            v0.printStackTrace();
            ALog.I(LOG_TAG, "---------- save picture filed --------");
            return false;
        }

        return true;
    }

}
