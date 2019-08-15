package com.zx.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.management.*;
import java.util.*;

import static java.lang.management.ManagementFactory.*;

@Component
public abstract class GameMonitor implements GameMonitorMXBean{

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMonitor.class);

    public GameMonitor(){
        LOGGER.info("init");
    }

    private  Map<Thread.State,List<ThreadInfo>> threadInfoMap = new HashMap<>();

    public abstract  void hook();

//    @Scheduled(fixedRate = 10000,initialDelayString="20000")
    public   void report(){
        Thread.currentThread().setName("monitor");
        threadInfoMap.clear();
        LOGGER.info("start....{}",new Date());
//        int onlineNum = getOnlinePlayerSum();
//        LOGGER.info("online number is {}",onlineNum);

        hook();

        LOGGER.info("jvm info start-----------------");
        LOGGER.info(printServerState());
        LOGGER.info("jvm info end-------------------");
        LOGGER.info("end....");
    }



    @Override
    public String printServerState() {
        final long ONE_MB = 1024 * 1024;
        String newLine = "\n";
        StringBuilder result = new StringBuilder();

        try {
            long freeMemory = Runtime.getRuntime().freeMemory() / ONE_MB;   // 空闲内存
            long totalMemory = Runtime.getRuntime().totalMemory() / ONE_MB; // 当前内存
            long maxMemory = Runtime.getRuntime().maxMemory() / ONE_MB; // 最大可使用内存
            result.append(String.format("freeMemory: %s mb", freeMemory)).append(newLine);
            result.append(String.format(String.format("usedMemory: %s mb", (totalMemory - freeMemory)))).append(newLine);
            result.append(String.format("totalMemory: %s mb", totalMemory)).append(newLine);
            result.append(String.format("maxMemory: %s mb", maxMemory)).append(newLine);

            MemoryMXBean memoryMXBean = getMemoryMXBean();
            result.append(String.format("heap memory used: %s mb", memoryMXBean.getHeapMemoryUsage().getUsed() / ONE_MB)).append(newLine);
            result.append(String.format("heap memory usage: %s", memoryMXBean.getHeapMemoryUsage())).append(newLine);
            result.append(String.format("nonHeap memory usage: %s", memoryMXBean.getNonHeapMemoryUsage())).append(newLine);

            List<BufferPoolMXBean> buffMXBeans = (List<BufferPoolMXBean>) getPlatformMXBeans(BufferPoolMXBean.class);
            for (BufferPoolMXBean buffMXBean : buffMXBeans) {
                result.append(String.format("buffer pool[%s]: used %s mb, total %s mb", buffMXBean.getName(),
                        buffMXBean.getMemoryUsed() / ONE_MB, buffMXBean.getTotalCapacity() / ONE_MB)).append(newLine);
            }

            List<GarbageCollectorMXBean> gcMXBeans = getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean gcBean : gcMXBeans) {
                result.append(String.format("%s 发生 %s 次 gc, gc 总共消耗 %s 毫秒", gcBean.getName(), gcBean.getCollectionCount(), gcBean.getCollectionTime())).append(newLine);
            }

            ThreadMXBean threadMXBean = getThreadMXBean();
            int nThreadRun     = 0;
            int nThreadBlocked = 0;
            int nThreadWaiting  = 0;
            int nThreadTimeWaiting  = 0;
            for (long threadId : threadMXBean.getAllThreadIds()) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
                if (threadInfo.getThreadState() == Thread.State.RUNNABLE) {
                    nThreadRun++;
                }
                if (threadInfo.getThreadState() == Thread.State.BLOCKED) {
                    nThreadBlocked++;
                }
                if (threadInfo.getThreadState() == Thread.State.WAITING) {
                    nThreadWaiting++;
                }
                if (threadInfo.getThreadState() == Thread.State.TIMED_WAITING) {
                    nThreadTimeWaiting++;
                }
                if(!threadInfoMap.containsKey(threadInfo.getThreadState())){
                    threadInfoMap.put(threadInfo.getThreadState(),new ArrayList<ThreadInfo>());
                }
                threadInfoMap.get(threadInfo.getThreadState()).add(threadInfo);
            }
            result.append(String.format("活跃线程数 %s, 阻塞线程数 %s, 等待线程数 %s 睡眠线程数 %s", nThreadRun, nThreadBlocked, nThreadWaiting, nThreadTimeWaiting)).append(newLine);
            if(  ( nThreadRun + nThreadBlocked + nThreadWaiting + nThreadTimeWaiting ) >300 ){

                for (Map.Entry<Thread.State,List<ThreadInfo>> entry : threadInfoMap.entrySet()){
                    Thread.State  state = entry.getKey();
                    List<ThreadInfo> threadInfos = entry.getValue();
                    result.append("线程状态: ").append(state.name()).append("\n");
                    for(ThreadInfo threadInfo:threadInfos){
                        result.append("             ").append(threadInfo.getThreadName()).append("\n");
                    }
                }
//                Set<Thread.State> states = threadInfoMap.keySet();
//                for(Thread.State state : states){
//                    result.append("线程状态: ").append(state.name()).append("\n");
//                    List<ThreadInfo> threadInfos = threadInfoMap.get(state);
//                    for(ThreadInfo threadInfo:threadInfos){
//                        result.append("             ").append(threadInfo.getThreadName()).append("\n");
//                    }
//                }
            }

        } catch (Exception e) {
            LOGGER.error("printServerState->msg={}", e.getMessage(), e);
        }

        return result.toString();
    }

    @Override
    public String execJavascript(String jsCode){
        String msg = "执行成功";
        try {
            ScriptEngineManager engineManager= new ScriptEngineManager();
            ScriptEngine scriptEngine = engineManager.getEngineByName("JavaScript");
            return scriptEngine.eval(jsCode).toString();
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;
    }

}
