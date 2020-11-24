package com.kangfawei.work_queue;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Worker2 {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = ConnectionUtil.getFactory(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 确保当RabbitMQ停止或者崩溃之后重启还是不会丢失消息
        // 注意：RabbitMQ不允许用不同的参数重新定义一个已经存在的队列，否则会返回一个错误
        boolean durable = true;
        channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 保证消费者处理完当前消息并确认前不会被再次分配一条消息。
        channel.basicQos(1);

        // lambda表达式写法
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println(" [x] Received '" + message + "'");
//        };

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[x] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }
        };

        // 不会自动应答，需要等待接收到消息之后手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String task) throws InterruptedException {
        for (char c : task.toCharArray()) {
            if (c == '.') Thread.sleep(1000);
        }
    }
}
