package org.mics.enduser.exception;

import org.mics.lang.exception.CustomException;

/**
 * 用户异常
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
public class EndUserException extends CustomException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4264183883983028306L;

	public EndUserException(String errorMsg) {
		super(errorMsg);
	}
	
	public EndUserException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public EndUserException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}
	
}
