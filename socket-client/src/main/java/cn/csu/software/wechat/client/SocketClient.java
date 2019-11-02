/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.wechat.client;

import cn.csu.software.wechat.entity.SocketData;
import com.sun.istack.internal.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket server入口
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class SocketClient implements ReceiveMessageThread.MessageListener {
    private Logger logger = LogManager.getLogger(ReceiveMessageThread.class.getSimpleName());

    private String socketHost;

    private int socketPort;

    private Socket socket;

    /**
     * 有参构造函数
     *
     * @param socketHost socket server地址
     * @param socketPort socket端口号
     */
    public SocketClient(@NotNull String socketHost, @NotNull int socketPort) {
        this.socketHost = socketHost;
        this.socketPort = socketPort;
    }

    /**
     * 启动客户端
     *
     * @throws IOException 新建socket发生的IO异常
     */
    public void startClient() throws IOException {
        socket = new Socket(socketHost, socketPort);
        logger.info("successfully connected to server: [" + socket.getInetAddress() + "]");
        ReceiveMessageThread receiveMessageThread = new ReceiveMessageThread(socket);
        receiveMessageThread.setMessageListener(this);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(receiveMessageThread);
        sendMessage();
    }

    private void sendMessage() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            if ("".equals(msg)) {
                continue;
            }
            if ("break".equals(msg)) {
                objectOutputStream.close();
                socket.close();
                break;
            }
            SocketData socketData = new SocketData();
            socketData.setTextMessage(msg);
            socketData.setMessageType(0);
            socketData.setSenderAccount(00);
            socketData.setReceiverAccount(00);
            objectOutputStream.writeObject(socketData);
        }
    }

    @Override
    public void onMessageListener(SocketData socketData) {
        logger.info("get message from [" + socket.getInetAddress() + "]: " + socketData.toString());
    }
}
