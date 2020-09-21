package org.mics.enduser.service;

import org.mics.core.page.PageQuery;
import org.mics.core.response.PageDataResponse;
import org.mics.enduser.request.ForgetRequest;
import org.mics.enduser.request.LoginRequest;
import org.mics.enduser.request.RegisterRequest;
import org.mics.enduser.request.SLoginRequest;
import org.mics.enduser.vo.EndUserInfoVO;
import org.mics.enduser.vo.EndUserTokenVO;
import org.mics.enduser.vo.EndUserVO;

/**
 * 终端用户
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
public interface EndUserService {

	/**
	 * 注册
	 * @param registerRequest 注册信息
	 */
	void register(RegisterRequest registerRequest);
	
	

	/**
	 * 用户登陆(手机号+密码)
	 * @param loginRequest 登陆请求
	 * @return 登陆用户信息
	 */
	EndUserVO login(LoginRequest loginRequest);
	
	/**
	 * 用户登陆(手机号+验证码)
	 * @param sLoginRequest
	 * @return 登陆用户信息
	 */
	EndUserVO slogin(SLoginRequest sLoginRequest);

	/**
	 * 根据id查询用户
	 * @param id 用户id
	 * @return 用户信息
	 */
	EndUserTokenVO info(String id);

	/**
	 * 忘记密码
	 * @param forgetRequest 忘记密码请求
	 */
	void forget(ForgetRequest forgetRequest);



	PageDataResponse<EndUserInfoVO> getEndUserListByPage(PageQuery pageQuery);



	EndUserInfoVO getUserByUserId(String userId);


	

	


}
