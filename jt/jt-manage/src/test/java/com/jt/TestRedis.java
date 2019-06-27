package com.jt;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

import lombok.Data;
import redis.clients.jedis.Jedis;


public class TestRedis {
	//测试redis
	@Test
	public void testString()
	{
		Jedis jedis = new Jedis("192.168.21.130",6379);
		jedis.set("1902","不行快给我纸");
		System.out.println(jedis.get("1902"));
	}
	//测试设定数据超时方
	//分布式锁
	@Test
	public void testTimeout() throws InterruptedException
	{
		Jedis jedis = new Jedis("192.168.21.130",6379);
		jedis.setex("aa", 10, "aa");//设定超时时间
		System.out.println(jedis.get("aa"));
		Thread.sleep(11000);
		Long s = jedis.setnx("aa", "bb");//对操作本身加锁,当key不存在时操作政策,存在时则操作失败
		System.out.println("获取输出的数据:"+s+jedis.get("aa"));
	}
	
	//实现对象转换为json
	@Test
	public void objectToJOSN() throws IOException
	{
		ItemDesc it = new ItemDesc();
		it.setItemId(100l).setItemDesc("数据测试");
		ObjectMapper om = new ObjectMapper();
		//转换为json时必须有get/set方法
		String s = om.writeValueAsString(it);
		System.out.println(s);
		
		//将json串转换为对象
		ItemDesc readValue = om.readValue(s, ItemDesc.class);
		System.out.println(readValue);
	}
	//实现list集合与json转换
	@Test
	public void listToJOSN() throws IOException
	{
		ItemDesc it1 = new ItemDesc();
		it1.setItemId(100l).setItemDesc("数据测试1");
		ItemDesc it2 = new ItemDesc();
		it2.setItemId(100l).setItemDesc("数据测试2");
		ArrayList<Object> list = new ArrayList<>();
		list.add(it1);
		list.add(it2);
		ObjectMapper om = new ObjectMapper();
		//转换为json时必须有get/set方法
		String s = om.writeValueAsString(list);
		System.out.println(s);
		
		//将数据保存到redis
		Jedis jedis = new Jedis("192.168.21.130",6379);
		jedis.set("itemDescList", s);
		//从redis中获取数据
		String string = jedis.get("itemDescList");
		ArrayList readValue = om.readValue(string, list.getClass());
		System.out.println("读取数据:"+readValue);
	}
	/**
	 * 利用Redis保存业务数据,数据库
	 * 数据库数据:对象
	 * String类型要求只能存储字符串类型,需要将item转换为字符串(JSON)
	 */
//	@Test
//	public void testSetObject()
//	{
//		Item item = new Item();
//		item.setId(100l).setTitle("数据测试");
//		Jedis jedis = new Jedis("192.168.21.130",6379);
//		new 
//		jedis.set("item",);
//		System.out.println(jedis.get("1902"));
//	}
	@Test
	public void userToJson() throws JsonProcessingException
	{
		ObjectMapper map= new ObjectMapper();
		User user = new User();
		user.setAge(18);
		user.setId(1);
		user.setName("小心心");
		user.setSex("男");
		String s = map.writeValueAsString(user);
		System.out.println(s);
	}
}
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class User
{
	private Integer id;
	private Integer age;
	private String name;
	private String sex;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", name=" + name + ", sex=" + sex + "]";
	}
	
	
}
