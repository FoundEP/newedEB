package com.jt.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private HttpClientService httpclient;
	
	@Override
	public Item findItemById(Long itemId) {
		System.out.println(itemId);
		String url = "http://manage.jt.com/web/item/findItemById";
		HashMap<String, String> params = new HashMap<>();
		params.put("id", itemId+"");
		String result = httpclient.doGet(url,params);
		Item object = ObjectMapperUtil.toObject(result, Item.class);
		System.out.println(object);
		return object;
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url = "http://manage.jt.com/web/item/findItemDescById";
		HashMap<String, String> params = new HashMap<>();
		params.put("id", itemId+"");
		String result = httpclient.doGet(url,params);
		ItemDesc object = ObjectMapperUtil.toObject(result, ItemDesc.class);
		return object;
	}
}
