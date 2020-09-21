package org.mics.order.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mics.order.callback.OrderPayCallBack;
import org.mics.order.entity.OrderDO;
import org.mics.order.enums.OrderState;
import org.mics.order.repository.OrderRepository;
import org.mics.pay.weixin.WeiXinPayTemplete;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 查询订单
 * @author mics
 * @date 2020年9月15日
 * @version  1.0
 */
@Component
public class OrderTask {
	@Resource
	private WeiXinPayTemplete weiXinPayTemplete;
	@Resource
	private OrderRepository orderRepository;
	@Resource
	private OrderPayCallBack orderPayCallBack;
	
	/**
	 * 每隔30查询一次订单
	 */
	@Scheduled(fixedRate = 30*1000)
	public void queryOrder(){
		List<OrderDO> orderDos = orderRepository.findByState(OrderState.PAY_SEND_SUCCESS);
		for(OrderDO orderDOTemp:orderDos) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("out_trade_no", orderDOTemp.getOrderNumber());
			Map<String,String> result = weiXinPayTemplete.requestWithoutCert("/pay/orderquery", map, true);
			if("SUCCESS".equals(result.get("return_code"))&& "SUCCESS".equals(result.get("result_code"))) {
				orderDOTemp.setState(OrderState.PAY_FINAL_SUCCESS);
				orderRepository.saveAndFlush(orderDOTemp);
				if(orderPayCallBack != null) {
					orderPayCallBack.payNotify(orderDOTemp);
				}
			}
		}
	}
}
