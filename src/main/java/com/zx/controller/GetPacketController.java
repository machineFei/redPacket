package com.zx.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zx.bean.Activity;
import com.zx.bean.JsonResult;
import com.zx.config.Constant;
import com.zx.util.IDFactory;

/**
 * 红包生成接口
 * @author lishenbo
 *
 */
@RestController
public class GetPacketController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClickPacketController.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ActivityHelper activityHelper;

	@Autowired
	private Constant constant;
	
	@GetMapping("/getredbag/{actid}")
	public JsonResult getPacket(@PathVariable String actid){
		JsonResult jsonResult = new JsonResult();
		Activity activity = activityHelper.getActivity(actid);
		
		//判断红包开始时间
		if(System.currentTimeMillis() < activity.getPrizeTime()){
			jsonResult.setCode(-9999);
			jsonResult.setMsg("抢红包时间未到");
			return jsonResult;
		}
		//判断红包结束时间
		if(activity.getPrizeTime()+activity.getDutation() < System.currentTimeMillis()){
			jsonResult.setCode(-9999);
			jsonResult.setMsg("抢红包时间已结束");
		}
		
		//每次返回20个红包id,减少调用次数
		List<String> packetIdList = new ArrayList<String>();
		for(int i = 0; i < 20; i++){
			packetIdList.add(IDFactory.getUUID());
		}
		
		//红包id存redis，待校验.再存redis set集合 ；hashcode%5分set存储
		redisTemplate.executePipelined(new RedisCallback() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				packetIdList.forEach((prizeId)->{
					String key = constant.getPacketIdSetKeyPrefix()+":"+actid+":"+(prizeId.hashCode()%5);
					Jackson2JsonRedisSerializer valueSerializer = (Jackson2JsonRedisSerializer)redisTemplate.getValueSerializer();
					try {
						connection.sAdd(key.getBytes("UTF-8"), 
								valueSerializer.serialize(prizeId));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
				return null;
			}
		});
		
		return jsonResult;
	}
	
	public static void main(String[] args) {
		// 每次返回20个红包id,减少调用次数
		List<String> packetIdList = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			packetIdList.add(IDFactory.getUUID());
		}
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(0);
		jsonResult.setMsg("success");
		jsonResult.setPacketIdList(packetIdList);
		System.out.println(JSONObject.toJSONString(jsonResult));
	}

}
