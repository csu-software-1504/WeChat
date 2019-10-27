/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.socket;

import cn.csu.software.wechat.util.LogUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * 服务的线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class SocketThread implements ReceiveMessageThread.MessageListener, Runnable {
    private static final String TAG = SocketThread.class.getSimpleName();

    private static final int WRITE_LENGTH = 8;

    private Socket socket;

    private OutputStream outputStream;

    SocketThread(Socket socket) {
        this.socket = socket;
    }

    private void waitAndSend(String msg) throws IOException {
        this.outputStream = this.socket.getOutputStream();
        this.sendMessage("get the message: " + msg);
    }

    private void sendMessage(String message) throws IOException {
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);
        int length = msgBytes.length;
        outputStream.write(length >> WRITE_LENGTH);
        outputStream.write(length);
        outputStream.write(msgBytes);
    }

    @Override
    public void onMessageListener(String msg) {
        try {
            LogUtil.i(TAG, "message from [%s]: %s", socket.getInetAddress(), msg);
            waitAndSend(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        LogUtil.i(TAG, "successful connected with ", socket.getInetAddress());
        ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(this.socket);
        receiveMessageThread.setMessageListener(this);
        Thread thread = new Thread(receiveMessageThread);
        thread.start();
    }
}