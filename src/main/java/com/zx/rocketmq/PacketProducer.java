package com.zx.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 普通消息生产者
 * @author lishenbo
 *
 */
@Service
public class PacketProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketProducer.class);
	
	@Autowired
	private DefaultMQProducer producer;
	
	public void send(String topic, Object object){
		Message message = new Message(topic, JSONObject.toJSONString(object).getBytes());
		try {
			producer.send(message, new SendCallback() {
				
				@Override
				public void onSuccess(SendResult result) {
					LOGGER.info("消息发送成功："+JSONObject.toJSONString(result));
				}
				
				@Override
				public void onException(Throwable e) {
					LOGGER.error("消息发送失败：",e);
				}
			});
		} catch (MQClientException e) {
			LOGGER.error("消息发送异常：",e);
		} catch (RemotingException e) {
			LOGGER.error("消息发送异常：",e);
		} catch (InterruptedException e) {
			LOGGER.error("消息发送异常：",e);
		}
	}
}
