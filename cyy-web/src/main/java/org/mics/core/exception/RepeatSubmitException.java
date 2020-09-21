package org.mics.core.exception;

import org.mics.lang.exception.CustomException;

public class RepeatSubmitException extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1296045485336478728L;

	public RepeatSubmitException(String errorMsg){
		super(errorMsg);
	}
	public RepeatSubmitException(int code, String errorMsg) {
		super(code, errorMsg);
	}
	
	public RepeatSubmitException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}

}
