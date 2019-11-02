package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

@Controller//跳转页面
@RequestMapping("/items")
public class ItemController {
/**
 * 根据页面的url请求,跳转通用的商品展现页面
 */
	@Autowired
	private ItemService itemService;
	@RequestMapping("/{itemId}")
	public String finItemById(@PathVariable Long itemId ,Model model) {
		Item item=itemService.findItemById(itemId);
		ItemDesc desc=itemService.FindItemDescById(itemId);
		model.addAttribute("item", item);//存到域中,在jsp中动态获取数据
		model.addAttribute("itemDesc", desc);
		return "item";//跳转页面
	}
}
