package com.jt;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHttpClient 
{
	@Autowired
	private CloseableHttpClient client;
	@Autowired
	private HttpClientService httpClientService;
	/**
	 * 测试httpclient
	 * 1实例化httpclient对象
	 * 2http请求路径url/uri
	 * 3
	 * 4利用api
	 * 5获取返回值以后判断状态信息 200
	 * 6获取响应数据
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@Test
	public void testGet() throws ClientProtocolException, IOException
	{
		//CloseableHttpClient client = HttpClients.createDefault();
		String url = "https://www.tmooc.cn/";
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse resp = client.execute(httpGet);
		if(resp.getStatusLine().getStatusCode()==200)
		{
			System.out.println("请求成功");
		
		HttpEntity entity = resp.getEntity();
		String s = EntityUtils.toString(entity);
		System.out.println(s);
		}
		else {
			throw new RuntimeException();
		}
	}
	@Test
	public void testUtil()
	{
		String doGet = httpClientService.doGet("http://www.tmooc.cn");
		System.out.println(doGet);
	}
}
