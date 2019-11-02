package com.jt;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClient {
	/**
	 * 1.实例化工具api对象
	 * 2.确定请求url地址
	 * 3.定义请求方式
	 * 4.发起http请求,并且获取数据.
	 * 5.判断吧状态码status是否为200
	 * 6.获取服务器返回值数据
	 * @throws IOException 
	 * @throws ParseException 
	 */
  @Test
  public void testGet() throws ParseException, IOException {
	  CloseableHttpClient client=HttpClients.createDefault();//实例化
	  String url="https://www.baidu.com/";
	  HttpGet get=new HttpGet(url);//HttpPost post=new HttpPost(url)
	  CloseableHttpResponse response = client.execute(get);//发起请求,得到响应结果
	  if(response.getStatusLine().getStatusCode()==200) {//判断状态码
		  String data=EntityUtils.toString(response.getEntity(),"utf-8");//获取数据,转换字符串
		  System.out.println(data);//页面代码
	  }
  }
}
