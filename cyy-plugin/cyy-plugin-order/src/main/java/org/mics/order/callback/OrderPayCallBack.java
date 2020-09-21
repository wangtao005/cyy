package org.mics.order.callback;

import org.mics.order.entity.OrderDO;

/**
 * 订单支付回调
 * @author mics
 * @date 2020年9月15日
 * @version  1.0
 */
public interface OrderPayCallBack {
	
	/**
	 * 支付回调
	 * @return
	 */
	OrderDO payNotify(OrderDO orderDO);
}
