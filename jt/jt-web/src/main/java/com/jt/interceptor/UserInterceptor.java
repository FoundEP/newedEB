package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
@Component//将拦截器交给spring容器管理
public class UserInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JedisCluster jedisCluster ;
	/**
	 * spring4版本中必须重写三个方法,在spring5中添加了默认方法,不使用则可以不重写
	 * 
	 * 业务逻辑:
	 * 1获取cookie数据
	 * 2从cookie中获取用户登录凭证token
	 * 判断redis缓存服务器中是否有数据
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName()))
			{
				token = cookie.getValue();
				break;
			}
		}
		//判断token是否有效
		if(!StringUtils.isEmpty(token)) 
		{
			String string = jedisCluster.get(token);
			if(!StringUtils.isEmpty(string))	
			{
				User user = ObjectMapperUtil.toObject(string, User.class);
				//request.setAttribute("JT_USER", user);
				UserThreadLocal.set(user);
				return true;
			}
			
		}
		//重定向到用户登录页面
		response.sendRedirect("/user/login.html");
		return false;//表示拦截
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
