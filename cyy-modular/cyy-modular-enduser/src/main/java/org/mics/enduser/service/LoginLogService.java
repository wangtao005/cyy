package org.mics.enduser.service;

/**
 * 登陆日志
 * @author mics
 * @date 2020年6月11日
 * @version  1.0
 */
public interface LoginLogService {

	/**
	 * 保存登陆日志
	 * @param userId 用户id
	 * @param ipAddr 用户ip
	 */
	void save(String userId, String ipAddr);

}
