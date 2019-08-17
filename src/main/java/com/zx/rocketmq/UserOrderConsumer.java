package com.zx.rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.alibaba.fastjson.JSONObject;
import com.zx.bean.Packet;
import com.zx.bean.UserOrder;
import com.zx.config.AbstractConsumer;
import com.zx.config.Constant;
import com.zx.elasticsearch.UserOrderESRepository;
/**
 * 用户排名记录持久化，消费者
 * @author lishenbo
 *
 */
public class UserOrderConsumer extends AbstractConsumer implements ApplicationListener<ContextRefreshedEvent>{
	@Autowired
	private Constant constant;
	
	@Autowired
	private UserOrderESRepository userOrderESRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {	
			super.start(constant.getUserSort(), null, null, null);
	}
	

	@Override
	public ConsumeConcurrentlyStatus dealMsg(List<MessageExt> msgs) {
		if(msgs == null || msgs.isEmpty()){
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		
		List<UserOrder> orderList = new ArrayList<UserOrder>();
		for (MessageExt msg : msgs) {
			try {
				String msgStr = new String(msg.getBody(), "utf-8");
				LOGGER.info("红包记录消费"+msgStr);
				UserOrder userOrder = JSONObject.parseObject(msgStr, UserOrder.class);
				orderList.add(userOrder);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("body转字符串解析失败");
			}
		}
		userOrderESRepository.save(orderList);
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
