/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software;

import cn.csu.software.client.SocketClient;
import cn.csu.software.uitl.XmlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**+
 * socket server入口
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class Main {
    private static Logger logger = LogManager.getLogger(Main.class.getSimpleName());

    private static final String SOCKET_HOST_CONFIG_NAME = "socketHost";

    private static final String SOCKET_PORT_CONFIG_NAME = "socketPort";

    private static String socketHost;

    private static int socketPort;

    /**
     * main 函数入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        socketHost = XmlUtil.getXmlConfig(SOCKET_HOST_CONFIG_NAME);
        try {
            socketPort  = Integer.parseInt(XmlUtil.getXmlConfig(SOCKET_PORT_CONFIG_NAME));
        } catch (NumberFormatException e) {
            logger.error("get socket port error");
        }
        SocketClient socketClient = new SocketClient(socketHost, socketPort);
        try {
            socketClient.startClient();
        } catch (IOException e) {
            logger.error("create client socket error");
        }
    }
}
