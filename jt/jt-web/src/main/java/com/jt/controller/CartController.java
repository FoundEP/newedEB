package com.jt.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartservice;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RequestMapping("/cart/")
@Controller
public class CartController {
	
	@Reference(timeout = 3000)
	private DubboCartservice  cartservice;
	
	@Autowired
	private JedisCluster jedisCluster;
	/**
	 * 购物车商品列表展现
	 * @return
	 */
	@RequestMapping("show")
	public String findCartList(Model model,HttpServletRequest request)
	{
		
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = cartservice.findCartListByUserId(userId);
		model.addAttribute("cartList",cartList);
		System.out.println("1111111");
		return "/cart";
	}
	
	/**
	 * 实现购物车数量的修改
	 * url参数中使用restful风格获取数据时,参数名称与对象属性名匹配,则可以使用对象接收数据
	 */
	@RequestMapping("update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNumber(Cart cart,HttpServletRequest request)
	{
		try {
			Long userId = UserThreadLocal.get().getId();
					cart.setUserId(userId);
			cartservice.updateCartNum(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("delete/{itemId}")
	public String deleteCart(Cart cart,HttpServletRequest request)
	{
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartservice.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	@RequestMapping("add/{itemId}")
	public String addCart(Cart cart,HttpServletRequest request)
	{
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cartservice.insertCart(cart);
		return "redirect:/cart/show.html";
	}
	
//	private Long getUserId(HttpServletRequest request)
//	{
//		String token =null;
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie : cookies) {
//			if(cookie.getName().equals("JT_TICKET"))
//			{
//				token = cookie.getValue();
//			}
//		}
//		String string = jedisCluster.get(token);
//		User user = ObjectMapperUtil.toObject(string, User.class);
//		Long userId = user.getId();
//		return userId;
//	}
}
