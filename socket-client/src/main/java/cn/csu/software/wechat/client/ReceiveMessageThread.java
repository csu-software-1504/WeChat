/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.client;

import cn.csu.software.wechat.entity.SocketData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * socket接收消息的线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class ReceiveMessageThread implements Runnable {
    private Logger logger = LogManager.getLogger(ReceiveMessageThread.class.getSimpleName());

    private Socket socket;

    private ObjectInputStream objectInputStream;

    private MessageListener messageListener;

    ReceiveMessageThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Object object = objectInputStream.readObject();
                if (object instanceof SocketData) {
                    SocketData socketData = (SocketData) object;
                    messageListener.onMessageListener(socketData);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.warn("get input stream error");
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                    socket.close();
                }
            } catch (IOException e) {
                logger.error("input stream close error");
            }
        }
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * 接收到消息的监听接口
     *
     * @author huangjishun 874904407@qq.com
     * @since 2019-10-12
     */
    public interface MessageListener {
        /**
         * 接收到消息的回调函数
         *
         * @param socketData 消息内容
         */
        void onMessageListener(SocketData socketData);
    }
}
