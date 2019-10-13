/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * socket接收消息的线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class SocketThread implements ReceiveMessageThread.MessageListener, Runnable {
    private Logger logger = LogManager.getLogger(SocketThread.class.getSimpleName());

    private static final int WRITE_LENGTH = 8;

    private Socket socket;

    private OutputStream outputStream;

    /**
     * 有参构造函数
     *
     * @param socket 非空socket
     */
    public SocketThread( Socket socket) {
        this.socket = socket;
    }

    private void forward(String message) throws IOException {
        outputStream = this.socket.getOutputStream();
        sendMessage("the server receive the message: " + message);
    }

    private void sendMessage(String message) throws IOException {
        byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
        int messageLength = messageByte.length;
        outputStream.write(messageLength >> WRITE_LENGTH);
        outputStream.write(messageLength);
        outputStream.write(messageByte);
    }

    @Override
    public void onMessageListener(String message) {
        logger.info("get message from [" + socket.getInetAddress() + "]: " + message);
        try {
            forward(message);
        } catch (IOException e) {
            logger.warn("forward message error");
        }
    }

    @Override
    public void run() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        logger.info("path " + path);
        logger.info("successfully connect with " + socket.getInetAddress());
        ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(socket);
        receiveMessageThread.setMessageListener(this);
        Thread thread = new Thread(receiveMessageThread);
        thread.start();
    }
}