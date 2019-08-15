package com.zx.rocketmq;

import com.alibaba.fastjson.JSON;
import com.zx.msgautosend.SendByTime10;
import com.zx.msgautosend.SendByTime30;
import com.zx.msgautosend.SendByTimeDay;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyProducer.class);

    @Value("${rocketmq.producerGroup}")
    private String producerGroup;


    @Value("${rocketmq.namesrvaddr}")
    private String namesrvAddr;


    @Autowired
    private SendByTime30 sendByTime30;

    @Autowired
    private SendByTimeDay sendByTimeDay;

    public void startProducer(){
        this.producer();
    }

//    @PostConstruct
    private void producer() {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
            for (int i = 0; i < 100; i++) {
                Thread.sleep(500);
                String phoneNum = String.valueOf(13700000000L+i);
                MyMessage userContent = new MyMessage(phoneNum,"abc"+i);
                String jsonstr = JSON.toJSONString(userContent);
                LOGGER.info("发送消息:"+jsonstr);
                Message message = new Message("my-topic", "my-tag", jsonstr.getBytes(RemotingHelper.DEFAULT_CHARSET));

                if (i%5==0){
                    message.setDelayTimeLevel(5);//messageDelayLevel=30s 1m 2m 5m 10m 30m 1h 2h
                    sendByTime30.addDataToMap(phoneNum,System.currentTimeMillis()+300000);
                    sendByTimeDay.addMassage(phoneNum);
                }

                SendResult result = producer.send(message);
                LOGGER.info("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }

}
