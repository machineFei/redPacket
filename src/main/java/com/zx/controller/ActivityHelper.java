/**
 * 
 */
package com.zx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zx.bean.Activity;
import com.zx.config.Constant;
import com.zx.elasticsearch.ActivityESRepository;

/**
 * 获取活动信息类
 * @author lishenbo
 *
 */
@Service
public class ActivityHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClickPacketController.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ActivityESRepository activityESRepository;
	
	@Autowired
	private Constant constant;
	
	/**
	 * 根据活动id,获取活动信息
	 * @param actid
	 * @return
	 */
	public Activity getActivity(String actid){
		Activity activity = (Activity)redisTemplate.opsForValue()
				.get(constant.getActivityRedisPrefix()+":"+actid);
		if(activity == null){
			activity = activityESRepository.findOne(actid);
			redisTemplate.opsForValue().set(constant.getActivityRedisPrefix()+":"+actid,
					activity);
		}
		return activity;
	}

}
