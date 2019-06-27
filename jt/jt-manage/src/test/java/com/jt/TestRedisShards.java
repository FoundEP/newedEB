package com.jt;

import java.util.ArrayList;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

public class TestRedisShards {
	/**
	 * 操作时需要将多台redis当做一台使用
	 */
	@Test
	public void testShards()
	{
		ArrayList<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo Info1 = new JedisShardInfo("192.168.21.130",6379);
		JedisShardInfo Info2 = new JedisShardInfo("192.168.21.130",6380);
		JedisShardInfo Info3 = new JedisShardInfo("192.168.21.130",6381);
		shards.add(Info1);
		shards.add(Info2);
		shards.add(Info3);
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("1902","挖了挖了哇");
		System.out.println(shardedJedis.get("1902"));
	}
}
