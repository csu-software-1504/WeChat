/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.database.content;

import android.net.Uri;
import android.provider.BaseColumns;

import cn.csu.software.wechat.database.helper.FriendChatInfoDatabaseHelper;

/**
 * 好友消息 content
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendChatInfoContent implements BaseColumns {
    // TODO: 19-10-20 后续数据库的创建，列表数据放在这里，注释后续添加
    public static final String AUTHORITIES = "cn.csu.software.wechat.database.provider.FriendChatInfoProvider";

    public static final String TABLE_NAME = FriendChatInfoDatabaseHelper.TABLE_NAME;

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/friend_chat_info");

    public static final String ACCOUNT = "account";

    public static final String USERNAME = "username";

    public static final String AVATAR_PATH = "avatar_path";

    public static final String LAST_MESSAGE = "last_message";

    public static final String LAST_MESSAGE_SEND_TIME = "last_message_send_time";

    public static final String SORT_ORDER_DESC = "desc";
}
