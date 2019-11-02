package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
@RestController
public class WebJSONPController {
 /**
  * 要求返回值结果callback(json)
  */
	@RequestMapping("/web/testJSONP")
	public JSONPObject jsonp(String callback) {
		ItemDesc desc=new ItemDesc();
		desc.setItemId(100l).setItemDesc("hahah");
		return new JSONPObject(callback, desc);
	}
	/*
	 * @RequestMapping("/web/testJSONP") public String jsonp(String callback) {
	 * ItemDesc desc=new ItemDesc(); desc.setItemId(100l).setItemDesc("hahah");
	 * String json = ObjectMapperUtil.toJson(desc); return callback+"("+json+")"; }
	 */
}
