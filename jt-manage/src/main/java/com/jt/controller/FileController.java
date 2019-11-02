package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.Image;

@RestController
public class FileController {
	/**
	 * 文件上传成功后返回json数据
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */

	@Autowired
	private FileService fileService;
	@RequestMapping("/file")
	public  String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取图片的名称
		String fileName=fileImage.getOriginalFilename();
		//创建文件对象,指定上传目录
		File dir = new File("D:/hah/");
		if(!dir.exists()) {
			//当文件不存在,创建目录
			dir.mkdirs();

		}
		String path="D:/hah/"+fileName;
		File file=new File(path);
		//输出  本来IO  利用工具api
		fileImage.transferTo(file);
		return "文件上传成功";
	}
	@RequestMapping("/pic/upload")
	public  Image  upload(MultipartFile uploadFile) {
		return fileService.upload(uploadFile);
	}
}
