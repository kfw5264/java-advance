package com.kangfawei.rpc;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 * 继承AutoCloseable意味着可以通过try-with-resource自动关闭连接
 */
public class RPCClient implements AutoCloseable {
    private final Connection connection;
    private final Channel channel;
    private static final String QUEUE_NAME = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException, TimeoutException {
        connection = ConnectionUtil.getFactory(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD).newConnection();
        channel = connection.createChannel();
    }

    public static void main(String[] argv) {
        // try-with-resource
        try (RPCClient fibonacciRpc = new RPCClient()) {
            for (int i = 0; i < 32; i++) {
                String intStr = Integer.toString(i);
                String response = fibonacciRpc.call(intStr);
                System.out.println("远程调用参数为" + intStr + "--返回结果为" + response);
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", QUEUE_NAME, props, message.getBytes(StandardCharsets.UTF_8));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            if(delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
            }
        };

        String ctag = channel.basicConsume(replyQueueName, true, deliverCallback, consumerTag -> { });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}