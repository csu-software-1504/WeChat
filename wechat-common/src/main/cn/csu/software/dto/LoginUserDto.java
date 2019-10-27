package cn.csu.software.dto;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/27
 */

import java.io.Serializable;

/**
 * @title : cn.csu.software.dto
 * @author : oranges
 * @date : 2019/10/27 1:43
 * @description : 
 */
public class LoginUserDto implements Serializable {

    private static final long serialVersionUID = 9083862926156245884L;

    private String email;

    private String password;

    private String ip;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
