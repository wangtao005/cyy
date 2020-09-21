package org.mics.order.vo;

import java.io.Serializable;

import org.mics.order.enums.OrderState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单信息")
public class OrderVO implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1780417355503068342L;

	@ApiModelProperty("订单id")
	private String id;
	
	@ApiModelProperty("订单状态")
	private OrderState state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}
	
	
	
}
