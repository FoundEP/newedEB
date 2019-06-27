package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_item_desc")
@Data
@Accessors(chain=true)
public class ItemDesc extends BasePojo{
	private static final long serialVersionUID = 3366362064246778732L;
	@TableId   //标识主键
	private Long itemId;
	private String itemDesc;//描述表
}
