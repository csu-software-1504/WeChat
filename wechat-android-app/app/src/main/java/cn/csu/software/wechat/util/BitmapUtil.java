/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片操作工具类
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    private BitmapUtil() {
    }

    /**
     * 保存图片到指定路径
     *
     * @param imagePath 图片路径
     * @param bitmap 图片
     */
    public static void saveImg(String imagePath, Bitmap bitmap) {
        File file = new File(imagePath);
        FileOutputStream out = null;
        try {
             out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
        } catch (IOException e) {
            LogUtil.e(TAG, "save image error");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                LogUtil.e(TAG, "output stream close error");
            }
        }
    }

    /**
     * 缩放图片比例
     *
     * @param bitmap 图片
     * @param newWidth 图片缩放后的宽度
     * @param newHeight 图片缩放后的高度
     * @return Bitmap 缩放后的图片
     */
    public static Bitmap zoomImg(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
}