package com.zx.msgautosend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SendByTime30 implements SendByTime{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendByTime30.class);

    private ConcurrentHashMap<String,Long> min30Map = new ConcurrentHashMap<>();

//    private static final long halfHour = 1800000;

    private static final long halfHour = 120000;

    @Autowired
    private SendByTime10 sendByTime10;

    @Override
//    @Scheduled(fixedRate = 300000,initialDelay = 10000)
    public void sendMessage() {
        LOGGER.info("SendByTime30 sendMessage ... ");
        if (min30Map.size()<=0){
            return;
        }
        long now = System.currentTimeMillis();
        Iterator iterator = min30Map.entrySet().iterator();
        HashMap<String, Long> outMap = new HashMap<>();
        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iterator.next();
            String key = entry.getKey();
            Long value = entry.getValue();
            if (value - now >= halfHour){
                outMap.put(key,value);
                iterator.remove();
            }
        }
        sendByTime10.addToMin10Map(outMap);
        LOGGER.info("SendByTime30 insert to SendByTime10 ... ");
    }

    public void addDataToMap(String phoneNum, long targetTime){
        min30Map.put(phoneNum,targetTime);
    }

    private void removeFromMap(String phoneNum){
        min30Map.remove(phoneNum);
    }


    @Override
    public void collectData() {

    }
}
