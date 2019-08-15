package com.zx.msgautosend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SendByTimeDay implements SendByTime{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendByTimeDay.class);

    private ConcurrentHashMap<String,Long> loginMap = new ConcurrentHashMap<>();

    private CopyOnWriteArraySet<String> daySendSet = new CopyOnWriteArraySet<>();

//    private static final long twoDay = 2 * 86400000;
    private static final long twoDay = 300000;

    @Override
//    @Scheduled(cron = "0 0 16 * * ?")//每天下午4点
//    @Scheduled(fixedRate = 60000,initialDelay = 10000)
    public void sendMessage() {
        if (daySendSet.isEmpty()){
            return;
        }
        LOGGER.info("SendByTimeDay sendMessage method ... ");

        for (String phone : daySendSet){
            // TODO send message
            LOGGER.info("SendByTimeDay sendMessage ... " + phone);
        }
        daySendSet.clear();
        LOGGER.info("SendByTimeDay sendMessage done ... ");
    }

    @Override
//    @Scheduled(cron = "0 0 2 * * ?")//每天凌晨2点
//    @Scheduled(fixedRate = 300000,initialDelay = 10000)
    public void collectData() {

        LOGGER.info("SendByTimeDay collectData ... ");

        long now = System.currentTimeMillis();
        Iterator iterator = loginMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iterator.next();
            String key = entry.getKey();
            Long value = entry.getValue();
            if (now - value >= twoDay){
                daySendSet.add(key);
                LOGGER.info("collectData phoneNum : " + key);
                iterator.remove();
            }
        }
        LOGGER.info("SendByTimeDay collectData done ... ");
    }


    public void addMassage(String phoneNum){

        LOGGER.info("add to login map : " + phoneNum);
        loginMap.put(phoneNum,System.currentTimeMillis());

    }

}
