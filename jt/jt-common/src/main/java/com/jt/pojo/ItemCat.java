package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("tb_item_cat")
@Accessors(chain=true)
public class ItemCat extends BasePojo{
	private static final long serialVersionUID = 5611227002505394489L;
	@TableId(type = IdType.AUTO)
	private Long id;
	private Long parent_id;
	private String name;
	private Integer status;
	private Integer sortOrder;
	private Boolean isParent; 

}
