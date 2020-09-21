package org.mics.order.enums;

/**
 * 订单状态
 * @author mics
 * @date 2020年7月21日
 * @version  1.0
 */
public enum OrderState {
	//未支付
	NO_PAY,
	//支付发起成功
	PAY_SEND_SUCCESS,
	//支付发起失败
	PAY_SEND_FAIL,
	//支付最终结果成功
	PAY_FINAL_SUCCESS,
	//支付最终结果失败
	PAY_FINAL_FAIL
}
