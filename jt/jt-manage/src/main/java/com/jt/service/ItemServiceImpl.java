package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		//查询总记录
		Integer total = itemMapper.selectCount(null);
		/*分页之后回传数据
		 * sql:select * from tb_item limit 起始位置.查询个数
		 * 第一页:select * from tb_item limit 0,20
		 * 第二页:select * from tb_item limit 20,20
		 * 第三页:select * from tb_item limit 40,20
		 * ....
		 * 第page页:select * from tb_item limit (page-1)*rows, rows
		 */
		//计算起始位置
		int start = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		System.out.println(itemList);
		return new EasyUIData(total,itemList);
	}

	@Transactional //添加事务的控制
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);
		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
		
	}
	//根据主键更新
	/**
	 * propagation 事务传播属性
	 * 						默认值required 默认值 必须添加事务  更新时事务合并
	 * 							requires_new必须新建事务
	 * 							supports表示事务支持的,之前有事务时合并事务
	 * spring中默认的事务控制策略
	 * 1检查异常/编译异常       		不浮躁事务控制
	 * 2运行时异常				回滚事务
	 * rollbackfor = "包名.类名.名称.class(异常的类型)" 按照指定的异常回滚事务 
	 * norollbackfor 遇到指定异常不回滚 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		// TODO Auto-generated method stub
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//同时修改2张表数据
		itemDesc.setItemId(item.getId());
		itemDescMapper.updateById(itemDesc);
	}
	
	@Override//mapper中的三大参数类型:数组,list集合,map集合
	public void deleteByIds(Long[] ids) {
//		for (Long id : ids) {
//			itemMapper.deleteById(id);
//		}	
		
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);
	}
	
	@Override
	public void updateStatus(Long[] ids, Integer status) {
		Item item = new Item();
		item.setStatus(status).setUpdated(new Date());
		List<Long> asList = Arrays.asList(ids);
		QueryWrapper<Item> queryWrapper = new QueryWrapper<Item>();
		queryWrapper.in("id", asList);
		itemMapper.update(item,queryWrapper);
		
	}
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc selectById = itemDescMapper.selectById(itemId);
		return selectById;
	}
	
	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}
}
