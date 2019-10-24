/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.database.content;

import android.net.Uri;
import android.provider.BaseColumns;

import cn.csu.software.wechat.database.helper.ChatMessageDatabaseHelper;

/**
 * 聊天消息 content
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatMessageContent implements BaseColumns {
    // TODO: 19-10-20 后续数据库的创建，列表数据放在这里，注释后续添加
    public static final String AUTHORITIES = "cn.csu.software.wechat.database.provider.ChatMessageProvider";

    public static final String TABLE_NAME = ChatMessageDatabaseHelper.TABLE_NAME;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/chat_message");

    public static final String SENDER_NAME = "sender_name";

    public static final String RECEIVER_NAME = "receiver_name";

    public static final String AVATAR_PATH = "avatar_path";

    public static final String CHAT_MESSAGE_TYPE = "chat_message_type";

    public static final String SEND_TIME = "send_time";

    public static final String CHAT_MESSAGE_TEXT = "chat_message_text";

    public static final String CHAT_MESSAGE_PHOTO_PATH = "chat_message_photo_path";

    public static final String CHAT_MESSAGE_VIDEO_PATH = "chat_message_video_path";

    // 默认的排序方法
    public static final String DEFAULT_SORT_ORDER = "_id desc";
}
