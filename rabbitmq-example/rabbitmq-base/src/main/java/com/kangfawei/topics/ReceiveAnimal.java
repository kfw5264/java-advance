package com.kangfawei.topics;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author kangfawei
 */
public class ReceiveAnimal {

    private static final String EXCHANGE_NAME = "animal";

    public static void main(String[] args) {
        try {
            Channel channel = ConnectionUtil.getChannel(RabbitMQConstant.RABBITMQ_HOST,
                    RabbitMQConstant.RABBITMQ_USERNAME,
                    RabbitMQConstant.RABBITMQ_PASSWORD);

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String queueName = channel.queueDeclare().getQueue();

            if (args.length < 1) {
                System.err.println("Usage: ReceiveLogsTopic [binding_key]...");
                System.exit(1);
            }

            for (String bindingKey : args) {
                channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
                System.out.println("bindingKey------>" + bindingKey);
            }

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
