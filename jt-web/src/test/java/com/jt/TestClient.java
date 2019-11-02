package com.jt;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClient {
 @Autowired
 private  HttpClientService service;
 
 @Test
 public  void testService() {
	 String  url="http://www.baidu.com";
	 Map<String, String> map = new HashMap<>();
	 map.put("id", "12");
	 map.put("name", "李知恩");
	 String data=service.doGet(url, map, null);
	 System.out.println(data);
 }
}
