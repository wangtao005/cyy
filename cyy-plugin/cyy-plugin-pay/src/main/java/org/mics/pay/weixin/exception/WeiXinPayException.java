package org.mics.pay.weixin.exception;

import org.mics.lang.exception.CustomException;

/**
 * 支付异常
 * @author mics
 * @date 2020年7月10日
 * @version  1.0
 */
public class WeiXinPayException  extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -765385592357481095L;

	

	public WeiXinPayException(String errorMsg) {
		super(errorMsg);
	}
	
	public WeiXinPayException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public WeiXinPayException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}
}
