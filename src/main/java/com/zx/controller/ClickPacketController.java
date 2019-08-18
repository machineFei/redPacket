package com.zx.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zx.bean.JsonResult;
import com.zx.bean.Packet;
import com.zx.bean.Prize;
import com.zx.bean.PrizeRecord;
import com.zx.bean.UserOrder;
import com.zx.config.Constant;
import com.zx.elasticsearch.PrizeESRepository;
import com.zx.elasticsearch.PrizeRecordESRepository;
import com.zx.rocketmq.PacketProducer;
import com.zx.util.DateTimeUtil;
import com.zx.util.IDFactory;
import com.zx.util.ThreadPoolManager;
/**
 * 抢红包接口
 * @author lishenbo
 *
 */
@RestController
public class ClickPacketController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClickPacketController.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private Constant constant;

	@Autowired
	private PacketProducer producer;

	@Autowired
	private PrizeRecordESRepository prizeRecordESRepository;

	@Autowired
	private PrizeESRepository prizeESRepository;

	@PostMapping("/clickredbag/{id}/{phoneNum}")
	public JsonResult save(@RequestBody Packet packet) {
		JsonResult jsonResult = new JsonResult();
		// 1.判断活动开展时间
		
		// 2.判断是否到红包开抢时间，是否超过2个小时

		// 校验红包id,是否伪造红包
		String setKey = constant.getPacketIdSetKeyPrefix()+(packet.getId().hashCode()%5);
		Boolean member = redisTemplate.opsForSet().isMember(setKey, packet.getId());
		//删除红包！！！！！！！！！！
		if(!member){
			return jsonResult;
		}
		// 3.处理红包记录
		// 3.1 redis 记录用户红包个数,达到8个则去竞争奖品
		// 用户 key RedPacket:actId:phoneNum:YYYYMMdd
		Long paeketNum = redisTemplate.opsForValue().increment("RedPacket:" + packet.getActId() + ":"
				+ packet.getPhoneNum() + ":" + DateTimeUtil.getCurrentDate("yyyyMMdd"), 1);
		if (paeketNum == 8) {
			// 获得排名
			Long userOrder = redisTemplate.opsForValue().increment("RedPacketOrder", 1);
			//判断用户排名是否超过奖品总量
			if(!isOverPrizeTotal(userOrder)){
			//可以获得奖品	
				// 根据排名获得奖品
				Prize prize = getPrize(userOrder);
				// 异步线程持久化，中奖信息
				if (prize != null) {
					jsonResult.setPrize(prize);
					PrizeRecord record = new PrizeRecord();
					record.setActid(packet.getActId());
					record.setDate(System.currentTimeMillis());
					record.setId(IDFactory.getUUID());
					record.setName(prize.getName());
					record.setPhone(packet.getPhoneNum());
					record.setPrizeId(prize.getId());
					record.setType(prize.getType());
					LOGGER.info("中奖记录:" + record.toString());
					// 异步线程记录中奖+++++++++
					ThreadPoolManager.addThread(() -> {
						prizeRecordESRepository.save(record);
					});
				}
			}
			// redis zset 记录排名信息
			redisTemplate.opsForZSet().add(constant.getUserSort(), packet.getPhoneNum(), userOrder);
			// 入消息队列，持久化排名信息
			UserOrder order = new UserOrder();
			order.setPhoneNum(packet.getPhoneNum());
			order.setSort(userOrder);
			producer.send(constant.getUserSort(), order);
		}
		// 记录日志，入消息队列
		packet.setDataTime(System.currentTimeMillis());
		LOGGER.info("获取红包记录：" + packet.toString());
		// 入消息队列+++++++++++
		producer.send(constant.getPacketTopic(), packet);
		return jsonResult;
	}

	/**
	 * 根据排名获得奖品
	 */
	public Prize getPrize(Long userOrder) {
		Prize prize = null; // 用户获得奖品

		// 查redis,redis为空查es
		@SuppressWarnings("unchecked")
		List<Prize> prizeList = (ArrayList<Prize>) redisTemplate.opsForValue().get(constant.getPrizeSetInfo());
		if (prizeList == null || prizeList.isEmpty()) {
			Order order = new Order(Sort.Direction.ASC, "level");
			Sort sort = new Sort(order);
			Iterable<Prize> prizes = prizeESRepository.findAll(sort);

			// 奖品信息缓存redis
			List<Prize> prizeArrayList = new ArrayList<Prize>();
			for (Prize pri : prizes) {
				prizeArrayList.add(pri);
			}
			redisTemplate.opsForValue().set(constant.getPrizeSetInfo(), prizeArrayList);

			prizeList = prizeArrayList;
		}
		
		//判断获得奖品
		int count = 0;
		for (Prize priz : prizeList) {
			count += priz.getAmount();
			if(userOrder <= count){
				prize = priz;
				break;
			}
		}

		return prize;
	}

	/**
	 * 获得奖品总量
	 * 
	 * @return
	 */
	public Integer countPrize() {
		Integer count = 0; // 全部奖品等级的，奖品总量

		// 查redis
		@SuppressWarnings("unchecked")
		List<Prize> prizeList = (ArrayList<Prize>) redisTemplate.opsForValue().get(constant.getPrizeSetInfo());
		// redis空，查Es
		if (prizeList == null || prizeList.isEmpty()) {
			Order order = new Order(Sort.Direction.ASC, "level");
			Sort sort = new Sort(order);
			Iterable<Prize> prizes = prizeESRepository.findAll(sort);

			// 奖品信息缓存redis
			List<Prize> prizeArrayList = new ArrayList<Prize>();
			for (Prize prize : prizes) {
				count += prize.getAmount(); // 统计奖品总量
				prizeArrayList.add(prize);
			}
			redisTemplate.opsForValue().set(constant.getPrizeSetInfo(), prizeArrayList);

			return count;
		} else {
			for (Prize prize : prizeList) {
				count += prize.getAmount(); // 统计奖品总量
			}
			return count;
		}
	}
	
	/**
	 * 判断用户排名，是否超过奖品总量，超过则不能中奖
	 * @return
	 */
	public boolean isOverPrizeTotal(Long userOrder){
		//查redis
		Integer prizeTotal = (Integer)redisTemplate.opsForValue().get(constant.getPrizeTotal());
		//countPrize获取
		if(prizeTotal == null || prizeTotal == 0){
			prizeTotal = countPrize();
			redisTemplate.opsForValue().set(constant.getPrizeTotal(), prizeTotal);
		}
		
		if(userOrder > prizeTotal){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断活动是否开始
	 */
}
