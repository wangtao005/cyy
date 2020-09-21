package org.mics.order.exception;

import org.mics.lang.exception.CustomException;

public class OrderException extends CustomException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6225665610064166625L;

	public OrderException(String errorMsg){
		super(errorMsg);
	}
	
	public OrderException(int code,String errorMsg){
		super(code,errorMsg);
	}
	
	public OrderException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
}
