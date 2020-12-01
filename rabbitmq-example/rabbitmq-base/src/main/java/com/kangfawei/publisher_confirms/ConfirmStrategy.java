package com.kangfawei.publisher_confirms;

import com.rabbitmq.client.ConnectionFactory;

/**
 * @author kangfawei
 */
public interface ConfirmStrategy {

    /**
     * @param factory connection factory
     * doc 发布消息
     */
    void publish(ConnectionFactory factory);
}
