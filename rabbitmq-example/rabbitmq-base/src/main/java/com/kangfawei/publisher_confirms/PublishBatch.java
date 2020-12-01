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
 * @author kangfawei
 */
public class PublishBatch implements ConfirmStrategy {
    @Override
    public void publish(ConnectionFactory factory) {
        try(Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare(queueName, false, false, true, null);
            // 启动Confirm
            channel.confirmSelect();
            // 每个批次的数量
            int batchSize = 100;
            // 当前数量
            int messageSize = 0;

            long start = System.currentTimeMillis();

            for (int i = 0; i < RabbitMQConstant.PUBLISH_COUNT; i++) {
                // 发布消息
                channel.basicPublish("", queueName, null, String.valueOf(i).getBytes(StandardCharsets.UTF_8));
                // 当前数量+1
                messageSize++;

                // 如果当前数量等于每个批次的数量
                if(messageSize == batchSize) {
                    messageSize = 0;
                    channel.waitForConfirmsOrDie(5_000);
                }
            }
            // 批次处理完剩余的零头
            if(messageSize > 0) {
                channel.waitForConfirmsOrDie(5_000);
            }

            System.out.println("batch方式发送" + RabbitMQConstant.PUBLISH_COUNT + "条消息花费时间：" + (System.currentTimeMillis() - start));
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
