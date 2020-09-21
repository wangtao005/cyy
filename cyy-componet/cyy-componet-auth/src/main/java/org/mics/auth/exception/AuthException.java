package org.mics.auth.exception;

import org.mics.lang.exception.CustomException;

/**
 * 认证/授权异常
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public class AuthException extends CustomException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public AuthException(String errorMsg) {
		super(errorMsg);
	}
	
	public AuthException(int code,String errorMsg){
		super(code,errorMsg);
	}
	
	
	public AuthException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public AuthException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
