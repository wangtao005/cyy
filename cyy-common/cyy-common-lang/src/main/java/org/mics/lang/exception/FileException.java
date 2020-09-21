package org.mics.lang.exception;

public class FileException extends CustomException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5370372352137206478L;

	public FileException(String errorMsg) {
		super(errorMsg);
	}
	
	public FileException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public FileException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
