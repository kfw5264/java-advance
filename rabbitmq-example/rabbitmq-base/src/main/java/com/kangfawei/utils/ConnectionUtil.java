package com.kangfawei.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 */
public class ConnectionUtil {

    /**
     *
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @param virtualHost 虚拟主机
     * @return ConnectionFactory
     */
    private static ConnectionFactory getFactory(String host, int port, String username, String password, String virtualHost) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setVirtualHost(virtualHost);
        return factory;
    }

    /**
     *
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return ConnectionFactory
     */
    private static ConnectionFactory getFactory(String host, int port, String username, String password) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    /**
     * 获取ConnectionFactory
     * @param host 主机
     * @param username 用户名
     * @param password 密码
     * @return 连接工厂
     */
    public static ConnectionFactory getFactory(String host, String username, String password) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    /**
     * 获取ConnectionFactory
     * @param host 主机
     * @return 连接工厂
     */
    public static ConnectionFactory getFactory(String host) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        return factory;
    }/**
     * 获取ConnectionFactory
     * @param host 主机
     * @return 连接工厂
     */
    public static ConnectionFactory getFactory() {
        return new ConnectionFactory();
    }



    /**
     * 获取Channel
     * @param host 主机
     * @param username 用户名
     * @param password 密码
     * @return Channel对象
     * @throws IOException
     * @throws TimeoutException
     */
    public static Channel getChannel(String host, String username, String password) throws IOException, TimeoutException {
        ConnectionFactory factory = getFactory(host, username, password);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
