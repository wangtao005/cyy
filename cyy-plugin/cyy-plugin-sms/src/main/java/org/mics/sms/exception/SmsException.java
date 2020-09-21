package org.mics.sms.exception;

import org.mics.lang.exception.CustomException;

public class SmsException extends CustomException{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3073350158453490280L;

	public SmsException(String errorMsg) {
		super(errorMsg);
	}
	
	public SmsException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	

}
