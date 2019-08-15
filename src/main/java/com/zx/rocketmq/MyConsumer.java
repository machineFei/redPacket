package com.zx.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyConsumer.class);

    @Value("${rocketmq.conumerGroup}")
    private String consumerGroup;


    @Value("${rocketmq.namesrvaddr}")
    private String namesrvAddr;


    public void startConsumer(){
        this.consumer();
    }

    //    @PostConstruct
    public void consumer() {

        LOGGER.info("init defaultMQPushConsumer...");

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            consumer.subscribe("my-topic", "my-tag");
            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
                try {
                    for (MessageExt messageExt : list) {

                        LOGGER.info("消费消息: " + new String(messageExt.getBody()));//输出消息内容
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
            });
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
