package com.jt.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.dubbo.service.DubboCartService;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;
@Controller
@RequestMapping("/order")
public class OrderCotroller {
	
	@Reference(check=false)
	private DubboCartService cartService;
	@Reference(check=false)
	private DubboOrderService orderservice;
	/**
	 * 实现订单确认页面跳转
	 * 回显数据:carts
	 * @return
	 */
	@RequestMapping("/create")
	 public  String order(Model model) {
		Long userId = ThreadLocalUtil.get().getId();
		List<Cart> carts= cartService.findCartByUserId(userId);
		model.addAttribute("carts", carts);
		 return "order-cart";
	 }
	/**
	 * 业务:完成订单的入库操作,并且返回oderId
	 * 自己动态生成orderId 用uuid
	 * 同时生成三张表入库  注意事务控制
	 * @param order
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult  saveOrder(Order order) {
		Long userId=ThreadLocalUtil.get().getId();//取值注意是否在同一个线程
		order.setUserId(userId);
		String orderId = orderservice.saveOrder(order);
		return SysResult.success(orderId);
	}
	//根据orderI的查询数据库 三张表 之后展现页面
	/**
	 * 根据orderId查询order对象 存到域中
	 * @param id
	 * @return
	 */
	@RequestMapping("/success")
	public String success(String id, Model model) {
		System.out.println(id);
		Order order=orderservice.findById(id);
		model.addAttribute("order", order);
		return "success";
	}
}
