package org.mics.enduser.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.mics.core.context.ServletContextHolder;
import org.mics.core.page.PageInfo;
import org.mics.core.page.PageQuery;
import org.mics.core.response.PageDataResponse;
import org.mics.email.request.EmailVerificationCodeCheckRequest;
import org.mics.email.service.EmailCodeService;
import org.mics.enduser.entity.EndUserDO;
import org.mics.enduser.enums.EndUserEnum;
import org.mics.enduser.exception.EndUserException;
import org.mics.enduser.repository.EndUserRepository;
import org.mics.enduser.request.ForgetRequest;
import org.mics.enduser.request.LoginRequest;
import org.mics.enduser.request.RegisterRequest;
import org.mics.enduser.request.SLoginRequest;
import org.mics.enduser.service.EndUserService;
import org.mics.enduser.service.LoginLogService;
import org.mics.enduser.service.RegisterCallBack;
import org.mics.enduser.vo.EndUserInfoVO;
import org.mics.enduser.vo.EndUserTokenVO;
import org.mics.enduser.vo.EndUserVO;
import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.net.IpUtils;
import org.mics.lang.security.PasswordUtil;
import org.mics.sms.request.SmsVerificationCodeCheckRequest;
import org.mics.sms.service.SmsCodeService;
import org.mics.token.enums.TokenEnum;
import org.mics.token.jwt.JwtPayLoad;
import org.mics.token.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * 终端用户
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@Service
public class EndUserServiceImpl implements EndUserService {
	@Resource
	private EndUserRepository endUserRepository;
	@Resource
	private SmsCodeService smsCodeService;
	@Resource
	private EmailCodeService emailCodeService;
	@Resource
	private LoginLogService loginLogService;
	@Resource
	private TokenService tokenService;
	@Resource
	private RegisterCallBack registerCallBack;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EndUserServiceImpl.class);
	
	
	@Override
	@Transactional
	public void register(RegisterRequest registerRequest) {
		LOGGER.debug("账号注册[registerRequest:{}]", registerRequest);

        //查询设备
		/*
		 * Long deviceIdCount =
		 * endUserRepository.countByDeviceId(registerRequest.getDeviceId()); if
		 * (deviceIdCount > 0) { LOGGER.info("用户注册失败，用户设备数据已经存在[deviceId:{}]",
		 * registerRequest.getDeviceId()); throw new EndUserException("设备信息异常，请联系管理员");
		 * }
		 */
        
        //查询用户
        Long accountCount = endUserRepository.countByAccountAndStatus(registerRequest.getAccount(), EndUserEnum.Status.ACTIVE);
        if (accountCount  > 0) {
            LOGGER.info("用户注册失败，用户数据已经存在[account:{}]", registerRequest.getAccount());
            throw new EndUserException("用户数据异常，请联系管理员");
        }
  
       //check验证码
		String regexp = "^1(3|4|5|6|7|8|9)\\d{9}$";
		if(Pattern.matches(regexp, registerRequest.getAccount())){
			SmsVerificationCodeCheckRequest smsVerificationCodeCheckRequest = BeanConvertUtil.convert(registerRequest.getVerificationCodeCheckRequest(), SmsVerificationCodeCheckRequest.class);
	        smsCodeService.checkCode(smsVerificationCodeCheckRequest);
		}else {
			EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest = BeanConvertUtil.convert(registerRequest.getVerificationCodeCheckRequest(), EmailVerificationCodeCheckRequest.class);
			emailCodeService.checkCode(emailVerificationCodeCheckRequest);
		}
        //ok，绑定起来
        EndUserDO endUserDO = EndUserDO.empty();
        endUserDO.setLoginPassword(PasswordUtil.encryptPassword(registerRequest.getPassword(), endUserDO.getLoginPasswordSalt()));
        endUserDO.setAccount(registerRequest.getAccount());
        endUserRepository.save(endUserDO);
        if(registerCallBack != null) {
        	registerCallBack.registerBack(endUserDO);
        }
        LOGGER.info("手机号注册成功");
	}

	@Override
	@Transactional
	public EndUserVO login(LoginRequest loginRequest) {
		LOGGER.debug("手机号密码登录[loginRequest:{}]", loginRequest);
        //query
        EndUserDO endUserDO = endUserRepository.getByAccountAndStatus(loginRequest.getAccount(), EndUserEnum.Status.ACTIVE);

        //未注册
        if (endUserDO == null) {
            LOGGER.info("手机号密码登录失败，该手机号未注册[loginAccount:{}]", loginRequest.getAccount());
            throw new EndUserException("该手机号未注册");
        }

        //用户名或密码错误
        if (!PasswordUtil.validatePassword(loginRequest.getPassword(), endUserDO.getLoginPasswordSalt(), endUserDO.getLoginPassword())) {
            LOGGER.info("手机号密码登录失败，用户名或密码错误[loginAccount:{}]", loginRequest.getAccount());
            throw new EndUserException("用户名或密码错误");
        }
        
        //修改用户
        endUserDO.setLastLoginTime(new Date());
        endUserRepository.save(endUserDO);
        
        //登录日志
        loginLogService.save(endUserDO.getId(), IpUtils.getIpAddr(ServletContextHolder.getHttpServletRequest()));

        //vo
        //生成token
        String token = createToken(endUserDO);

        //vo
        EndUserVO endUserVO = new EndUserVO();
        endUserVO.setToken(token);

        LOGGER.info("手机号密码登录成功");
        return endUserVO;
	}
	
	@Override
	@Transactional
	public EndUserVO slogin(SLoginRequest sLoginRequest) {
		LOGGER.debug("手机号验证码登录[loginRequest:{}]", sLoginRequest);

		if(!sLoginRequest.getAccount().equals(sLoginRequest.getVerificationCodeCheckRequest().getAccount())) {
            throw new EndUserException("登陆错误！");
		}
        //query
        EndUserDO endUserDO = endUserRepository.getByAccountAndStatus(sLoginRequest.getAccount(), EndUserEnum.Status.ACTIVE);

        //未注册
        if (endUserDO == null) {
            LOGGER.info("手机号验证码登录失败，该手机号未注册[loginAccount:{}]", sLoginRequest.getAccount());
            throw new EndUserException("该手机号未注册");
        }

        //check验证码
		String regexp = "^1(3|4|5|6|7|8|9)\\d{9}$";
		if(Pattern.matches(regexp, sLoginRequest.getAccount())){
			SmsVerificationCodeCheckRequest smsVerificationCodeCheckRequest = BeanConvertUtil.convert(sLoginRequest.getVerificationCodeCheckRequest(), SmsVerificationCodeCheckRequest.class);
	        smsCodeService.checkCode(smsVerificationCodeCheckRequest);
		}else {
			EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest = BeanConvertUtil.convert(sLoginRequest.getVerificationCodeCheckRequest(), EmailVerificationCodeCheckRequest.class);
			emailCodeService.checkCode(emailVerificationCodeCheckRequest);
		}
		
        //修改用户
        endUserDO.setLastLoginTime(new Date());
        endUserRepository.save(endUserDO);
        
        //登录日志
        loginLogService.save(endUserDO.getId(), IpUtils.getIpAddr(ServletContextHolder.getHttpServletRequest()));

        //vo
        //生成token
        String token = createToken(endUserDO);

        //vo
        EndUserVO endUserVO = new EndUserVO();
        endUserVO.setToken(token);

        LOGGER.info("手机号验证码登录成功");
        return endUserVO;
	}
	
	

	private String createToken(EndUserDO endUserDO) {
		LOGGER.debug("开始为用户生成token[endUserId:{}]", endUserDO.getId());
		//生成token
		JwtPayLoad jwtPayLoad = new JwtPayLoad(endUserDO.getId(), endUserDO.getAccount(), endUserDO.getName(), endUserDO.getAvatar(), TokenEnum.TokenSource.END_USER);
		String token = tokenService.generate(jwtPayLoad);
		
		LOGGER.info("为用户生成token成功");
		return token;
	}


	@Override
	public EndUserTokenVO info(String userId) {
		  //通过token获取id
        LOGGER.debug("根据token获取用户信息-service-开始[userId:{}]", userId);

        EndUserDO endUserDO = endUserRepository.getByIdAndStatus(userId, EndUserEnum.Status.ACTIVE);
        if (endUserDO == null) {
            LOGGER.info("根据token获取用户信息，用户数据异常[endUserId:{}]", userId);
            throw new EndUserException( "用户数据异常");
        }
        EndUserTokenVO endUserTokenVO = BeanConvertUtil.convert(endUserDO, EndUserTokenVO.class);
        LOGGER.info("根据token获取用户信息-service-结束[userId:{}]", userId);
        return endUserTokenVO;
	}

	@Override
	@Transactional
	public void forget(ForgetRequest forgetRequest) {
		LOGGER.debug("用户开始重置密码[forgetRequest:{}]", forgetRequest);
        //query
        EndUserDO endUserDO = endUserRepository.getByAccountAndStatus(forgetRequest.getAccount(), EndUserEnum.Status.ACTIVE);
        if (endUserDO == null) {
        	LOGGER.info("重置密码失败，用户不存在[account:{}]", forgetRequest.getAccount());
            throw new EndUserException("用户未注册");
        }
        
        //check验证码
  		String regexp = "^1(3|4|5|6|7|8|9)\\d{9}$";
  		if(Pattern.matches(regexp, forgetRequest.getAccount())){
  			SmsVerificationCodeCheckRequest smsVerificationCodeCheckRequest = BeanConvertUtil.convert(forgetRequest.getVerificationCodeCheckRequest(), SmsVerificationCodeCheckRequest.class);
  	        smsCodeService.checkCode(smsVerificationCodeCheckRequest);
  		}else {
  			EmailVerificationCodeCheckRequest emailVerificationCodeCheckRequest = BeanConvertUtil.convert(forgetRequest.getVerificationCodeCheckRequest(), EmailVerificationCodeCheckRequest.class);
  			emailCodeService.checkCode(emailVerificationCodeCheckRequest);
  		}
      		
        ///重置密码
        endUserDO.setLoginPassword(PasswordUtil.encryptPassword(forgetRequest.getPassword(), endUserDO.getLoginPasswordSalt()));
        LOGGER.info("重置密码成功");
	}

	/**
	 * 分页获取用户列表
	 * author zls
	 * 2020年7月1日
	 */
	@Override
	public PageDataResponse<EndUserInfoVO> getEndUserListByPage(PageQuery pageQuery){
		//规格定义
        Specification<EndUserDO> specification = new Specification<EndUserDO>() {
            /**
			 * author zls
			 */
			private static final long serialVersionUID = -8785309034575411806L;

			/**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<EndUserDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	
                if(StringUtils.isNotBlank(pageQuery.getKey())){ //添加断言
                	Predicate predicate = cb.disjunction();
                	predicate.getExpressions().add(cb.like(root.get("name").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	predicate.getExpressions().add(cb.like(root.get("account").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	predicate.getExpressions().add(cb.like(root.get("phone").as(String.class),"%".concat(pageQuery.getKey()).concat("%")));
                	return predicate;
                }
                return cb.conjunction();
                
            }
        };
        
       Pageable pageable = PageRequest.of(pageQuery.getPageNo(),pageQuery.getPageSize(), Sort.Direction.DESC, "createDate");
        //查询
       Page<EndUserDO> page = this.endUserRepository.findAll(specification,pageable);
       PageInfo pageInfo = new PageInfo();
       pageInfo.setPageNo(page.getNumber());
       pageInfo.setPageSize(page.getSize());
       pageInfo.setTotalCount(page.getTotalElements());
       pageInfo.setTotalPage(page.getTotalPages());
       List<EndUserInfoVO> pageList = BeanConvertUtil.convertList(page.getContent(), EndUserInfoVO.class);
      return new PageDataResponse<>(pageInfo,pageList);
	}
	
	/**
	 * 根据用户ID查询用户信息
	 * author zls
	 * 2020年7月1日
	 */
	@Override
	public EndUserInfoVO getUserByUserId(String userId) {
		EndUserDO entity =  endUserRepository.getOne(userId);
		return BeanConvertUtil.convert(entity, EndUserInfoVO.class);
	}

}
