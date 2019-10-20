/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.bean;

/**
 * 好友消息
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendChatInfo {
    private String friendName;

    private String friendAvatarPath;

    private String friendLastMessage;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendAvatarPath() {
        return friendAvatarPath;
    }

    public void setFriendAvatarPath(String friendAvatarPath) {
        this.friendAvatarPath = friendAvatarPath;
    }

    public String getFriendLastMessage() {
        return friendLastMessage;
    }

    public void setFriendLastMessage(String friendMessage) {
        this.friendLastMessage = friendMessage;
    }
}
