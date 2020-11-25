package com.kangfawei.publish_subscribe;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 */
public class PrintLogToFile {

    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) {
        try {
            Channel channel = ConnectionUtil.getChannel(RabbitMQConstant.RABBITMQ_HOST,
                    RabbitMQConstant.RABBITMQ_USERNAME, RabbitMQConstant.RABBITMQ_PASSWORD);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");
            System.out.println("等待接收消息......");

            DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                // 保存到文件
                File file = new File("E:\\var\\rabbitLog.txt");

                if(!file.exists()) {
                    file.createNewFile();
                }

                FileWriter writer = new FileWriter(file, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(message + "\r\n");
                bufferedWriter.close();
                writer.close();

                System.out.println("日志写入文件成功！");

            });

            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
