package org.mics.order.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.mics.lang.bean.BeanConvertUtil;
import org.mics.lang.calculate.RandomUtils;
import org.mics.lang.date.DateUtils;
import org.mics.lang.file.XmlUtils;
import org.mics.log.annotation.OperatorLog;
import org.mics.order.callback.OrderPayCallBack;
import org.mics.order.entity.OrderDO;
import org.mics.order.enums.OrderState;
import org.mics.order.exception.OrderException;
import org.mics.order.repository.OrderRepository;
import org.mics.order.request.PlaceOrderRequest;
import org.mics.order.service.OrderService;
import org.mics.order.vo.OrderVO;
import org.mics.order.vo.WeixinNativeVO;
import org.mics.pay.weixin.SignUtil;
import org.mics.pay.weixin.WeiXinPayTemplete;
import org.mics.pay.weixin.config.WXPayProperties;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderRepository orderRepository;
	@Resource
	private WeiXinPayTemplete weiXinPayTemplete;
	@Resource
	private WXPayProperties wxPayProperties;
	@Resource
	private OrderPayCallBack orderPayCallBack;
	
	@Override
	@Transactional
	public String placeOrder(PlaceOrderRequest placeOrderRequest,String userId) {
		OrderDO orderDO = BeanConvertUtil.convert(placeOrderRequest, OrderDO.class);
		BigDecimal amount = new BigDecimal(placeOrderRequest.getAmount());
		orderDO.setAmount(amount);
		orderDO.setOrderNumber(createOrderNumber());
		orderDO.setState(OrderState.NO_PAY);
		orderDO.setUserId(userId);
		orderRepository.save(orderDO);
		return orderDO.getId();
	}
	
	@Override
	@Transactional
	@OperatorLog(value = "微信二维码支付")
	public WeixinNativeVO weixinNative(PlaceOrderRequest placeOrderRequest, String userId) {
		//保存订单
		OrderDO orderDO = BeanConvertUtil.convert(placeOrderRequest, OrderDO.class);
		orderDO.setOrderNumber(createOrderNumber());
		orderDO.setState(OrderState.NO_PAY);
		orderDO.setUserId(userId);
		BigDecimal amount = new BigDecimal(placeOrderRequest.getAmount());
		orderDO.setAmount(amount);
		orderRepository.saveAndFlush(orderDO);
		
		//统一下单
		Map<String,String> map = new HashMap<String,String>();
		map.put("product_id", orderDO.getGoodsId());
		map.put("out_trade_no", orderDO.getOrderNumber());
		BigDecimal fee = orderDO.getAmount().multiply(new BigDecimal(100));
		fee =  fee.setScale(0);
		map.put("total_fee", String.valueOf(fee));
		map.put("spbill_create_ip", placeOrderRequest.getIp());
		map.put("time_start",DateUtils.getDateYMDS(new Date(), "yyyyMMddHHmmss"));
		
		LocalDateTime currentLocalDateTime = LocalDateTime.now();
	    LocalDateTime nextTime = currentLocalDateTime.plusMinutes(10);
	    ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = nextTime.atZone(zoneId);
        Date date = Date.from(zonedDateTime.toInstant());
		map.put("time_expire",DateUtils.getDateYMDS(date, "yyyyMMddHHmmss"));
		map.put("trade_type", "NATIVE");
		map.put("body", "云实验-充值会员");
		map.put("notify_url", wxPayProperties.getNotifyUrl());

		
		Map<String,String> resultMap = weiXinPayTemplete.requestWithoutCert("/pay/unifiedorder", map,true);
		WeixinNativeVO weixinNativeVO = new WeixinNativeVO();
		weixinNativeVO.setOrderId(orderDO.getId());
		if("SUCCESS".equals(resultMap.get("return_code")) && "SUCCESS".equals(resultMap.get("result_code"))) {
			String codeUrl = resultMap.get("code_url");
			weixinNativeVO.setCodeUrl(codeUrl);
			orderDO.setState(OrderState.PAY_SEND_SUCCESS);
			orderRepository.save(orderDO);
		}else {
			orderDO.setState(OrderState.PAY_SEND_FAIL);
			orderRepository.save(orderDO);
			throw new  OrderException("支付异常");
		}
		return weixinNativeVO;
	}
	
	
	/**
	 * 创建订单号
	 */
	private  String createOrderNumber(){
		String time = String.valueOf(System.currentTimeMillis());
		String random = RandomUtils.randomNumberString(2)  ;
		return time+random;
	}
	
	/**
	 * 支付回调
	 */
	@Transactional
	public String payCallBack(String result) {
		Map<String,String> notifyResult = XmlUtils.xmlToMap(result);
		Map<String,String>  callBack = new HashMap<String,String>();
		if("SUCCESS".equals(notifyResult.get("return_code"))&& "SUCCESS".equals(notifyResult.get("result_code"))) {
			 boolean validate = false;
				try {
					validate = SignUtil.isSignatureValid(notifyResult, wxPayProperties.getKey());
			         if(validate){  
			             //验签成功，进行结算  
			        	 //1、修改订单状态
			        	 OrderDO query = new OrderDO();
			        	 query.setOrderNumber(notifyResult.get("out_trade_no"));
			        	 Optional<OrderDO> orderOptional =  orderRepository.findOne(Example.of(query));
			        	 if(orderOptional.isPresent()) {
			        		 OrderDO orderDO = orderOptional.get();
			        		 if(orderDO != null) {
			        			 orderDO.setState(OrderState.PAY_FINAL_SUCCESS);
			        			 orderRepository.saveAndFlush(orderDO);
			        			 if(orderPayCallBack != null) {
				        			 orderPayCallBack.payNotify(orderDO);
			        			 }
			        			 callBack.put("return_code", "SUCCESS");
			        			 callBack.put("return_msg", "OK");
			        		 }else {
			        			 callBack.put("return_code", "FAIL");
			        			 callBack.put("return_msg", "参数格式校验错误:订单号不存在");
			        		 }
			        	 }
			         }else {
			        	 callBack.put("return_code", "FAIL");
	        			 callBack.put("return_msg", "签名失败");
			         }  		
				} catch (Exception e) {
					callBack.put("return_code", "FAIL");
       			 	callBack.put("return_msg", "验签算法错误");
       			 	throw new OrderException("验签算法错误");
				}  
		}else {
			callBack.put("return_code", "FAIL");
			callBack.put("return_msg", "未知错误");
		}
		return XmlUtils.mapToXmlStr(callBack);
	}

	@Override
	public OrderVO findByNumber(String orderId) {
		OrderDO orderDO =  orderRepository.getOne(orderId);
		return BeanConvertUtil.convert(orderDO, OrderVO.class);
	}
	
}
