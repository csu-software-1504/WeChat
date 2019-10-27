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
 *
 *  receiverType:  消息接收者类型：0 好友， 1 群组
 *  messageType:  1 文本消息
 *                2 文本+图片消息
 *                3 图片
 *                4 视频
 *                5 其他类型
 *  sendTime：  消息发送时间  取毫秒数
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -1143699039418085162L;

    private int senderUid;

    private int receiverType;

    private int receiverId;

    private int messageType;

    private String content;

    private long sendTime;

    public int getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(int senderUid) {
        this.senderUid = senderUid;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
