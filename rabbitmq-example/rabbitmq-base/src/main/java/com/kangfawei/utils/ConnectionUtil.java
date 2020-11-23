package com.kangfawei.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
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
