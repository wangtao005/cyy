package org.mics.push.exception;

import org.mics.lang.exception.CustomException;

/**
 * 认证/授权异常
 * @author mics
 * @date 2020年6月15日
 * @version  1.0
 */
public class PushException extends CustomException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;

	public PushException(String errorMsg) {
		super(errorMsg);
	}
	
	public PushException(int code,String errorMsg){
		super(code,errorMsg);
	}
	
	
	public PushException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public PushException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
