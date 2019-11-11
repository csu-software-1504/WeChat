/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.socket;

import cn.csu.software.wechat.entity.SocketData;
import cn.csu.software.wechat.util.LogUtil;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 客户端线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class SocketClient implements Runnable, ReceiveMessageThread.MessageListener {
    private static final String TAG = SocketClient.class.getSimpleName();

    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    private SocketClientListener socketClientListener;

    private static final String SERVER_HOST = "129.211.71.65";

//  private static final String SERVER_HOST = "192.168.10.200";

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
            objectOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(SocketData socketData) throws IOException {
        objectOutputStream.writeObject(socketData);
    }

    @Override
    public void onMessageListener(SocketData socketData) {
        socketClientListener.onSocketClientListener(socketData);
    }

    @Override
    public void run() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            LogUtil.e(TAG, "start socket error:  %s", e);
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
        void onSocketClientListener(SocketData socketData);
    }
}