package org.mics.file.exception;

import org.mics.lang.exception.CustomException;

/**
 * 上传文件异常
 * @author mics
 * @date 2020年7月2日
 * @version  1.0
 */
public class FileUploadException extends CustomException{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 5370372352137206478L;

	public FileUploadException(String errorMsg) {
		super(errorMsg);
	}
	
	public FileUploadException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public FileUploadException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}
}
