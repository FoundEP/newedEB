package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.mapper.FileMapper;
import com.jt.vo.ImageVO;
@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	//本地磁盘路径
	@Value("${image.localDirPath}")
	private String localDirPath;
	//定义虚拟路径名称
	@Value("${image.urlPath}")
	private String urlPath;
	@Autowired
	private FileMapper fileMapper;
	/**
	 * 1获取图片名称
	 * 2校验是否为图片类型
	 * 3校验是否为恶意程序(木马).exe.jpg	 
	 * 4分文件保存,按照时间存储 yyyy/MM/dd
	 * 5防止文件重名 UUID(盐值加密)
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@Override
	public ImageVO updateFile(MultipartFile uploadFile) throws IllegalStateException, IOException {
		ImageVO imageVO = new ImageVO();
		//1获取图片名称
		String fileName = uploadFile.getOriginalFilename();
		//将字符统一转换为小写
		fileName = fileName.toLowerCase();
		//2校验图片类型
		//2.1使用正则表达式判断字符串
		if(!fileName.matches("^.+\\.(jpg|png|jif|jpeg)$"))
		{
			imageVO.setError(1);//表示上传有误
			return imageVO;
		}
		//3判断是否为恶意程序 
		try {
			BufferedImage b = ImageIO.read(uploadFile.getInputStream());
			int width = b.getWidth();
			int height = b.getHeight();
			if(width==0||height==0)
			{
				imageVO.setError(1);
				return imageVO;
			}
			//4分文件保存,按照时间存储,将时间转换为字符串
			String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//准备文件夹
			String  localDir = localDirPath+format+"/";
			File file = new File(localDir);
			if(!file.exists())
			{
				file.mkdirs();
			}
			//使用UUID定义文件名称防止文件重名
			String uuid = UUID.randomUUID().toString().replace("-","");
			//动态获取图片类型
			String substring = fileName.substring(fileName.lastIndexOf("."));
			//拼接文件名称
			String s = localDir+uuid+substring;
			//完成文件上传
			uploadFile.transferTo(new File(s));
			//拼接url路径
			String realUrlPath = urlPath+format+"/"+uuid+substring;
			//文件信息回传
			imageVO.setError(0).setHeight(height).setWidth(width).setUrl(realUrlPath);
		} catch (Exception e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}
		
		return imageVO;
	}
}
