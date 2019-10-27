/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.bean;

/**
 * 好友信息
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class FriendInfo {
    private String friendName;

    private String friendAvatarPath;

    private int group;

    private String account;

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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
