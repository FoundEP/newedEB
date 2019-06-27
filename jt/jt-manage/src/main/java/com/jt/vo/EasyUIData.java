package com.jt.vo;

import java.io.Serializable;
import java.util.List;

import com.jt.pojo.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data 
@Accessors  //链式加载
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIData implements Serializable{
	private static final long serialVersionUID = 1968115217587984105L;
	private Integer total;//记录总数
	private List<Item> rows;//展现数据的集合
}
