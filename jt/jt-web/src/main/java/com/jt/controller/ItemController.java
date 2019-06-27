package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 根据商品ID查询后台服务器的数据
	 * 业务步骤
	 * 1在前台service中实现httpclient调用
	 * 2后台根据itemid查询数据库返回对象的json串
	 * 3将json转化为item对象
	 * 4将item对象保存到request域中
	 * 5返回页面逻辑名称item
	 */
	
	@RequestMapping("/items/{itemId}")
	public String findItemById(@PathVariable Long itemId,Model model)
	{
		Item item = itemService.findItemById(itemId);
		ItemDesc itemDesc = itemService.findItemDescById(itemId);
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
	}
	
	
}
