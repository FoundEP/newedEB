package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper; 
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Service(timeout = 3000)//连接超时
public class DubboUserServiceImpl implements DubboUserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public void saveUser(User user) {
		//将密码加密
		String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5).setEmail(user.getPhone()).setUpdated(new Date()).setCreated(new Date());
		userMapper.insert(user);
	}
	
	/**
	 * 校验用户名或密码是否正确
	 * 如果不正确则返回null
	 * 如果正确
	 * 1生成加密秘钥:md5(JT_TICKET+username+当前毫秒数)
	 * 2将userDB数据转化为userJSON
	 * 3将数据保存到redis中设置超时时间7天
	 */
	@Override
	public String findUserByup(User user) {
		String token = null;
		
		//将密码进行加密
		String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		
		User userDB = userMapper.selectOne(queryWrapper);
		
		//判断数据是否正确
		
		if(userDB!=null)
		{
			token = "JT_TICKET"+userDB.getUsername()+System.currentTimeMillis();
			token = DigestUtils.md5DigestAsHex(token.getBytes());
			
			//脱敏处理
			userDB.setPassword("CXKNMSL");
			String json = ObjectMapperUtil.toJSON(userDB);
			//将数据保存到redis中
			jedisCluster.setex(token, 7*24*60*60, json);
		}
		System.out.println("redis信息:"+jedisCluster.get(token));
		return token;
	}
	@Override
	public void deleteJedis(String token) {
		
		jedisCluster.del(token);
	}
}
