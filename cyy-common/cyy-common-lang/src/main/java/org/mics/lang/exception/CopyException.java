package org.mics.lang.exception;

/**
 * 拷贝异常
 * @author mics
 * @date 2020年6月11日
 * @version  1.0
 */
public class CopyException extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -4190975304785844927L;

	public CopyException(String errorMsg) {
		super(errorMsg);
	}
	
	public CopyException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public CopyException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
