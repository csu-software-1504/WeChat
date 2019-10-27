package cn.csu.software.entity;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @title : cn.csu.software.common.entity
 * @author : oranges
 * @date : 2019/10/19 20:10
 * @description : 
 */
public class Group implements Serializable {

    private static final long serialVersionUID = 1468194116386741575L;

    private int groupId;

    private String groupName;

    private int groupMasterUid;

    private String desc;

    private Date createTime;

    private List<UserDetail> groupMembers;

    public List<UserDetail> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<UserDetail> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupMasterUid() {
        return groupMasterUid;
    }

    public void setGroupMasterUid(int groupMasterUid) {
        this.groupMasterUid = groupMasterUid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
