package org.mics.core.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 返回数据列表对象
 * 
 * @author mics
 * @date 2020年5月19日
 * @version 1.0
 */
public class MultipleDataResponse<T> extends SimpleResponse {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -429588641645706683L;
	/**
	 * 列表数据
	 */
	@ApiModelProperty(value = "列表数据")
	private List<T> data;

	public MultipleDataResponse(List<T> data) {
        super();
        this.data = data;
	}

	public MultipleDataResponse(Integer code, String desc, List<T> data) {
        super(code, desc);
        this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	
}
