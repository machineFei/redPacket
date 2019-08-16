package com.zx.config;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AbstractConsumer {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractConsumer.class); 

	@Autowired
	private RocketMQConfig mqConfig;

	// 开启消费者监听服务
	public void start(String topic, String tag) {
		LOGGER.info("开启" + topic + ":" + tag + "消费者-------------------");
		LOGGER.info(mqConfig.toString());

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(mqConfig.getConumerGroup());

		consumer.setNamesrvAddr(mqConfig.getNamesrvAddr());

		try {
			consumer.subscribe(topic, tag);
		} catch (MQClientException e) {
			LOGGER.error("消费者订阅主题异常--------", e);
		}

		// 开启内部类实现监听
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				return AbstractConsumer.this.dealMsg(msgs);
			}
		});

		try {
			consumer.start();
			LOGGER.info("rocketmq 消费者启动成功---------------------------------------");
		} catch (MQClientException e) {
			LOGGER.error("rocketmq 消费者启动异常---------------------------------------", e);
		}

		

	}

	// 处理body的业务
	public abstract ConsumeConcurrentlyStatus dealMsg(List<MessageExt> msgs);

}
