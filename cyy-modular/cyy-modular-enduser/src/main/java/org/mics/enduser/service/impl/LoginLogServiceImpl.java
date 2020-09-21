package org.mics.enduser.service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.mics.enduser.entity.EndUserLoginLogDO;
import org.mics.enduser.repository.LoginLogRepository;
import org.mics.enduser.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * 登陆日志
 * @author mics
 * @date 2020年6月11日
 * @version  1.0
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
	@Resource
	private LoginLogRepository loginLogRepository;
	
	@Override
	@Transactional
	public void save(String userId, String ipAddr) {
        EndUserLoginLogDO loginLogDO = new EndUserLoginLogDO();
        loginLogDO.setEndUserId(userId);
        loginLogDO.setLoginIp(ipAddr);
        loginLogRepository.save(loginLogDO);
	}

}
