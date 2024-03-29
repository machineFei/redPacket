package com.zx.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zx.bean.Packet;
import com.zx.config.AbstractConsumer;
import com.zx.config.Constant;
import com.zx.elasticsearch.PacketESRepository;
/**
 * 红包记录消费者
 * @author lishenbo
 *
 */
@Service
public class PacketConsumer extends AbstractConsumer implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private Constant constant;
	
	@Autowired
	private PacketESRepository packetESRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {	
			super.start(constant.getPacketTopic(), null, 200, 500);
	}

	@Override
	public ConsumeConcurrentlyStatus dealMsg(List<MessageExt> msgs) {
		if(msgs == null || msgs.isEmpty()){
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		
		List<Packet> packetList = new ArrayList<Packet>();
		for (MessageExt msg : msgs) {
			try {
				String msgStr = new String(msg.getBody(), "utf-8");
				LOGGER.info("红包记录消费"+msgStr);
				Packet packet = JSONObject.parseObject(msgStr, Packet.class);
				packetList.add(packet);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("body转字符串解析失败");
			}
		}
		packetESRepository.save(packetList);
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
