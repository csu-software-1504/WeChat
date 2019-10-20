/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.constant;

/**
 * 项目 constant data
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ConstantData {
    /**
     * 保存图片资源的目录
     */
    public static final String PHOTO_DIRECTORY = "photo";

    /**
     * 保存图片资源的目录
     */
    public static final String AVATAR_DIRECTORY = "avatar";

    /**
     * 全局查询条件
     */
    public static final String GLOBAL_QUERY_CONDITION = "1=1";

    /**
     * 数据库第一次创建
     */
    public static final int DATABASE_CREATE_VISION_FIRST_TIME = 1;

    /**
     * 数据库第二次创建
     */
    public static final int DATABASE_CREATE_VISION_SECOND_TIME = 2;

    /**
     * 我的界面头像尺寸
     */
    public static final int AVATAR_SIZE_MINE = 200;

    /**
     * 消息界面头像尺寸
     */
    public static final int AVATAR_SIZE_MESSAGE = 160;

    /**
     * 好友界面头像尺寸
     */
    public static final int AVATAR_SIZE_FRIEND = 120;

    /**
     * 头像圆角尺寸
     */
    public static final int AVATAR_CIRCLE_SIZE = 6;

    /**
     * extra receiver name
     */
    public static final String EXTRA_RECEIVER_NAME = "receiverName";

    /**
     * extra friend name
     */
    public static final String EXTRA_FRIEND_NAME = "friendName";

    /**
     * extra avatar name
     */
    public static final String EXTRA_AVATAR_PATH = "avatarPath";

    /**
     * bundle key message
     */
    public static final String BUNDLE_KEY_MESSAGE = "message";

    /**
     * package name
     */
    public static final String PACKAGE_NAME = "cn.csu.software.wechat";

    /**
     * PersonalInfoActivity class name
     */
    public static final String ACTIVITY_CLASS_NAME_PERSONAL_INFO = "cn.csu.software.wechat.activity.PersonalInfoActivity";

    /**
     * ChatActivity class name
     */
    public static final String ACTIVITY_CLASS_NAME_CHAT = "cn.csu.software.wechat.activity.ChatActivity";


    /**
     * 示例初始化好友列表
     */
    public static final String[] EXAMPLE_FRIEND_NAME = {"黄绩顺", "刘德华","郭富城", "黎明", "张学友", "周杰伦", "许嵩",
            "胡歌", "林俊杰", "成龙", "李连杰", "李小龙", "赵薇", "霍建华"};

    /**
     * 示例初始化头像
     */
    public static final String[] EXAMPLE_AVATAR_NAME = {"me", "liudehua","guofucheng", "liming", "zhangxueyou",
            "zhoujielun", "xusong", "huge", "linjunjie", "chenglong", "lilianjie", "lixiaolong",
            "zhaowei", "huojianhua"};

    /**
     * extra avatar name
     */
    public static final String MY_AVATAR_NAME = "me";

    /**
     * 示例初始化头像后缀
     */
    public static final String EXAMPLE_EXTENSION_NAME = ".jpg";

    /**
     * 示例初始化最后一条消息
     */
    public static final String EXAMPLE_LAST_MESSAGE_HEADER = "hello, 我是";
}
