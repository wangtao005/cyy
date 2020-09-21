package org.mics.lang.file;

import org.mics.lang.exception.CustomException;

/**
 * excel读取异常
 * @author mics
 * @date 2018年7月20日
 * @version 1.0
 */
public class ExcelReadWriteException extends CustomException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3154356658862418511L;

	public ExcelReadWriteException(String errorMsg) {
		super(errorMsg);
	}
	
	public ExcelReadWriteException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public ExcelReadWriteException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}
}
