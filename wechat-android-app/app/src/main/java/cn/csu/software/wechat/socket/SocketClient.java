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
 * 客户端线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class SocketClient implements Runnable, ReceiveMessageThread.MessageListener {
    private static final String TAG = SocketClient.class.getSimpleName();

    private Socket socket;

    private OutputStream outputStream;

    private SocketClientListener socketClientListener;

    private static final String SERVER_HOST = "129.211.71.65";

    private static final int SERVER_PORT = 7110;

    private static SocketClient socketClient;

    public static SocketClient getInstance() {
        if (socketClient == null) {
            socketClient = new SocketClient();
        }
        return socketClient;
    }

    public void close() {
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) throws IOException {
        byte[] msgBytes = message.getBytes(StandardCharsets.UTF_8);
        int length = msgBytes.length;
        LogUtil.i(TAG, "message: %s", message);
        outputStream.write(length >> 8);
        outputStream.write(length);
        outputStream.write(msgBytes);
    }

    @Override
    public void onMessageListener(String msg) {
        socketClientListener.onSocketClientListener(msg);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            LogUtil.e(TAG, "start socket error");
        }
        LogUtil.i(TAG, "successful connected to server: %s", socket.getInetAddress());
        ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(this.socket);
        receiveMessageThread.setMessageListener(this);
        Thread thread = new Thread(receiveMessageThread);
        thread.start();

    }

    public void setSocketClientListener(SocketClientListener socketClientListener) {
        this.socketClientListener = socketClientListener;
    }

    public interface SocketClientListener {
        void onSocketClientListener(String msg);
    }
}