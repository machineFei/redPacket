package com.zx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zx.bean.JsonResult;
import com.zx.bean.Packet;
import com.zx.config.Constant;
import com.zx.rocketmq.MyProducer;
import com.zx.util.DateTimeUtil;

@RestController("/packet")
public class PacketController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PacketController.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private Constant constant;
	
	@PostMapping("/save")
	public JsonResult save(@RequestBody Packet packet){
		JsonResult jsonResult = new JsonResult();
		//1.判断活动开展时间
		
		//2.判断是否到红包开抢时间
		
		//3.处理红包记录
		//3.1 redis 记录用户红包个数,达到8个则去竞争奖品
		//用户 key RedPacket:actId:phoneNum:YYYYMMdd
		Long paeketNum = redisTemplate.opsForValue().increment("RedPacket:"+packet.getActId()+":"+packet.getPhoneNum()+
				":"+DateTimeUtil.getCurrentDate("yyyyMMdd"), 1);
		if(paeketNum == 8){
		//可以去竞争奖品
			Object pirze = redisTemplate.opsForList().rightPop(constant.getPrizeList());
		}else {
		//普通红包，记录日志，入消息队列
			packet.setDataTime(System.currentTimeMillis());
			LOGGER.info("获取红包记录："+packet.toString());
		}
		return jsonResult;
	}
}
