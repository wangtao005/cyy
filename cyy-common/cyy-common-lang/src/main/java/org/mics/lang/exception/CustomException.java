package org.mics.lang.exception;


/**
 * 自定义异常
 * @author mics
 * @date 2018年7月19日
 * @version 1.0
 */
public class CustomException extends RuntimeException {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2516953136345832443L;
	 /**
     * 返回错误码
     */
    private int code = 500;
	/**
	 * 错误消息
	 */
	private String errorMsg;
	
    
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		
	}
	
	public CustomException(String errorMsg){
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	
	public CustomException(int code,String errorMsg){
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.code = code;
	}
	
	public CustomException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
}
