package com.jt.service;

import java.util.List;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUiTable;

public interface ItemService {

	EasyUiTable  findItemByPage(Integer page, Integer rows);

	int savaItem(Item item, ItemDesc desc);

	int updateItem(Item item, ItemDesc itemdesc);

	int deleteItem(Long...ids);

	void updateItemStatus(int status, Long[] ids);

	ItemDesc FindItemDescById(Long itemId);

	Item findItemById(Long id);

	
	
}
