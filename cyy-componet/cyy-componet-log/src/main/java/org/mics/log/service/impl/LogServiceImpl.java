package org.mics.log.service.impl;

import org.mics.lang.bean.BeanConvertUtil;
import org.mics.log.bean.OperationLogBean;
import org.mics.log.entity.OperationLogDO;
import org.mics.log.repository.OperationLogRepository;
import org.mics.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 日志
 * @author mics
 * @date 2020年6月8日
 * @version  1.0
 */
@Service("jpaLogService")
public class LogServiceImpl implements LogService {

    @Autowired
    private OperationLogRepository operationLogRepository;


    /**
     * 保存操作日志
     *
     * @param operationLogBean
     */
    @Override
    public void saveOperationLog(OperationLogBean operationLogBean) {
        OperationLogDO operationLogDO = BeanConvertUtil.convert(operationLogBean, OperationLogDO.class);
        operationLogRepository.save(operationLogDO);
    }

}
