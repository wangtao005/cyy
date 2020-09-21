package org.mics.pay.weixin.exception;

import org.mics.lang.exception.CustomException;

/**
 * 签名异常
 * @author mics
 * @date 2020年7月9日
 * @version  1.0
 */
public class SignException  extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -765385592357481095L;

	

	public SignException(String errorMsg) {
		super(errorMsg);
	}
	
	public SignException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public SignException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}
}
