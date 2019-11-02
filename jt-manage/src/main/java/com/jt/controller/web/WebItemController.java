package com.jt.controller.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;
	/**
	 * 编辑it-manage 完成数据获取
	 * @param id
	 * @return
	 */
	@RequestMapping("/findItemById")
	public Item findItemById(Long id) {
		return itemService.findItemById(id);
	}
	@RequestMapping("/findItemDescById")
	public ItemDesc FindItemDescById(Long id) {
		return itemService.FindItemDescById(id);
	}
	
}
