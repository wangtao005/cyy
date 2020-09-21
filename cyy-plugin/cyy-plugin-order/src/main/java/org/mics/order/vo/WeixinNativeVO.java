package org.mics.order.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("下单支付信息")
public class WeixinNativeVO implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1066860935261122739L;
	
	@ApiModelProperty("订单id")
	private String orderId;
	
	@ApiModelProperty("二维码")
	private String codeUrl;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	
}
