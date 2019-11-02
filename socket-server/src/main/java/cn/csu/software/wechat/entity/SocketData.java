/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.entity;

import java.io.Serializable;

/**
 * socket 传输数据
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class SocketData implements Serializable {
    private static final long serialVersionUID = 2360664973707770902L;

    private int receiverAccount;

    private int senderAccount;

    private int messageType;

    private String textMessage;

    private byte[] bytes;

    public SocketData() {
        bytes = new byte[0];
    }

    public SocketData(int receiverAccount, int senderAccount, int messageType, String textMessage, byte[] bytes) {
        this.receiverAccount = receiverAccount;
        this.senderAccount = senderAccount;
        this.messageType = messageType;
        this.textMessage = textMessage;
        this.bytes = bytes;
    }

    public int getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(int receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public int getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(int senderAccount) {
        this.senderAccount = senderAccount;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String toString() {
        return "receiver account: " + getReceiverAccount() + "\n" + "sender account: " + getSenderAccount() + "\n"
                + "message type: " + getMessageType() + "\n" + "text message: " + getTextMessage() + "\n"
                + "bytes length: " + getBytes().length;
    }
}
