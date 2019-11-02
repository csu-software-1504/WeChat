/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.entity;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 5552472348858194737L;

    private int account;

    private String email;

    private String username;

    private String remark;

    private String avatarPath;

    private int sex;

    private String lastMessage;

    private long lastMessageSendTime;

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getLastMessageSendTime() {
        return lastMessageSendTime;
    }

    public void setLastMessageSendTime(long lastMessageSendTime) {
        this.lastMessageSendTime = lastMessageSendTime;
    }
}
