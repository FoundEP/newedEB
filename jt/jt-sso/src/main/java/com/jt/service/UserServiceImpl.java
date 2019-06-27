package com.jt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	/**
	 * true表示用户已存在,false表示可以使用
	 * param用户参数
	 * type表示用户查询的字段/1 username/2 phone/3 email
	 * 将type转化为具体字段
	 */
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public boolean checkUser(String param, Integer type) {
	
		String column = type==1?"username":(type==2?"phone":"email");
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, param);
		int count = userMapper.selectCount(queryWrapper);
		
		return count==1;
	}
	
}
