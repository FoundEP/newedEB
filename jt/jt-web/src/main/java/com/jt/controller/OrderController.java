package com.jt.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartservice;
import com.jt.service.DubboOrderService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/order/")
public class OrderController {
	@Reference(timeout = 3000,check = false)
	private DubboOrderService orderService; 
	@Reference(timeout = 3000,check = false)
	private DubboCartservice cartservice;
	
	/**
	 * 跳转订单确认页面
	 * url:"http://www.jt.com/order/create.html"
	 * 页面名称:order-cart.jsp
	 * 页面取值:items=${items}
	 */
	@RequestMapping("create")
	public String orderCreate(Model model)
	{
		System.out.println("1234");
		Long userId = UserThreadLocal.get().getId();
		List<Cart> carts = cartservice.findCartListByUserId(userId);
		model.addAttribute("carts",carts);
		return "/order-cart";
	}
	@RequestMapping("submit")
	@ResponseBody
	public SysResult submitOrder(Order order)
	{
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			String orderId = orderService.insertOrder(order);
			System.out.println(orderId);
			if(!StringUtils.isEmpty(orderId))
			{
				return SysResult.ok(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	
	/**
	 * 根据订单信息查询数据
	 * @return
	 */
	@RequestMapping("success")
	public String findOrderById(String id,Model model)
	{
		Order order = orderService.findOrderById(id);
		model.addAttribute("order",order);
		return "success";
	}
}
