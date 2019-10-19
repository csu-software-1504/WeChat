/**
 * Copyright (c) 2019-2019 cn.csu.software. All rights reserved.
 */

package cn.csu.software.uitl;

import cn.csu.software.client.ReceiveMessageThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * 读取XML的工具类
 *
 * @author huangjishun 874904407@qq.com
 * @since 2019-10-12
 */
public class XmlUtil {
    private static Logger logger = LogManager.getLogger(ReceiveMessageThread.class.getSimpleName());

    private static final String USER_DIRECTORY = "user.dir";

    private static final String CONFIG_PATH = "/src/main/resources/config.xml";

    /**
     * 获取Xml配置
     *
     * @param name 配置项名字
     * @return 返回String类型的配置
     */
    public static String getXmlConfig(String name){
        String config = "";
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(System.getProperty(USER_DIRECTORY) + CONFIG_PATH));
            NodeList nodeList = document.getElementsByTagName(name);
            Node node = nodeList.item(0).getFirstChild();
            config = node.getNodeValue().trim();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error("get config error");
        }
        return config;
    }
}
