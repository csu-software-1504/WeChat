/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * 打印工具类
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class LogUtil {
    /**
     * log level verbose
     */
    public static final int LOG_LEVEL_VERBOSE = 1;

    /**
     * log level debug
     */
    public static final int LOG_LEVEL_DEBUG = 2;

    /**
     * log level info
     */
    public static final int LOG_LEVEL_INFO = 3;

    /**
     * log level warn
     */
    public static final int LOG_LEVEL_WARN = 4;

    /**
     * log level error
     */
    public static final int LOG_LEVEL_ERROR = 5;

    private static int sLevel = LOG_LEVEL_DEBUG;

    private static final String TAG = "huangjishun";

    private LogUtil() {
    }

    /**
     * 设置日志打印级别
     *
     * @param level 打印级别
     */
    public static synchronized void setLogLevel(int level) {
        LogUtil.sLevel = level;
    }

    /**
     * verbose类型的日志
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void v(String tag, String msg) {
        if (sLevel <= LOG_LEVEL_VERBOSE) {
            Log.v(TAG, "<" + tag + ">,  " + msg);
        }
    }

    /**
     * verbose类型的日志
     *
     * @param tag 标签
     * @param format 格式字符串
     * @param args 参数
     */
    public static void v(String tag, String format, Object... args) {
        if (sLevel <= LOG_LEVEL_VERBOSE) {
            if (TextUtils.isEmpty(format)) {
                e(TAG, "invalid format");
                return;
            }
            String formatted = String.format(Locale.ROOT, format, args);
            Log.v(TAG, "<" + tag + ">,  " + formatted);
        }
    }

    /**
     * info类型的日志
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void i(String tag, String msg) {
        if (sLevel <= LOG_LEVEL_INFO) {
            Log.i(TAG, "<" + tag + ">,  " + msg);
        }
    }

    /**
     * info类型的日志
     *
     * @param tag 标签
     * @param format 格式字符串
     * @param args 参数
     */
    public static void i(String tag, String format, Object... args) {
        if (sLevel <= LOG_LEVEL_INFO) {
            if (TextUtils.isEmpty(format)) {
                e(TAG, "invalid format");
                return;
            }
            String formatted = String.format(Locale.ROOT, format, args);
            Log.i(TAG, "<" + tag + ">,  " + formatted);
        }
    }

    /**
     * debug类型的日志
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void d(String tag, String msg) {
        if (sLevel <= LOG_LEVEL_DEBUG) {
            Log.d(TAG, "<" + tag + ">,  " + msg);
        }
    }

    /**
     * debug类型的日志
     *
     * @param tag 标签
     * @param format 格式字符串
     * @param args 参数
     */
    public static void d(String tag, String format, Object... args) {
        if (sLevel <= LOG_LEVEL_DEBUG) {
            if (TextUtils.isEmpty(format)) {
                e(TAG, "invalid format");
                return;
            }
            String formatted = String.format(Locale.ROOT, format, args);
            Log.d(TAG, "<" + tag + ">,  " + formatted);
        }
    }

    /**
     * warning类型的日志
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void w(String tag, String msg) {
        if (sLevel <= LOG_LEVEL_WARN) {
            Log.w(TAG, "<" + tag + ">,  " + msg);
        }
    }

    /**
     * warning类型的日志
     *
     * @param tag 标签
     * @param format 格式字符串
     * @param args 参数
     */
    public static void w(String tag, String format, Object... args) {
        if (sLevel <= LOG_LEVEL_WARN) {
            if (TextUtils.isEmpty(format)) {
                e(TAG, "invalid format");
                return;
            }
            String formatted = String.format(Locale.ROOT, format, args);
            Log.w(TAG, "<" + tag + ">,  " + formatted);
        }
    }

    /**
     * error类型的日志
     *
     * @param tag 标签
     * @param msg 日志内容
     */
    public static void e(String tag, String msg) {
        if (sLevel <= LOG_LEVEL_ERROR) {
            Log.e(TAG, "<" + tag + ">,  " + msg);
        }
    }

    /**
     * error类型的日志
     *
     * @param tag 标签
     * @param format 格式字符串
     * @param args 参数
     */
    public static void e(String tag, String format, Object... args) {
        if (sLevel <= LOG_LEVEL_ERROR) {
            if (TextUtils.isEmpty(format)) {
                e(TAG, "invalid format");
                return;
            }
            String formatted = String.format(Locale.ROOT, format, args);
            Log.e(TAG, "<" + tag + ">,  " + formatted);
        }
    }
}
