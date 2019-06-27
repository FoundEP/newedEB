package com.jt.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;


@RestController
public class JSONPController {
//	@RequestMapping("/web/testJSONP")
//	public String testJSONP(String callback)
//	{
//		User user = new User();
//		user.setId(100);
//		user.setName("孤儿");
//		String json = ObjectMapperUtil.toJSON(user);
//		return callback+"("+json+")";
//	}
	@RequestMapping("/web/testJSONP")
	public JSONPObject testJSONP02(String callback)
	{
		User user = new User();
		user.setId(100);
		user.setName("哦,牛皮");
		JSONPObject obj = new JSONPObject(callback, user);
		return obj;
	}
}
