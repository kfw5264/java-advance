package com.kangfawei.work_queue;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";
    public static void main(String[] args) {
        ConnectionFactory factory = ConnectionUtil.getFactory(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD);

        // try-with-resource方法创建连接可以自动关闭
        try(Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {

            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            String message = String.join(" ", args);
            channel.basicPublish("",
                    TASK_QUEUE_NAME,
                    // 保证消息持久性
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8));

            System.out.println("发送消息" + message);
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
