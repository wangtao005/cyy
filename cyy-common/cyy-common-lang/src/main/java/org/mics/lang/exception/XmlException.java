package org.mics.lang.exception;

/**
 * xml转化异常
 * @author mics
 * @date 2020年7月9日
 * @version  1.0
 */
public class XmlException  extends CustomException {


	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9023523320320320597L;

	public XmlException(String errorMsg) {
		super(errorMsg);
	}
	
	public XmlException(String errorMsg,Throwable throwable){
		super(errorMsg,throwable);
	}
	
	public XmlException(String errorMsg,Exception exception){
		super(errorMsg,exception);
	}

}
