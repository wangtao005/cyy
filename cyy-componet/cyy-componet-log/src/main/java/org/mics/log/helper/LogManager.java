package org.mics.log.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 日志管理器
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
public class LogManager {
    /**
     * 构造线程池
     */
	
	  ExecutorService executor = new ThreadPoolExecutor(5,
			  200,
			  0L,
			  TimeUnit.MILLISECONDS,
			  new LinkedBlockingQueue<Runnable>(1024),
			  new ThreadPoolExecutor.AbortPolicy()
			  );
	 
    

    private LogManager() {
    	
    }

    public static LogManager logManager = new LogManager();

    /**
     * 对外
     * @return
     */
    public static LogManager get() {
        return logManager;
    }

    /**
     * 执行线程
     * @param task
     */
    public void executeLog(Runnable task) {
        executor.execute(task);
    }
}
