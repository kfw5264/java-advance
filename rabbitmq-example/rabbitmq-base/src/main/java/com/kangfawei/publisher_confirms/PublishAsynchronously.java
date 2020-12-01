package com.kangfawei.publisher_confirms;

import com.kangfawei.common.RabbitMQConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;

/**i
 * @author kangfawei
 */
public class PublishAsynchronously implements ConfirmStrategy {

    @Override
    public void publish(ConnectionFactory factory) {
        try(Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {

            String queueName = UUID.randomUUID().toString();
            channel.queueDeclare("", false, false, true, null);
            // 开启confirm
            channel.confirmSelect();

            // 通过一个map把序列号跟消息关联起来
            ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

            // 删除映射中对应的条目
            ConfirmCallback cleanOutstandingConfirms = (sequenceNumber, multiple) -> {
                // multiple如果为true，所有小于或等于sequenceNumber的都被确认或者丢失
                // 如果为false，则只有一条消息被处理或者丢失
                if(multiple) {
                    // 用另外一个map保存所有小于或者等于sequenceNumber的消息
                    ConcurrentNavigableMap<Long, String> confirmedOrNackEdMessages = outstandingConfirms.headMap(sequenceNumber, true);
                    confirmedOrNackEdMessages.clear();
                } else {
                    // 又有一条消息，直接清理了就好
                    outstandingConfirms.remove(sequenceNumber);
                }
            };

            channel.addConfirmListener(
                    // 已确认消息的回调,
                    (sequenceNumber, multiple) -> {
//                        String body = outstandingConfirms.get(sequenceNumber);
//                        System.out.println(sequenceNumber + "---" + body + "---已确认！");
                        cleanOutstandingConfirms.handle(sequenceNumber, multiple);
                    },
                    // 丢失的消息的回调
                    (sequenceNumber, multiple) -> {
                        String body = outstandingConfirms.get(sequenceNumber);
                        System.out.println(sequenceNumber + "---" + body + "---被丢失！");
                        cleanOutstandingConfirms.handle(sequenceNumber, multiple);
                    });
            long start = System.currentTimeMillis();
            for (int i = 0; i < RabbitMQConstant.PUBLISH_COUNT; i++) {
                String body = String.valueOf(i);
                // 将消息与序列号通过map关联起来
                outstandingConfirms.put(channel.getNextPublishSeqNo(), body);
                // 发布消息
                channel.basicPublish("", queueName, null, body.getBytes(StandardCharsets.UTF_8));
            }
            if (!waitUntil(Duration.ofSeconds(60), outstandingConfirms::isEmpty)) {
                throw new IllegalStateException("All messages could not be confirmed in 60 seconds");
            }

            System.out.println("Asynchronously方式发送" + RabbitMQConstant.PUBLISH_COUNT + "条消息花费时间：" + (System.currentTimeMillis() - start));


        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static boolean waitUntil(Duration timeout, BooleanSupplier condition) throws InterruptedException {
        int waited = 0;
        while (!condition.getAsBoolean() && waited < timeout.toMillis()) {
            Thread.sleep(100L);
            waited = +100;
        }
        return condition.getAsBoolean();
    }
}

