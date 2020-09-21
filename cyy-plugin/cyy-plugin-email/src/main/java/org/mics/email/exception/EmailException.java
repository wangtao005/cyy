package org.mics.email.exception;

import org.mics.lang.exception.CustomException;

public class EmailException extends CustomException{
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3073350158453490280L;

	public EmailException(String errorMsg) {
		super(errorMsg);
	}
	
	public EmailException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}

}
