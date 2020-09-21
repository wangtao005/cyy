package org.mics.enduser.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录vo")
public class EndUserVO  implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1643880137091001889L;
	/**
     * token
     */
    @ApiModelProperty("token")
    private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
    
    
}
