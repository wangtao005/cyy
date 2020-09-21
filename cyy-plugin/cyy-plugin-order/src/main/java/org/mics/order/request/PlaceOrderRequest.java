package org.mics.order.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 下单请求
 * @author mics
 * @date 2020年8月13日
 * @version  1.0
 */
@ApiModel(value = "下单请求")
public class PlaceOrderRequest {

    @NotBlank(message = "商品id不能为空")
    @ApiModelProperty("商品id")
    private String goodsId;
    
    @NotBlank(message = "商品描述不能为空")
    @ApiModelProperty("商品描述")
    private String goodsDescription;
    
    
    @NotBlank(message = "金额不能为空")
    @Pattern(regexp="^([1-9]\\d{0,9}|0)(\\.\\d{1,2})?$")
    @ApiModelProperty("订单金额")
    private String amount;

    private String ip;
    

	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public String getGoodsDescription() {
		return goodsDescription;
	}


	public void setGoodsDescription(String goodsDescription) {
		this.goodsDescription = goodsDescription;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	
	

	

    
   
}
