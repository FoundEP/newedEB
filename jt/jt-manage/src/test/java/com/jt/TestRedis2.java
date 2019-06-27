package com.jt;

import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis2 {
	@Test
	public void testHash()
	{
		Jedis jedis = new Jedis("192.168.21.130",6379);
		jedis.hset("user1", "id", "200");
		jedis.hset("user1", "name", "tom");
		String hget = jedis.hget("user1","name");
		System.out.println(hget);
		Map<String, String> hgetAll = jedis.hgetAll("user1");
		System.out.println(hgetAll);
	}
	
	//编辑list集合
	@Test
	public void testList()
	{
		Jedis jedis = new Jedis("192.168.21.130",6379);
		jedis.lpush("list","1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
	}
	
	//redis事务控制
	@Test
	public void testTransaction()
	{
		Jedis jedis = new Jedis("192.168.21.130",6379);
		Transaction multi = jedis.multi();//开启事务
		try {
			multi.set("aa", "201314");
			multi.set("bb", "131");
			multi.exec();
		} catch (Exception e) {
			multi.discard();//回滚
		}
	}
}
