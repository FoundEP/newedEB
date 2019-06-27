package com.jt;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestJedisCluster {
	@Test
	public void test01()
	{
		HashSet<HostAndPort> sets = new HashSet<>();
		sets.add(new HostAndPort("192.168.21.130",7000));
		sets.add(new HostAndPort("192.168.21.130",7001));
		sets.add(new HostAndPort("192.168.21.130",7002));
		sets.add(new HostAndPort("192.168.21.130",7003));
		sets.add(new HostAndPort("192.168.21.130",7004));
		sets.add(new HostAndPort("192.168.21.130",7005));
		JedisCluster cluster = new JedisCluster(sets);
		cluster.set("1902", "集群搭建完成");
		System.out.println(cluster.get("1902"));
	}
}
