package com.tedu.sp09.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tedu.sp01.pojo.Item;
import com.tedu.web.util.JsonResult;

@FeignClient(name="item-service", fallback = ItemFeignServiceFB.class)
public interface ItemFeignService {
	
	/**
	 * 利用springMVC注解来反向生成访问路径
	 * 
	 * 根据mapping中指定的路径在主机地址后面拼接路径
	 * http://localhost:8001/{orderId}
	 * 根据@PathVariable配置把参数数据拼接到路径当中,假设参数是123
	 * http://localhost:8001/123
	 * 向拼接的路径来转发调用
	 * @param orderId
	 * @return
	 */
	@GetMapping("/{orderId}")
	JsonResult<List<Item>> getItems(@PathVariable String orderId);

	@PostMapping("/decreaseNumber")
	JsonResult decreaseNumber(@RequestBody List<Item> items);
}