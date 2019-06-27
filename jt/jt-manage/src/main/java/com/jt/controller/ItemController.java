package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	//查询商品列表信息,分页查询
	@RequestMapping("/query")
	public EasyUIData findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc)
	{
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品修改失败");
		}
	}
	//删除商品
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids)
	{
		try {
			itemService.deleteByIds(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品删除失败");
		}
	}
	
	//商品下架
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids)
	{
		try {
			Integer status = 2;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail("商品上架失败");
		}
	}
	//商品上架
		@RequestMapping("/reshelf")
		public SysResult reshelf(Long[] ids)
		{
			try {
				Integer status = 1;
				itemService.updateStatus(ids,status);
				return SysResult.ok();
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.fail("商品下架失败");
			}
		}
	//根据itemid查询商品详细信息
		@RequestMapping("/query/item/desc/{itemId}")
		public SysResult findItemDescById(@PathVariable Long itemId)
		{
			System.out.println("111111");
			try {
				ItemDesc findItemDescById = itemService.findItemDescById(itemId);
				System.out.println(findItemDescById);
				return SysResult.ok(findItemDescById);
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.fail("查询商品失败");
			}
		}
}
