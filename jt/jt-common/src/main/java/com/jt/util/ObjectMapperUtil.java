package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
private static final ObjectMapper MAPPER= new ObjectMapper();
//实现对象与JSON之间的相互转换

	//将对象转换为JSON串
	 public static String toJSON(Object target)
	 {
		 String json = null;
		 try {
			 json = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		 return json;
	 }
	 //将JSON转换为对象
	 public static <T> T toObject(String json,Class<T> targetClass)
	 {
		 T target  = null;
		 try {
			target = MAPPER.readValue(json, targetClass);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		 return target;
	 }
}
