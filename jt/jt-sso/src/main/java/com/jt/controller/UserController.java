package com.jt.controller;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
public class UserController {
	
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private UserService userService;
	
	/**
	 * 业务声明
	 * 校验用户是否存在
	 * http://sso.jt.com/user/check/k4125542g/1?r=0.32114579872487825&callback=jsonp1560411022552&_=1560411132631
	 * 返回值:sysresult
	 * 由于跨域请求所以返回值必须处理callback(json)
	 */
	
	@RequestMapping("/user/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,String callback)
	{
		JSONPObject obj = null;
		
		try {
			boolean flag = userService.checkUser(param,type);
			obj = new JSONPObject(callback,SysResult.ok(flag));
		} catch (Exception e) {
			e.printStackTrace();
			obj = new JSONPObject(callback,SysResult.fail());
		}
		
		return obj;
	}
	
	//利用跨域实现用户信息回显
		@RequestMapping("/user/query/{ticket}")
		@ResponseBody
		public JSONPObject finUserByTicket(@PathVariable String ticket,String callback)
		{
			String userJSON = jedisCluster.get(ticket);
			//回传数据需要经过状态码(200)判断sysresult对象
			if(StringUtils.isEmpty(userJSON))
			{
				return new JSONPObject(callback,SysResult.fail());
			}else
			{
				return new JSONPObject(callback,SysResult.ok(ticket, userJSON));
			}
			
		}
		
		
}
