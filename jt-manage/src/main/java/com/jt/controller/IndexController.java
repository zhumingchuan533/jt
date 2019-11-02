package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	/**
	 * restFul风格
	 *    语法:
	 *    1.参数必须使用/分割
	 *    2.参数使用{}包裹
	 *    3.只用@PathVariable注解修饰
	 *    规则:
	 *    1.如果参数名称与对象的属性一致是  可以直接使用对象接收
	 *    
	 *    高级应用:
	 *    例子1:查询 UserById/100
	 *    例子2:删除 UserById/100
	 *    url一致怎么实现:
	 *    请求方式7种,常用4种:get:查询操作
	 *    ,post:入库操作
	 *    ,put:更新操作
	 *    ,delete:删除操作
	 * @param moduleName
	 * @return
	 */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	//url:page/100/祝鸣川/18/男
	//@RequestMapping("/page/{id}/{name}/{age}/{sex}")
	//public String  addObject(User user) {}
		//查询操作
	/*
	 * 方法一:@RequestMapping(value="/userById/{id}",method=RequestMethod.GET)
	 * 方法二:@GetMapping("/userById/{id}") 下同
	 *  public xxx findUser(@PathVariable  Integer id) {}
	 *
	 * @RequestMapping(value="/userById/{id}",method=RequestMethod.POST) public xxx
	 * findUser(@PathVariable Integer id) {}
	 * 
	 * @RequestMapping(value="/userById/{id}",method=RequestMethod.PUT) public xxx
	 * findUser(@PathVariable Integer id) {}
	 * 
	 * @RequestMapping(value="/userById/{id}",method=RequestMethod.DELETE) public
	 * xxx findUser(@PathVariable Integer id) {}
	 */
}
