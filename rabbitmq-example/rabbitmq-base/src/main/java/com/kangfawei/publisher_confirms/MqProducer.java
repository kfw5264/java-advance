package com.kangfawei.publisher_confirms;

import com.kangfawei.common.RabbitMQConstant;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author kangfawei
 */
public class MqProducer {

    private  ConfirmStrategy confirmStrategy;
    private static ConnectionFactory factory;
    private static final String QUEUE_NAME = "confirm_demo";

    private MqProducer(ConfirmStrategy confirmStrategy) {
         this.confirmStrategy = confirmStrategy;
    }

    static {
        factory = new ConnectionFactory();
        factory.setHost(RabbitMQConstant.RABBITMQ_HOST);
        factory.setUsername(RabbitMQConstant.RABBITMQ_USERNAME);
        factory.setPassword(RabbitMQConstant.RABBITMQ_PASSWORD);
    }

    // result:
    //    individually方式发送10000条消息花费时间：5598
    //    batch方式发送10000条消息花费时间：880
    //    Asynchronously方式发送10000条消息花费时间：341
    public static void main(String[] args) {
        ConfirmStrategy individuallyStrategy = new PublishIndividually();
        ConfirmStrategy batchStrategy = new PublishBatch();
        ConfirmStrategy asynchronouslyStrategy = new PublishAsynchronously();

        MqProducer producerIndividually = new MqProducer(individuallyStrategy);
        producerIndividually.confirmStrategy.publish(factory);

        MqProducer producerBatch = new MqProducer(batchStrategy);
        producerBatch.confirmStrategy.publish(factory);

        MqProducer producerAsynchronously = new MqProducer(asynchronouslyStrategy);
        producerAsynchronously.confirmStrategy.publish(factory);
    }


}
