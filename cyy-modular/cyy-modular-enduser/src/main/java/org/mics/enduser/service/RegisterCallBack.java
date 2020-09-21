package org.mics.enduser.service;

import org.mics.enduser.entity.EndUserDO;

public interface RegisterCallBack {
	/**
	 * 注册回调
	 * @param endUserDO
	 */
	public void registerBack(EndUserDO endUserDO);
}
