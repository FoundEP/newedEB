package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	/**
	 * 页面通用请求响应,请求什么返回什么
	 * @param moduleName 请求路径
	 * @PathVariable 把{moduleName}的值赋值给moduleName字符串
	 * @return	返回请求的路径
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
}
