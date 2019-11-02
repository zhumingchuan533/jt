package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
@Service
public class ItemServiceImpl implements ItemService{
/**
 * web服务器不能直接调用数据库,怎么调用后台服务器去调用数据库呢? 可以利用http请求实现,类似于浏览器的效果,之后获取json数据(API:HttpClient)
 */
	@Autowired
	private HttpClientService httpService;
	@Override
	public Item findItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById";
		Map<String,String> params=new HashMap<>();
		params.put("id", itemId+"");
		
		String data = httpService.doGet(url,params);
		return ObjectMapperUtil.toObject(data, Item.class);
	}
	@Override
	public ItemDesc FindItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById";
		Map<String,String> params=new HashMap<>();
		params.put("id", itemId+"");
		String data = httpService.doGet(url,params);
		return ObjectMapperUtil.toObject(data, ItemDesc.class);
	}

}
