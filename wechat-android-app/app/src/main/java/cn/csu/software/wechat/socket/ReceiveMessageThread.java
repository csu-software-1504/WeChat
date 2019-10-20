/*
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 接收消息线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class ReceiveMessageThread implements Runnable {
    private static final String CODING = "UTF-8";

    private Socket socket;

    private InputStream inputStream;

    private MessageListener messageListener;

    public ReceiveMessageThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            while (true) {
                int first = inputStream.read();
                if (first == -1) {
                    // 如果是-1，说明输入流已经被关闭了，也就不需要继续监听了
                    this.socket.close();
                    break;
                }
                int second = this.inputStream.read();
                int msgLength = (first<<8) + second;
                byte[] readBuffer = new byte[msgLength];
                if (inputStream.read(readBuffer) != -1) {
                    String message = new String(readBuffer, CODING);
                    messageListener.onMessageListener(message);
                }
            }
        } catch (IOException e) {
            System.out.println("close");
        }  finally {
            try {
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public interface MessageListener {
        void onMessageListener(String msg);
    }
}
