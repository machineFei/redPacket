package com.zx.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义线程池
 * @author lishenbo
 *
 */
public class ThreadPoolManager {

   private static final Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);
 
   private static ThreadPoolManager tpm = new ThreadPoolManager();
   /**
    * 核心线程数
    */
   private static final int CORE_POOL_SIZE = 10;
   //最大线程数
   private static final int MAX_POOL_SIZE = 100;
   //非核心线程存活时间
   private static final long KEEP_ALIVE_TIME = 0L;
   //任务等待队列大小
   private static final int WORK_QUEUE_SIZE = 300;
   
   private static final int TASK_QOS_PERIOD = 300;
   //拒绝执行任务暂存队列，之后会被定时任务线程再次执行
   private Queue<Runnable> taskQueue = new LinkedList();
 
   final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
     public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {
       ThreadPoolManager.this.taskQueue.offer(task);
     }
   };
 
   final Runnable accessBufferThread = new Runnable() {
     public void run() {
       if (ThreadPoolManager.this.hasMoreAcquire())
         ThreadPoolManager.this.threadPool.execute((Runnable)ThreadPoolManager.this.taskQueue.poll());
     }
   };
 
   final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
 
   final ScheduledFuture<?> taskHandler = this.scheduler.scheduleAtFixedRate(this.accessBufferThread, 0L, 300L, TimeUnit.MILLISECONDS);
   /**
    * 工作线程池
    */
   final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
		   TimeUnit.SECONDS, new ArrayBlockingQueue(WORK_QUEUE_SIZE), this.handler);
   
   //返回单例对象
   public static ThreadPoolManager newInstance()
   {
     return tpm;
   }
 
   public static void addThread(Runnable task) {
     tpm.addExecuteTask(task);
   }
 
   private boolean hasMoreAcquire()
   {
     return !this.taskQueue.isEmpty();
   }
 
   public void addExecuteTask(Runnable task)
   {
     if (task != null)
       this.threadPool.execute(task);
   }
 
   public static void shutdown()
   {
     tpm.threadPool.shutdown();
     try
     {
       if (!tpm.threadPool.awaitTermination(60L, TimeUnit.SECONDS)) {
         tpm.threadPool.shutdownNow();
         logger.warn("Interrupt the worker, which may cause some task inconsistent. Please check the biz logs.");
 
         if (!tpm.threadPool.awaitTermination(60L, TimeUnit.SECONDS))
           logger.error("Thread pool can't be shutdown even with interrupting worker threads, which may cause some task inconsistent. Please check the biz logs.");
       }
     } catch (InterruptedException ie) {
       tpm.threadPool.shutdownNow();
       logger.error("The current server thread is interrupted when it is trying to stop the worker threads. This may leave an inconcistent state. Please check the biz logs.");
 
       Thread.currentThread().interrupt();
     }
   }
 
}
