package org.mics.core.response;

import java.io.Serializable;

import org.mics.core.enums.CommonConstant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础返回类
 * @author mics
 * @date 2020年6月10日
 * @version  1.0
 */
@ApiModel
public class SimpleResponse implements Serializable{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -557129642090828230L;
	/**
     * 业务码
     */
	@ApiModelProperty(value = "业务码")
    private Integer code;
    /**
     * 描述
     */
	 @ApiModelProperty(value = "描述")
    private String desc;
    
    
	public SimpleResponse() {
        this.code = CommonConstant.SUCCESS_CODE;
        this.desc = CommonConstant.SUCCESS_DESC;
    }

    
    public SimpleResponse(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public Integer getCode() {
 		return code;
 	}


 	public void setCode(Integer code) {
 		this.code = code;
 	}


 	public String getDesc() {
 		return desc;
 	}


 	public void setDesc(String desc) {
 		this.desc = desc;
 	}

 	
}
