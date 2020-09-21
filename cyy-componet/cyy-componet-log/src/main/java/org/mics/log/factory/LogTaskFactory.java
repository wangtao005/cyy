package org.mics.log.factory;

import org.mics.core.context.SpringContextHolder;
import org.mics.log.bean.OperationLogBean;
import org.mics.log.service.LogService;

/**
 * 日志任务工厂
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class LogTaskFactory {

    /**
     * 成功
     */
    public static final String SUCCESS = "成功";

    /**
     * 失败
     */
    public static final String FAIL = "失败";

    private static LogService logService = SpringContextHolder.getBean(LogService.class);


    /**
     * 操作日志
     *
     * @param loginName
     * @param operation
     * @param method
     * @param params
     * @param ip
     * @param time
     * @param message
     * @return
     */
    public static Runnable operationLogBean(String loginName, String operation, String method,
                                             String params, String ip, Long time, String message) {
        return new Runnable() {
            @Override
            public void run() {
                logService.saveOperationLog(new OperationLogBean(loginName, operation, method, params, ip, time, message));
            }
        };
    }

    
}
