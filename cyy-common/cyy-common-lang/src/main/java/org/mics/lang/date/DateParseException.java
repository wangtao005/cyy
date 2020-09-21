package org.mics.lang.date;

import org.mics.lang.exception.CustomException;

/**
 * 参数异常
 * @author mics
 * @date 2018年7月19日
 * @version 1.0
 */
public final class DateParseException extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2516953136345832443L;
	
    
	public DateParseException(String errorMsg) {
		super(errorMsg);
       
    }
	
	public DateParseException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
    
}
