/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software;

import cn.csu.software.server.SocketThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket server入口
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class Main {
    private static Logger logger = LogManager.getLogger(Main.class.getSimpleName());

    private static final int socketPort = 8888;

    /**
     * main 函数入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        logger.info("server has been started, waiting for client connection");
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            ServerSocket serverSocket = new ServerSocket(socketPort);
            while (true) {
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new SocketThread(socket);
                executorService.execute(socketThread);
            }
        } catch (IOException e) {
            logger.error("create server socket or accept socket error");
        }
    }
}
