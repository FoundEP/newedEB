package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;
@Controller//要跳转页面不能用restcontroller
@RequestMapping("/user/")
public class UserController 
{
//	@Autowired
//	private UserService userService;
	
	
	
	//导入dubbo用户接口
	@Reference(timeout = 3000,check = false)
	private DubboUserService userService;
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName)
	{
		return moduleName;
	}
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user)
	{
		System.out.println(user.getUsername()+user.getPassword()+user.getPhone());
		try {
			userService.saveUser(user);
			return  SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return  SysResult.fail("注册失败!");
		}
	}
	
	/**
	 * 实现用户登录
	 * cookie.setmaxage()//设置为0则立即删除,-1会话结束后删除
	 * cookie.setpath("/XXX")设置cookie的权限本域名下的XXX子域名可获取
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response)
	{
		try {
			//调用sso系统获取秘钥
			String token = userService.findUserByup(user);
			//如果数据不为null,将数据保存到cookie中
			//cookie中的key固定值JT_TICKET
			if(!StringUtils.isEmpty(token))
			{
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(7*24*60*60);
				cookie.setDomain("jt.com");//设定二级域名可访问
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("用户名或密码错误");
		}
	}
	@RequestMapping("logout")
	public String dologout(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie1:cookies)
			{
				String token = cookie1.getValue();
				if(token!=null)
				{
					userService.deleteJedis(token);
				}
			}
				Cookie cookie = new Cookie("JT_TICKET", "");
				cookie.setMaxAge(0);
				cookie.setDomain("jt.com");//设定二级域名可访问
				cookie.setPath("/");
				response.addCookie(cookie);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
}
