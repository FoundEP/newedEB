package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	@Autowired
	private ItemCatMapper itemCatMapper;
//	@Autowired
//	private Jedis jedis;
	@Override
	public String findItemCatNameByCid(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}
	/**根据parentId查询数据库记录
	 * 
	 */
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		List<ItemCat> catList = findItemCatList(parentId);
		ArrayList<EasyUITree> treeList = new ArrayList<EasyUITree>();
		for (ItemCat itemCat : catList) {
			EasyUITree uiTree = new EasyUITree();
			uiTree.setId(itemCat.getId());
			uiTree.setText(itemCat.getName());
			String s = itemCat.getIsParent()?"closed":"open";
			uiTree.setState(s);
			treeList.add(uiTree);
		}
		return treeList;
	}
	public List<ItemCat> findItemCatList(Long parentId){
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}
//	@Override
//	public List<EasyUITree> findItemCatByCache(Long parentId) {
//		long t1 = System.currentTimeMillis();
//		String key = "ITEM_CAT_"+parentId;
//		String result = jedis.get(key);
//		List<EasyUITree> list = new ArrayList<>();
//		if(StringUtils.isEmpty(result))//如果为空则查询数据库
//		{
//			list = findItemCatByParentId(parentId);
//			//将数据转换为JSON
//			String json = ObjectMapperUtil.toJSON(list);
//			jedis.setex(key,7*24*60*60,json);
//			System.out.println("数据查询自数据库");
//			long t2 = System.currentTimeMillis();
//			System.out.println(t2-t1);
//		}
//		else {
//			list= ObjectMapperUtil.toObject(result,list.getClass());
//			System.out.println("数据查询自缓存");
//			long t3 = System.currentTimeMillis();
//			System.out.println(t3-t1);
//		}
//		
//		return list;
//	}
}
