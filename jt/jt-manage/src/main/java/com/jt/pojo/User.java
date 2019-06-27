package com.jt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private Integer id;
	private Integer age;
	private String name;
	private String sex;
	
}
