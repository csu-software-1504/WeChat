package cn.csu.software.entity.chatmessage;/**
 * @description
 * @author oranges 864160262@qq.com
 * @date 2019/10/19
 */

import java.io.Serializable;
import java.sql.Date;

/**
 * @title : cn.csu.software.common.entity.chatmessage
 * @author : oranges
 * @date : 2019/10/19 20:28
 * @description : 
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 2753805299663566414L;

    private int uid;

    private String content;

    private Date sendTime;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
