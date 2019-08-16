package com.zx.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.zx.config.AbstractConsumer;
import com.zx.config.RocketMQConfig;

@Service
public class PacketConsumer extends AbstractConsumer implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private RocketMQConfig mqConfig;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {	
			super.start(mqConfig.getPacketTopic(), mqConfig.getPacketTag());
	}

	@Override
	public ConsumeConcurrentlyStatus dealMsg(List<MessageExt> msgs) {
		int num = 1;
		LOGGER.info("进入");
		for (MessageExt msg : msgs) {
			LOGGER.info("第" + num + "次消息");
			try {
				String msgStr = new String(msg.getBody(), "utf-8");
				LOGGER.info(msgStr);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("body转字符串解析失败");
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
