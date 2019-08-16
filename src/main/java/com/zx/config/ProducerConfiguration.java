package com.zx.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProducerConfiguration.class);
	
	@Autowired
	private RocketMQConfig mqConfig;
	
	/**
	 * 普通消息生产者
	 * @return
	 */
	@Bean
	private DefaultMQProducer defaultMQProducer() {
		LOGGER.info(mqConfig.toString());
		LOGGER.info("defaultProducer 正在创建---------------------------------------");
		DefaultMQProducer producer = new DefaultMQProducer(mqConfig.getProducerGroup());
		producer.setNamesrvAddr(mqConfig.getNamesrvAddr());
		producer.setVipChannelEnabled(false);
		producer.setRetryTimesWhenSendAsyncFailed(3);
		try {
			producer.start();
			LOGGER.info("rocketmq producer server开启成功---------------------------------.");
		} catch (MQClientException e) {
			LOGGER.error("rocketmq producer server开启失败---------------------------------.", e);
		}
		return producer;
	}
}
