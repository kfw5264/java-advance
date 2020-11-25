package com.kangfawei.publish_subscribe;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 */
public class LogDispatcher {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory factory = ConnectionUtil.getFactory(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME, RabbitMQConstant.RABBITMQ_PASSWORD);
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            // 声明一个交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = args.length < 1 ? "info: Hello World" :
                    String.join("", args);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息：" + message);

        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
