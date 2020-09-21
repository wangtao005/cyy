package org.mics.lang.exception;

public class ImageException extends CustomException{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6468645902314558856L;

	public ImageException(String errorMsg) {
		super(errorMsg);
	}
	
	public ImageException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public ImageException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
