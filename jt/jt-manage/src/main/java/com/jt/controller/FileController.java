package com.jt.controller;


import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
/**
 * 1获取用户文件信息.包含文件,名称
 * 2执行文件上传的路径 并判断是否存在该路径
 * 3实现文件上传
 * @author tarena
 *
 */

import com.jt.service.FileService;
import com.jt.vo.ImageVO;
@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
//当用户上传完成时重新定向到上传页面
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException
	{
		//获取input标签中的name属性
		String name = fileImage.getName();
		System.out.println("1"+name);
		
		//获取文件名称
		String fileName = fileImage.getOriginalFilename();
		//定义文件夹路径
		File dir = new File("D:/DDD/jt/jt/jt-manage/src/main/webapp/image");
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		//实现文件上传
		fileImage.transferTo(new File("D:/DDD/jt/jt/jt-manage/src/main/webapp/image/"+fileName));
		return "redirect:/file.jsp";
	}
	
	//实现文件上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO uploadFile(MultipartFile uploadFile) throws IllegalStateException, IOException
	{
		return fileService.updateFile(uploadFile);
	}
}
