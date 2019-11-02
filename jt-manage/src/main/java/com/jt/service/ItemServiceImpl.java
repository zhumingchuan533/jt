package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jt.mapper.DescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUiTable;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private DescMapper descMapper;
	
	private ItemDesc desc;

	@Override
	public EasyUiTable findItemByPage(Integer page, Integer rows) {
		//获取商品的记录总数
		Integer r = itemMapper.selectCount(null);
		//获取分页后的数据    select  *  from xxx  limit 起始页,页面记录数
		//                                                 0  ,20
		//                                                 20 , 20
		int start=(page-1)*rows;
		List<Item> itemlist= itemMapper.findItemByPage(start,rows);
		EasyUiTable ui = new EasyUiTable(r,itemlist);
		
		return ui;
	}

	@Override
	@Transactional//控制事务
	public int savaItem(Item item,ItemDesc desc) {
		item.setStatus(1);
		item.setCreated(new Date()).setUpdated(item.getCreated());
		int rows = itemMapper.insert(item);
		//当数据库入库之后才有主键
		//mybatis特性:数据库操作中主键自增之后会自动回填数据
		desc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getUpdated());
		descMapper.insert(desc);
		return rows;
	}

	@Override
	@Transactional
	public int updateItem(Item item,ItemDesc itemdesc) {
		int rows = itemMapper.updateById(item);
		itemdesc.setItemId(item.getId());
		descMapper.updateById(itemdesc);
		return rows;
	}

	@Override
	@Transactional//控制事务
	public int deleteItem(Long...ids) {
		/*
		 * List<Long> idList=Arrays.asList(ids);//数组转集合 int rows =
		 * itemMapper.deleteBatchIds(idList);
		 */
		int rows = itemMapper.deleteItem(ids);
		List<Long> idList=Arrays.asList(ids);
		descMapper.deleteBatchIds(idList);
		return rows;
	
		
	}
    //修改操作一般单独做 尽量避免批量
	@Override
	public void updateItemStatus(int status, Long[] ids) {
		for (Long id : ids) {
			Item item = new Item();
			item.setId(id).setStatus(status).setUpdated(new Date());
			itemMapper.updateById(item);
		}

	}

	@Override
	public ItemDesc FindItemDescById(Long itemId) {
		ItemDesc itemDesc = descMapper.selectById(itemId);
		return itemDesc;
	}

	@Override
	public Item findItemById(Long id) {
		Item data = itemMapper.selectById(id);
		return data;
	}


	
	
	
	
	
	
	
	
}
