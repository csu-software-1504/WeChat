/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.bean;

/**
 * 聊天消息
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class ChatMessage {
    /**
     * 文本消息类型
     */
    public static final int TEXT_TYPE = 0;

    /**
     * 图片消息类型
     */
    public static final int PHOTO_TYPE = 1;

    /**
     * 视频消息类型
     */
    public static final int VIDEO_TYPE = 2;

    /**
     * 文本图片综合消息类型
     */
    public static final int TEXT_AND_PHOTO_TYPE = 3;

    private String senderName;

    private String receiverName;

    private String avatarPath;

    private int chatMessageType;

    private String sendTime;

    private String chatMessageText;

    private String chatMessagePhotoPath;

    private String chatMessageVideoPath;

    public String getChatMessageText() {
        return chatMessageText;
    }

    public void setChatMessageText(String chatMessageText) {
        this.chatMessageText = chatMessageText;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getChatMessageType() {
        return chatMessageType;
    }

    public void setChatMessageType(int chatMessageType) {
        this.chatMessageType = chatMessageType;
    }

    public String getChatMessagePhotoPath() {
        return chatMessagePhotoPath;
    }

    public void setChatMessagePhotoPath(String chatMessagePhotoPath) {
        this.chatMessagePhotoPath = chatMessagePhotoPath;
    }

    public String getChatMessageVideoPath() {
        return chatMessageVideoPath;
    }

    public void setChatMessageVideoPath(String chatMessageVideoPath) {
        this.chatMessageVideoPath = chatMessageVideoPath;
    }
}
