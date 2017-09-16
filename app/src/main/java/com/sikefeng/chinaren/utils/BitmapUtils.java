package com.sikefeng.chinaren.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.sikefeng.chinaren.utils.event.CameraEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class BitmapUtils {
    public static final String TAG = "BitmapUtils";

    /**
     * 图片质量
     */
    private static final int QUALITY = 100;

    /**
     * 图片最大尺寸
     */
    private static final int IMAGE_MAX_SIZE = 800;

    /**
     * 旋转90
     */
    private static final int ROTATE_DEGRESS_90 = 90;
    /**
     * 旋转180
     */
    private static final int ROTATE_DEGRESS_180 = 180;
    /**
     * 旋转270
     */
    private static final int ROTATE_DEGRESS_270 = 270;
    /**
     * 旋转360
     */
    private static final int ROTATE_DEGRESS_360 = 360;
    /**
     * 1024
     */
    private static final int BYTE_1024 = 1024;
    /**
     * values9
     */
    private static final int VALUES_9 = 9;


    /**
     * 保存
     *
     * @param b            Bitmap
     * @param absolutePath String
     * @return boolean
     */
    public static boolean saveBitmap(Bitmap b, String absolutePath) {
        return saveBitmap(b, absolutePath, QUALITY);
    }

    /**
     * 保存
     *
     * @param b            Bitmap
     * @param absolutePath 路径
     * @param format       格式
     * @return boolean
     */
    public static boolean saveBitmap(Bitmap b, String absolutePath, Bitmap.CompressFormat format) {
        return saveBitmap(b, absolutePath, QUALITY, format);
    }

    /**
     * 保存
     *
     * @param b            Bitmap
     * @param absolutePath 路径
     * @param quality      质量
     * @return boolean
     */
    public static boolean saveBitmap(Bitmap b, String absolutePath, int quality) {
        return saveBitmap(b, absolutePath, quality, Bitmap.CompressFormat.JPEG);
    }

    /**
     * 保存
     *
     * @param b            Bitmap
     * @param absolutePath 路径
     * @param quality      质量
     * @param format       格式
     * @return boolean
     */
    public static boolean saveBitmap(Bitmap b, String absolutePath, int quality, Bitmap.CompressFormat format) {
        String fileName = absolutePath;
        File f = new File(fileName);
        try {
            boolean c = f.createNewFile();
            if(!c){
                return c;
            }
            FileOutputStream fOut = new FileOutputStream(f);
            b.compress(format, quality, fOut);
            fOut.flush();
            fOut.close();

            EventBus.getDefault().post(new CameraEvent(f.getAbsolutePath()));
            return true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    /**
     * 创建Bitmap
     *
     * @param b     Bitmap
     * @param width 宽度
     * @param angle 缩放
     * @return Bitmap
     */
    public static Bitmap createBitmap(Bitmap b, float width, float angle) {
        // 计算缩放比例
        if (b != null) {
            if (b.getWidth() != width) {
                float scale = width / b.getWidth();

                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                matrix.postRotate(angle);

                Bitmap bNew = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), matrix, true);
                return bNew;
            } else {
                return b;
            }
        }
        return null;
    }

    /**
     * 流转换为Bitmap
     *
     * @param b byte[]
     * @return Bitmap
     */
    public static Bitmap bytes2Bimap(byte[] b) {
        if (b != null) {
            if (b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
        }
        return null;
    }

    /**
     * 缩放
     *
     * @param bitmap bitmap
     * @param scale  缩放比例
     * @return Bitmap
     */
    public static Bitmap scale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap outBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) (bitmap.getWidth()), (int) (bitmap.getHeight()), matrix, true);
        bitmap.recycle();
        return outBitmap;
    }


    /**
     * 圆角图片
     *
     * @param context Context
     * @param resId   资源文件
     * @param ratio   圆角占图片的百分比
     * @return Bitmap
     */

    public static Bitmap getRoundCornerBitmapByRatio(Context context, int resId, float ratio) {
        Bitmap output = BitmapFactory.decodeResource(context.getResources(), resId);
        return getRoundCornerBitmapByRatio(output, ratio);
    }

    /**
     * 圆角图片
     *
     * @param bm    Bitmap
     * @param ratio 圆角占图片的百分比
     * @return Bitmap
     */
    public static Bitmap getRoundCornerBitmapByRatio(Bitmap bm, float ratio) {
        try {
            if (bm != null) {
                float roundPx = bm.getWidth() * ratio;
                Bitmap output = Bitmap.createBitmap(bm.getWidth(),
                        bm.getHeight(), Config.ARGB_8888);
                if (output == null) {
                    return null;
                }
                Canvas canvas = new Canvas(output);

                final int _COLOR = 0xff424242;
                final Paint _PAINT = new Paint();
                final Rect _RECT = new Rect(0, 0, bm.getWidth(), bm.getHeight());
                final RectF _RECTF = new RectF(_RECT);

                _PAINT.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);
                _PAINT.setColor(_COLOR);
                canvas.drawRoundRect(_RECTF, roundPx, roundPx, _PAINT);

                _PAINT.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
                canvas.drawBitmap(bm, _RECT, _RECT, _PAINT);

                return output;
            }
        } catch (Throwable e) {
        }

        return null;
    }

    /**
     * 创建切割的图片
     *
     * @param filepath 图片路径
     * @return Bitmap
     */
    public static Bitmap createCaptureBitmap(String filepath) {
        int scale = 1;
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filepath, options);
            if (options.outWidth > IMAGE_MAX_SIZE
                    || options.outHeight > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.round(Math
                        .log(IMAGE_MAX_SIZE
                                / (double) Math.max(options.outHeight,
                                options.outWidth))
                        / Math.log(0.5)));
            }
            Options opt = new Options();
            opt.inSampleSize = scale;
            return BitmapFactory.decodeFile(filepath, opt);
        } catch (OutOfMemoryError e) {
            Log.e("memory",
                    "createCaptureBitmap out of memory");
            scale = scale * 2;
            try {
                Options opt = new Options();
                opt.inSampleSize = scale;
                return BitmapFactory.decodeFile(filepath, opt);
            } catch (OutOfMemoryError oe) {
                Log.e("memory",
                        "createCaptureBitmap out of memory second");
                return null;
            }
        }
    }


    /**
     * 处理三星手机拍出来的图片会旋转的问题，同时刷新相册
     *
     * @param imgPath 图片路径
     */
    public static void checkImageOrientation(String imgPath) {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        int rotateDegress = 0;
        try {
            ExifInterface exif = new ExifInterface(imgPath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotateDegress = ROTATE_DEGRESS_90;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotateDegress = ROTATE_DEGRESS_180;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotateDegress = ROTATE_DEGRESS_270;
            } else {
                return;
            }

            Options opt = new Options();
            opt.inJustDecodeBounds = true;
            Bitmap bit = BitmapFactory.decodeFile(imgPath, opt);
            int width = opt.outWidth;
            int height = opt.outHeight;
            if (width > height && width > BYTE_1024) {
                opt.inSampleSize = width / BYTE_1024 + 1;
            } else if (height > BYTE_1024) {
                opt.inSampleSize = height / BYTE_1024 + 1;
            }
            opt.inJustDecodeBounds = false;
            bit = BitmapFactory.decodeFile(imgPath, opt).copy(Config.ARGB_8888, true);

            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, 1.0f);
            matrix.postRotate(rotateDegress);
            Bitmap bitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                    bit.getHeight(), matrix, false);
            bit.recycle();
            bit = null;
            bit = bitmap;

            baos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
            //			String newImagePath = imgPath + "1.jpg";
            File file = new File(imgPath);
            if (!file.exists()) {
                boolean c = file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(baos.toByteArray());
            bos.flush();

            bit.recycle();
            bit = null;

            exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_NORMAL));
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 旋转图片
     *
     * @param imgPath       图片路径
     * @param rotateDegress 旋转角度，顺时针
     * @return 旋转后的图片路径
     */
    public static String rotateImage(String imgPath, int rotateDegress) {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        if (rotateDegress % ROTATE_DEGRESS_360 == 0
                || rotateDegress % ROTATE_DEGRESS_90 != 0
                ) {
            return imgPath;
        }

        if (TextUtils.isEmpty(imgPath)) {
            return imgPath;
        }

        int index = imgPath.lastIndexOf(".");
        if (index <= 0) {
            index = imgPath.length();
        }
        String outImgPath = imgPath.substring(0, index) + "_" + rotateDegress + ".jpg";
        try {

            Options opt = new Options();
            opt.inJustDecodeBounds = true;

            Bitmap bit = BitmapFactory.decodeFile(imgPath, opt);
            int width = opt.outWidth;
            int height = opt.outHeight;
            if (width > height && width > BYTE_1024) {
                opt.inSampleSize = width / BYTE_1024 + 1;
            } else if (height > BYTE_1024) {
                opt.inSampleSize = height / BYTE_1024 + 1;
            }
            opt.inJustDecodeBounds = false;
            bit = BitmapFactory.decodeFile(imgPath, opt).copy(Config.ARGB_8888, true);

            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, 1.0f);
            matrix.postRotate(rotateDegress);
            Bitmap bitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                    bit.getHeight(), matrix, false);
            bit.recycle();
            bit = null;
            bit = bitmap;

            baos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, QUALITY, baos);
            //			String newImagePath = imgPath + "1.jpg";
            File file = new File(outImgPath);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(baos.toByteArray());
            bos.flush();

            bit.recycle();
            bit = null;

            return outImgPath;
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage());
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imgPath;
    }

    /**
     * 旋转图片
     *
     * @param bm            图片
     * @param rotateDegress 旋转度数
     * @return 旋转后的图片
     */
    public static Bitmap rotateImage(Bitmap bm, int rotateDegress) {
        Matrix m = new Matrix();
        m.setRotate(rotateDegress, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (rotateDegress == ROTATE_DEGRESS_90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] _VALUES = new float[VALUES_9];
        m.getValues(_VALUES);

        float x1 = _VALUES[Matrix.MTRANS_X];
        float y1 = _VALUES[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }

    /**
     * 从中心点   按正方形裁切图片  边长为小边
     *
     * @param bitmap 图片
     * @return Bitmap
     */
    public static Bitmap cropImage(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int centerX = w / 2;
        int centerY = h / 2;

        //下面这句是关键
        Bitmap outBitmap = Bitmap.createBitmap(bitmap, centerX - wh / 2, centerY - wh / 2, wh, wh, null, false);
        bitmap.recycle();
        return outBitmap;
    }

    /**
     * 按指定区域解码
     *
     * @param is      流
     * @param rect    Rect
     * @param options Options
     * @return Bitmap
     * @throws Exception Exception
     */
    public static Bitmap decode(InputStream is, Rect rect, Options options) throws Exception {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > VALUES_9) {
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
            bitmap = decoder.decodeRegion(rect, options);
        } else {
            Bitmap temp = BitmapFactory.decodeStream(is, null, options);
            bitmap = Bitmap.createBitmap(temp, rect.left, rect.top, rect.width(), rect.height());
            if (temp != null && !temp.isRecycled()) {
                temp.recycle();
            }
        }

        return bitmap;
    }
}
