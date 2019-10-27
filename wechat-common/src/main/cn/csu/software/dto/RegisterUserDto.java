package cn.csu.software.dto;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/27
 */

import java.io.Serializable;

/**
 * @title : cn.csu.software.dto
 * @author : oranges
 * @date : 2019/10/27 12:44
 * @description : 
 */
public class RegisterUserDto implements Serializable {

    private static final long serialVersionUID = 356079853022888194L;

    private String email;

    private String password;

    private String username;

    private String ip;

    private int sex;

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
}
