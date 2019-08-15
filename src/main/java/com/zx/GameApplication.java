package com.zx;

import com.zx.rocketmq.MyConsumer;
import com.zx.rocketmq.MyProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GameApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameApplication.class);

    private static SpringApplication app = null;
    private static ConfigurableApplicationContext configurableApplicationContext = null;

    public static boolean start = false;

    public static void main(String[] args) throws Exception {

        start = true;
        ServerInfo.saveSystemProperty();
        app = new SpringApplication(GameApplication.class);
        app.setWebEnvironment(true);
        configurableApplicationContext = app.run(args);

//        MyProducer myProducer = configurableApplicationContext.getBean(MyProducer.class);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Thread.currentThread().setName("MyProducer-thread");
//                myProducer.startProducer();
//            }
//        }).start();
//
//
//        MyConsumer myConsumer = configurableApplicationContext.getBean(MyConsumer.class);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Thread.currentThread().setName("MyConsumer-thread");
//                myConsumer.startConsumer();
//            }
//        }).start();

    }
}
