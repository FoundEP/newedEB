package com.jt.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

public interface FileService {

	ImageVO updateFile(MultipartFile uploadFile) throws IllegalStateException, IOException;

}
