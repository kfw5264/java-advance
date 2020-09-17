package com.kangfawei.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadXmlUtil {
    public static Object getClassName(String filePath) throws Exception {

        // 创建文档对象
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document doc = builder.parse(ReadXmlUtil.class.getClassLoader().getResourceAsStream(filePath));
        // 获取包含文件类名的文档对象
        NodeList nl = doc.getElementsByTagName("className");
        Node node = nl.item(0).getFirstChild();

        String className = node.getNodeValue();
        // 通过类名生成实例并返回
        Class<?> cls = Class.forName(className);
        return cls.newInstance();
    }
}
