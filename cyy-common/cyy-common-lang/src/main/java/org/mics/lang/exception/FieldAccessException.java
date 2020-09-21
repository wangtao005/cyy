package org.mics.lang.exception;
/**
 * field取值异常
 * @author mics
 * @date 2019年11月11日
 * @version  1.0
 */
public class FieldAccessException extends CustomException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3013474407439986177L;

	public FieldAccessException(String errorMsg) {
		super(errorMsg);
	}
	
	public FieldAccessException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public FieldAccessException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
