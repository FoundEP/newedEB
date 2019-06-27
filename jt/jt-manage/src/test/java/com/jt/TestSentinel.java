package com.jt;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestSentinel {
	//测试哨兵
	@Test
	public void test01()
	{
		//mastername代表主机额变量名称
		//sentinel set<string> ip:端口
		HashSet<String> sentinels = new HashSet<>();
		sentinels.add("192.168.21.130:26379");
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = pool.getResource();
		jedis.set("cc", "超星空你们谁来");
		jedis.close();
	}
}
