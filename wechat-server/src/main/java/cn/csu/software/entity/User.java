package cn.csu.software.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @title : cn.csu.software.common.entity
 * @author : oranges
 * @date : 2019/10/19 20:13
 * @description :
 */

public class User implements Serializable {

    private static final long serialVersionUID = -6137415927846397712L;

    private int uid;

    private String email;

    private String password;

    private String username;

    private String ip;

    private String avatar;

    private int sex;

    private int status;

    private Date createDate;

    private List<UserDetail> friends;

    private List<Group> groups;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<UserDetail> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDetail> friends) {
        this.friends = friends;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
