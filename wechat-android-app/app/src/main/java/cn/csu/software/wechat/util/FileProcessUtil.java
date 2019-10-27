/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作工具类
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FileProcessUtil {
    private static final String TAG = FileProcessUtil.class.getSimpleName();

    private FileProcessUtil() {
    }

    /**
     * 获取assets目录下文件的字符串
     *
     * @param filePath 文件名
     * @return 返回canonicalPath处理后的路径
     */
    private static String getCanonicalPath(String filePath) {
        String canonicalPath = null;
        if (filePath == null) {
            return canonicalPath;
        }
        File file = new File(filePath);
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            LogUtil.e(TAG, "getCanonicalPath IOException");
        }
        return canonicalPath;
    }

    /**
     * 获取assets目录下图片的bitmap
     *
     * @param con      上下文
     * @param fileName 文件名
     * @return 返回Bitmap数据
     */
    public static Bitmap getAssetsFileBitmap(Context con, String fileName) {
        Bitmap bitmap = null;
        if ((fileName == null) || (con == null)) {
            return bitmap;
        }
        InputStream is = null;
        try {
            is = con.getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            LogUtil.e(TAG, "get bitmap file error! %s", e);
            LogUtil.e(TAG, "get bitmap file error! %s", fileName);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, "get bitmap file error! %s", fileName);
                }
            }
        }
        return bitmap;
    }

    /**
     * 获取图片的bitmap
     *
     * @param con      上下文
     * @param filePath 文件名
     * @return 返回Bitmap数据
     */
    public static Bitmap getBitmap(Context con, String filePath) {
        Bitmap bitmap = null;
        if ((filePath == null) || (con == null)) {
            return bitmap;
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            LogUtil.e(TAG, "get bitmap file error! %s", filePath);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtil.e(TAG, "get bitmap file error! %s", filePath);
                }
            }
        }
        return bitmap;
    }

    /**
     * 创建路径
     *
     * @param path 文件夹路径
     * @return 存在或创建成功返回true，否则返回false
     */
    public static boolean createDirectory(String path) {
        if (path == null) {
            return false;
        }
        String fileCanonicalPath = getCanonicalPath(path);
        if (fileCanonicalPath == null) {
            return false;
        }
        boolean isRet = true;
        String fileDirectory = fileCanonicalPath;
        if (!fileCanonicalPath.endsWith(File.separator)) {
            fileDirectory = fileCanonicalPath + File.separator;
        }
        File file = new File(fileDirectory);
        if (!file.exists()) {
            isRet = file.mkdir();
        }
        return isRet;
    }

    /**
     * 判断路径是否存在
     *
     * @param filePath 文件夹路径
     * @return 存在返回true，否则返回false
     */
    public static boolean isExist(String filePath) {
        if (filePath == null) {
            return false;
        }
        String fileCanonicalPath = getCanonicalPath(filePath);
        if (fileCanonicalPath == null) {
            return false;
        }
        String fileDirectory = fileCanonicalPath;
        if (!fileCanonicalPath.endsWith(File.separator)) {
            fileDirectory = fileCanonicalPath + File.separator;
        }
        File file = new File(fileDirectory);
        if (file.exists()) {
            return true;
        }
        return false;
    }
}
