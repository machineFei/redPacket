package com.zx.msgautosend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SendByTime10 implements SendByTime{

    private static final Logger LOGGER = LoggerFactory.getLogger(SendByTime10.class);


    private ConcurrentHashMap<String,Long> min10Map = new ConcurrentHashMap<>();

    @Override
//    @Scheduled(fixedRate = 60000,initialDelay = 10000)
    public void sendMessage() {
        LOGGER.info("SendByTime10 sendMessage method ... ");
        if (min10Map.size()<=0){
            return;
        }
        long now = System.currentTimeMillis();
        Iterator iterator = min10Map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Long> entry = (Map.Entry<String, Long>) iterator.next();
            String key = entry.getKey();
            Long value = entry.getValue();
            if (value - now <= 0){
                //TODO send message
                iterator.remove();
                LOGGER.info("SendByTime10 sendMessage ... ");
            }
        }
    }

    @Override
    public void collectData() {

    }

    public ConcurrentHashMap<String, Long> getMin10Map() {
        return min10Map;
    }

    public void setMin10Map(ConcurrentHashMap<String, Long> min10Map) {
        this.min10Map = min10Map;
    }

    public void addToMin10Map(HashMap<String, Long> inMap){
        this.min10Map.putAll(inMap);
    }
}
