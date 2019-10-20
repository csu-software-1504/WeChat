package cn.csu.software.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * @description User实体类
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

public class User implements Serializable {

    private static final long serialVersionUID = -4615862659161052332L;

    private int uid;

    private String tel;

    private String password;

    private String username;

    private String pic;

    private int sex;

    private int status;

    private Date createDate;

//    private List<UserDetail> friends;
//
//    private List<Group> groups;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

//    public List<UserDetail> getFriends() {
//        return friends;
//    }
//
//    public void setFriends(List<UserDetail> friends) {
//        this.friends = friends;
//    }
//
//    public List<Group> getGroups() {
//        return groups;
//    }
//
//    public void setGroups(List<Group> groups) {
//        this.groups = groups;
//    }
}
