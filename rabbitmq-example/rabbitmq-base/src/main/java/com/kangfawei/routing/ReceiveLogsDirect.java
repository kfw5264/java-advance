package com.kangfawei.routing;

import com.kangfawei.common.RabbitMQConstant;
import com.kangfawei.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

/**
 * @author kangfawei
 */
public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        Channel channel = ConnectionUtil.getChannel(RabbitMQConstant.RABBITMQ_HOST,
                RabbitMQConstant.RABBITMQ_USERNAME,
                RabbitMQConstant.RABBITMQ_PASSWORD);

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

        for (String severity : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}