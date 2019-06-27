package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2810183566580881875L;
	private Integer error;//0表示成功,1表示失败
	private String url;
	private Integer width;
	private Integer height;
}
