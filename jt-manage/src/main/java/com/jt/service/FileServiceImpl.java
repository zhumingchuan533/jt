package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.Image;
import com.jt.vo.SysResult;
@Service
@PropertySource("classpath:/properties/image.properties")//加载配置文件
public class FileServiceImpl  implements FileService{
	/**
	 * 实现思路:文件上传
	 *   1.校验图片文件格式jpg/jpeg/png/gif..
	 *   2.校验是否为恶意程序
	 *   3.分文件存储  文件 hash=32位~2位  或时间 yy/MM/dd
	 *   4.防止文件重名,自定义文件名称uuid.类型      uuid:全局唯一标识符
	 */
	//定义本地磁盘路径
	@Value("${image.url}")//配置文件赋值
	private  String url;
	//定义虚拟路径地址
	@Value("${image.urlPath}")
	private  String urlPath;
	@Override
	public Image upload(MultipartFile uploadFile) {
		//第一步:获取图片名   
		String fileName=uploadFile.getOriginalFilename();
		fileName=fileName.toLowerCase();//转小写
		//校验 正则表达式  "^.+\\.{jpg|png|jpeg}$"  大小写
		String reg = "^.+(.jpeg|.jpg|.gif|.png)$";
		if(!fileName.matches(reg)) {
			return Image.fail();
		}
		System.out.println("校验成功");
		//校验是否为恶意程序 图片:高度/宽度/px
		try {
			//通过图片io流读取
			BufferedImage bImage=ImageIO.read(uploadFile.getInputStream());
			int width=bImage.getWidth();
			int heigth=bImage.getHeight();

			if(width==0||heigth==0) {
				return Image.fail();
			}
			System.out.println("是图片");
			//实现分文件存储.yyyy/MM/dd
			String dateDir=new SimpleDateFormat("yyyy/MM/dd/").format(new Date());//实现时间拼接路径
			//D:/hah/2019/09/26/
			String dirFilePath=url+dateDir;
			System.out.println(dirFilePath);
			File dirFile = new File(dirFilePath);
			if(!dirFile.exists()) {
				//如果文件不存在,创建文件夹
				dirFile.mkdirs();
			}
			//动态生成文件名称 uuid+文件后缀
			String uuid=UUID.randomUUID().toString();
			//截取文件后缀  从最后一个点截取
			String fileType=fileName.substring(fileName.lastIndexOf("."));
			String realFileName=uuid+fileType;
			System.out.println(realFileName);
			//最后路径
			String realFilePath=dirFilePath+realFileName;
			uploadFile.transferTo(new File(realFilePath));
			//实现数据回显  url:虚拟路径 访问路径
			String httpUrl=urlPath+dateDir+realFileName;
			Image image=new Image(0, httpUrl, width, heigth);
			return image;
		}catch (Exception e) {
			e.printStackTrace();
			return Image.fail();
		}


		
	}

}
