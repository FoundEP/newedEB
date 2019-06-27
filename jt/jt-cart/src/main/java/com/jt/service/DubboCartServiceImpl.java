package com.jt.service;

import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartservice
{
	@Autowired
	private CartMapper cartMapper;
	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> selectList = cartMapper.selectList(queryWrapper);
		System.out.println(selectList);
		return selectList;
	}
	
	@Override
	public void updateCartNum(Cart cart) {
		Cart tempCart = new Cart();
		tempCart.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> queryWrapper = new UpdateWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		cartMapper.update(tempCart,queryWrapper);
	}
	
	@Override
	public void deleteCart(Cart cart) {
		
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>(cart);
		
		cartMapper.delete(queryWrapper);
		
	}
	@Override
	public void insertCart(Cart cart) {
		
		QueryWrapper<Cart> qw = new QueryWrapper<>();
		qw.eq("user_id", cart.getUserId()).eq("item_id", cart.getItemId());
		Cart cartDB = cartMapper.selectOne(qw);
		if(cartDB == null)
		{
			cart.setCreated(new Date()).setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
		else
		{
			int num = cart.getNum()+cartDB.getNum();
			cartDB.setNum(num).setUpdated(new Date());
			cartMapper.updateById(cartDB);
		}
		
	}
}
