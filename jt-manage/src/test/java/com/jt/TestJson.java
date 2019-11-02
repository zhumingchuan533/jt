package com.jt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;

public class TestJson {
	private static final ObjectMapper mapper =new ObjectMapper();
	/**
	 * 对象转化为json
	 * @throws IOException 
	 */
	@Test
	public void toJson() throws IOException {
		ItemDesc item=new  ItemDesc();
		item.setItemId(100l).setItemDesc("hah").setCreated(new Date()).setUpdated(item.getCreated());
		String json = mapper.writeValueAsString(item);
		System.out.println(json);

		//将json转为对象
		ItemDesc desc = mapper.readValue(json, ItemDesc.class);
		System.out.println(desc);
	}		
	//集合转json
	@Test
	public void testList() throws Exception {
		ItemDesc item=new  ItemDesc();
		item.setItemId(100l).setItemDesc("hah").setCreated(new Date()).setUpdated(item.getCreated());
		ItemDesc item1=new  ItemDesc();
		item.setItemId(200l).setItemDesc("呵呵").setCreated(new Date()).setUpdated(item.getCreated());
		List list=new ArrayList();
		list.add(item);
		list.add(item1);
		String c = mapper.writeValueAsString(list);
		System.out.println(c);
		List<ItemDesc> l = mapper.readValue(c, list.getClass());
		System.out.println(l);
	}
}
