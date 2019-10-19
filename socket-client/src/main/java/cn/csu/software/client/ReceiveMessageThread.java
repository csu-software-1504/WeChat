/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.client;

import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * socket接收消息的线程
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class ReceiveMessageThread implements Runnable {
    private Logger logger = LogManager.getLogger(ReceiveMessageThread.class.getSimpleName());

    private static final int INPUT_STREAM_OFF = -1;

    private Socket socket;

    private MessageListener messageListener;

    /**
     * 有参构造函数
     *
     * @param socket 非空socket
     */
    ReceiveMessageThread(@NotNull Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true) {
                int firstByte = inputStream.read();
                if (firstByte == INPUT_STREAM_OFF) {
                    socket.close();
                    break;
                }
                int secondByte = inputStream.read();
                int messageLength = (firstByte << 8) + secondByte;
                byte[] readBuffer = new byte[messageLength];
                if (inputStream.read(readBuffer) != -1) {
                    String message = new String(readBuffer, StandardCharsets.UTF_8);
                    if (messageListener != null) {
                        messageListener.onMessageListener(message);
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("get input stream error");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                socket.close();
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
    interface MessageListener {
        /**
         * 接收到消息的回调函数
         *
         * @param message 消息内容
         */
        void onMessageListener(String message);
    }
}
