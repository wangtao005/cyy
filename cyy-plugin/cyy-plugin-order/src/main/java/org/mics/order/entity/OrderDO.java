package org.mics.order.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mics.jpa.entity.BaseEntity;
import org.mics.order.enums.OrderState;

/**
 * 订单
 * @author mics
 * @date 2020年7月21日
 * @version  1.0
 */
@Entity
@Table(name="t_order") 
public class OrderDO extends BaseEntity{

	/**
	 * 订单号
	 */
	@Column(name = "order_number", columnDefinition ="varchar(32) comment '订单号'",nullable = false,unique = true) 
	private String orderNumber;
	
	/**
	 * 商品id
	 */
	@Column(name = "goods_id", columnDefinition ="varchar(32) comment '商品id'",nullable = true)
	private String goodsId;
	
	/**
	 * 商品描述
	 */
	@Column(name = "goods_description", columnDefinition ="varchar(32) comment '商品描述'",nullable = true)
	private String goodsDescription;
	
	/**
	 * 订单状态
	 */
    @Column(name = "state", columnDefinition = "int(1) comment '支付状态(0:未支付;1:支付发起成功,2:支付发起失败,3:支付最终成功,4:支付最终失败)'")
	private OrderState state;
	
    /**
     * 订单金额
     */
	@Column(name = "amount", columnDefinition ="decimal(16,2) comment '订单金额'",nullable = true)
    private BigDecimal amount;
	
	/**
	 * 合单id
	 */
	@Column(name = "combine_order_number", columnDefinition ="varchar(32) comment '合单号'") 
	private String combineOrderNumber;
	
	/**
	 * 用户id
	 */
	@Column(name = "user_id", columnDefinition ="varchar(32) comment '用户id'",nullable = true)
	private String userId;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCombineOrderNumber() {
		return combineOrderNumber;
	}

	public void setCombineOrderNumber(String combineOrderNumber) {
		this.combineOrderNumber = combineOrderNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
