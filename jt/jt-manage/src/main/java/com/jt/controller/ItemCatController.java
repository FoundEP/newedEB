package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.anno.Cache_find;
import com.jt.enu.KEY_ENUM;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUITree;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	
	//需要获取任意名称的参数,然后为指定的参数赋值
	//@RequestParam获取从客户端发来的值,并在没有发送的时候赋一个默认值;然后这个值赋给parentId
	@RequestMapping("/list")
	@Cache_find(key = "ITEM_CAT",keyType = KEY_ENUM.AUTO)
	public List<EasyUITree> findItemCatByParentId(
			@RequestParam(name="id",defaultValue="0")Long parentId){
		return itemCatService.findItemCatByParentId(parentId);
		//return itemCatService.findItemCatByCache(parentId);
	}
	/**
	 * 用户发起post请求携带了itemCatId
	 * 
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("/queryItemName")
	public String findItemCatNameByCid(Long itemCatId) {
		String name = itemCatService.findItemCatNameByCid(itemCatId);
		return name;
	}
	
	
	
}