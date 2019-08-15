package com.zx.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CoreGameMonitor extends  GameMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreGameMonitor.class);
    private static long startTime = 0;
    public CoreGameMonitor(){
        LOGGER.info("initCoreGameMonitor");
    }

    @Override
    public void hook(){
        LOGGER.info("do nothing ...");
    }

    @Override
    public int getOnlinePlayerSum() {
        return 0;
    }
}
