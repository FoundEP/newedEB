package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.util.ShardInfo;

//表示redis配置类
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
//	@Value("${jedis.host}")
//	private String host;
//	@Value("${jedis.port}")
//	private Integer port;
//	@Bean
//	public Jedis jedis()
//	{
//		return new Jedis(host,port);
//	}
	
//	@Bean
//	public ShardedJedis shardedJedis()
//	{
//		ArrayList<JedisShardInfo> shards = new ArrayList<>();
//		String[] nodes = redisNodes.split(",");
//		for (String node : nodes) {
//			String host = node.split(":")[0];
//			int port = Integer.parseInt(node.split(":")[1]);
//			JedisShardInfo info = new JedisShardInfo(host,port);
//			shards.add(info);
//		}
//		
//		return new ShardedJedis(shards);
//	}
//	@Value("${redis.nodes}")
//	private String redisNodes;
//	@Value("${redis.sentinels}")
//	private String jedisSentinelNodes;
//	@Value("${redis.sentinel.masterName}")
//	private String masterName;
//	@Bean
//	public JedisSentinelPool jedisSentinelPool()
//	{
//		HashSet<String> sentinels = new HashSet<>();
//		sentinels.add(jedisSentinelNodes);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public JedisCluster jedisCluster()
	{
		HashSet<HostAndPort> nodes = new HashSet<>();
		String[] node = redisNodes.split(",");
		for (String nod : node) {
			String host = nod.split(":")[0];
			int port = Integer.parseInt(nod.split(":")[1]);
			HostAndPort hp = new HostAndPort(host, port);
			nodes.add(hp);
		}
		JedisCluster cluster = new JedisCluster(nodes);
		return cluster;
	}
}
