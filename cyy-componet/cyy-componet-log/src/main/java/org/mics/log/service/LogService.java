package org.mics.log.service;

import org.mics.log.bean.OperationLogBean;

/**
 * 操作日志
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public interface LogService {

    /**
     * 保存操作日志
     *
     * @param operationLogBean
     */
    void saveOperationLog(OperationLogBean operationLogBean);

}
