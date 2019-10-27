package cn.csu.software.entity;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import java.sql.Date;

/**
 * @title : cn.csu.software.common.entity
 * @author : oranges
 * @date : 2019/10/19 20:13
 * @description :
 *  adminInGroup:  用户在群组中的身份: 0 普通用户
 *                                   1 管理员
 *                                   2 群主
 */
public class UserDetail extends User {

    private static final long serialVersionUID = -3663176584206242734L;

    private int adminInGroup;

    private Date addedTime;

    public int getAdminInGroup() {
        return adminInGroup;
    }

    public void setAdminInGroup(int adminInGroup) {
        this.adminInGroup = adminInGroup;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }
}
