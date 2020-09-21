package org.mics.enduser.repository;

import org.mics.enduser.entity.EndUserDO;
import org.mics.enduser.enums.EndUserEnum.Status;
import org.mics.jpa.repository.BaseRepository;

public interface EndUserRepository extends BaseRepository<EndUserDO> {

	/**
	 * 根据设备id查询用户
	 * @param deviceId 设备id
	 * @return 存在用户数量
	 */
	Long countByDeviceId(String deviceId);

	/**
	 * 根据账号和状态查询用户
	 * @param account  账号
	 * @param active 状态
	 * @return 存在用户数量
	 */
	Long countByAccountAndStatus(String account, Status active);

	/**
	 * 根据账号和状态查询用户
	 * @param account 账号
	 * @param active 状态
	 * @return 用户信息
	 */
	EndUserDO getByAccountAndStatus(String account, Status active);

	/**
	 * 根据用户id和状态查询用户
	 * @param userId 用户id
	 * @param active 状态
	 * @return 用户信息
	 */
	EndUserDO getByIdAndStatus(String userId, Status active);

}
