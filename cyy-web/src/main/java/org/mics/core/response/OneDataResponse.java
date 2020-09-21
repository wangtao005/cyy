package org.mics.core.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回包含一个数据的响应对象
 * @author mics
 * @date 2020年5月19日
 * @version  1.0
 */
public class OneDataResponse<T> extends SimpleResponse{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6476668381520193921L;
	/**
	 * 对象信息
	 */
	@ApiModelProperty(value = "对象信息")
    private T data;
    
    public OneDataResponse(T data) {
        super();
        this.data = data;
    }

    public OneDataResponse(Integer code, String desc, T data) {
        super(code, desc);
        this.data = data;
    }

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
}
