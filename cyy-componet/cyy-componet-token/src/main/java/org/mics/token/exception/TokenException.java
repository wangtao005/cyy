package org.mics.token.exception;

import org.mics.lang.exception.CustomException;

/**
 * token异常
 * @author mics
 * @date 2020年5月22日
 * @version  1.0
 */
public class TokenException extends CustomException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3073350158453490280L;

	public TokenException(String errorMsg) {
		super(errorMsg);
	}
	
	public  TokenException(int code,String errorMsg) {
		super(code,errorMsg);
	}
	
	public TokenException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
}
