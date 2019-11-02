/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.server;

import cn.csu.software.wechat.entity.SocketData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Socket Thread
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-19
 */
public class SocketThread implements Runnable, ReceiveMessageThread.MessageListener {
    private Logger logger = LogManager.getLogger(SocketThread.class.getSimpleName());

    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    public SocketThread(Socket socket) throws IOException {
        this.socket = socket;
        objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
    }

    private void sendMessage(SocketData socketData) throws IOException {
        objectOutputStream.writeObject(socketData);
    }

    @Override
    public void onMessageListener(SocketData socketData) {
        logger.info("get message from [" + socket.getInetAddress() + "]: " + socketData.toString());
        try {
            socketData.setTextMessage("receive the message from [" + socket.getInetAddress() + "] "
                    + socketData.getTextMessage());
            sendMessage(socketData);
        } catch (IOException e) {
            logger.error("send message error");
        }
    }

    @Override
    public void run() {
        logger.info("successfully connected to server: [" + socket.getInetAddress() + "]");
        ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(this.socket);
        receiveMessageThread.setMessageListener(this);
        Thread thread = new Thread(receiveMessageThread);
        thread.start();
    }
}