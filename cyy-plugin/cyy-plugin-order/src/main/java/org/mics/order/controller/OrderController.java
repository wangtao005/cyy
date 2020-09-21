package org.mics.order.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.mics.core.response.SaveOrUpdateResponse;
import org.mics.lang.net.IpUtils;
import org.mics.order.request.PlaceOrderRequest;
import org.mics.order.service.OrderService;
import org.mics.order.vo.OrderVO;
import org.mics.order.vo.WeixinNativeVO;
import org.mics.token.annotation.IgnoreToken;
import org.mics.token.annotation.LoginUser;
import org.mics.token.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/order")
@Api(tags = "[管理端]-订单")
public class OrderController {
	@Autowired
    private OrderService orderService;
	
	/**
	 * 下单
	 */
	@PostMapping("/placeOrder")
	@ApiOperation(value = "下单",notes = "下单")
	public SaveOrUpdateResponse placeOrder(@Valid PlaceOrderRequest placeOrderRequest,@LoginUser CurrentUser currentUser) {
		String id = orderService.placeOrder(placeOrderRequest,currentUser.getId());
		return new SaveOrUpdateResponse(id);
	}
	
	/**
	 * 下单并支付
	 */
	@PostMapping("/weixinNative")
	@ApiOperation(value = "微信二维码下单并支付",notes = "下单并支付")
	public WeixinNativeVO weixinNative(@Valid PlaceOrderRequest placeOrderRequest,@LoginUser CurrentUser currentUser,HttpServletRequest request) {
		String ip = IpUtils.getIpAddr(request);
		placeOrderRequest.setIp(ip);
		WeixinNativeVO weixinNativeVO = orderService.weixinNative(placeOrderRequest,currentUser.getId());
		return weixinNativeVO;
	}
	
	/**
	 * 支付回调
	 * @param result
	 * @return
	 */
	@IgnoreToken
	@PostMapping("/payCallBack")
	@ApiOperation(value = "支付回调",notes = "支付回调")
	public String payCallBack(@RequestBody String result){
		String callback = orderService.payCallBack(result);
		return callback;
	}
	
	/**
	 * 查询订单
	 * @return
	 */
	@ApiOperation(value = "支付回调",notes = "支付回调")
	@PostMapping("/findOrder")
	public OrderVO findOrder(@Valid @NotBlank(message = "订单id不能为空！") @ApiParam("订单id") String id) {
		return orderService.findByNumber(id);
	}
}
