package com.jt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	@Autowired(required=false)
	private JedisSentinelPool pool;
	//封装方法
	public String get(String key)
	{
		Jedis jedis = pool.getResource();
		String result = jedis.get(key);
		jedis.close();
		return result;
	}
	public void set(String key,String value)
	{
		Jedis jedis = pool.getResource();
		jedis.set(key, value);
		jedis.close();
	}
	public void setex(String key,Integer seconds,String value)
	{
		Jedis jedis = pool.getResource();
		jedis.setex(key, seconds, value);
		jedis.close();
	}
}
