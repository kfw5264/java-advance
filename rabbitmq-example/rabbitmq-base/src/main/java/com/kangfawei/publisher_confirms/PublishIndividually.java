package com.kangfawei.publisher_confirms;

import com.kangfawei.common.RabbitMQConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfwei
 *
 */
public class PublishIndividually implements ConfirmStrategy {



    @Override
    public void publish(ConnectionFactory factory) {
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {

            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, false, false, true, null);
            // 启动Confirm
            channel.confirmSelect();
            // 发送10000条消息
            long start = System.currentTimeMillis();
            for (int i = 0; i < RabbitMQConstant.PUBLISH_COUNT; i++) {
                channel.basicPublish("", queueName, null, String.valueOf(i).getBytes(StandardCharsets.UTF_8));
                channel.waitForConfirmsOrDie(5_000);
            }
            System.out.println("individually方式发送" + RabbitMQConstant.PUBLISH_COUNT + "条消息花费时间：" + (System.currentTimeMillis() - start));

        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
