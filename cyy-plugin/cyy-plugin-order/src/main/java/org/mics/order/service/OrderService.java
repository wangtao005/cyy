package org.mics.order.service;

import org.mics.order.request.PlaceOrderRequest;
import org.mics.order.vo.OrderVO;
import org.mics.order.vo.WeixinNativeVO;

public interface OrderService {
	
	/**
	 * 下单
	 * @param placeOrderRequest 下单参数
	 * @param userId 下单用户
	 * @return 
	 */
	public String placeOrder(PlaceOrderRequest placeOrderRequest,String userId);
	
	/**
	 * 下单并支付
	 * @param placeOrderRequest 下单参数
	 * @param userId  下单用户
	 * @param request 
	 * @return 
	 */
	public WeixinNativeVO weixinNative(PlaceOrderRequest placeOrderRequest,String userId);
	
	/**
	 * 支付回调
	 * @param result
	 */
	public String payCallBack(String result);


	public OrderVO findByNumber(String orderNubmer);

}
