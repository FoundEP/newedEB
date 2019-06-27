package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;

@Service
public class DubboOrderServiceImpl implements DubboOrderService {
/**
 * 一个业务逻辑要操作三张数据表
 * 1添加事务控制
 * 2表数据分析 order orderItem orderShipping
 * 3准备orderId'订单号:登录用户ID+当前时间戳'
 * 4操作三个mapper入库
 */
	@Autowired
	private OrderMapper ordermapper;
	@Autowired
	private OrderShippingMapper orderShippingmapper;
	@Autowired
	private OrderItemMapper orderItemmapper;
	
	@Override
	public String insertOrder(Order order) {
		//获取orderId
		String orderId = ""+order.getUserId()+System.currentTimeMillis();
		Date date = new Date();
		//2入库订单
		order.setOrderId(orderId).setStatus(1).setCreated(date).setUpdated(date);
		ordermapper.insert(order);
		System.out.println("订单入库成功");
		
		//入库订单物流
		OrderShipping os = order.getOrderShipping();
		os.setOrderId(orderId).setCreated(date).setUpdated(date);
		orderShippingmapper.insert(os);
		System.out.println("订单物流入库成功");
		
		//入库订单商品
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId).setCreated(date).setUpdated(date);
			orderItemmapper.insert(orderItem);
		}
		System.out.println("订单商品入库成");
		System.out.println(orderId);
		return orderId;
	}
	
	@Override
		public Order findOrderById(String orderId) {
		System.out.println(orderId);
		Order order = ordermapper.selectById(orderId);
		OrderShipping os = orderShippingmapper.selectById(orderId);
		System.out.println(os);
		QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("order_id", orderId);
		List<OrderItem> list = orderItemmapper.selectList(queryWrapper);
		order.setOrderShipping(os);
		order.setOrderItems(list);
		return order;
		}
}
