package com.jt.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.mapper.DescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUiTable;
import com.jt.vo.SysResult;

@RestController  //返回值json
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private  DescMapper desc;
	
	@RequestMapping("/query")
	public EasyUiTable  findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
    /**	
     * 业务需求:
     * url:/item/save
         *   参数:
         *   返回值:  sysRseult成功200 失败201
     */
	@RequestMapping("/save")
    public SysResult savaItem(Item item ,ItemDesc desc) {
    	//try {  //如果不是一定要加try    改写为全局异常处理机制
    		itemService.savaItem(item,desc);
    		return SysResult.success();
		//} catch (Exception e) {
		//	e.printStackTrace();
          //   return SysResult.fail();
		//}
    }
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemdesc) {
		item.setUpdated(new Date());
		itemdesc.setUpdated(item.getUpdated());
		itemService.updateItem(item ,itemdesc);
		return SysResult.success();
	}
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {
		
		
		itemService.deleteItem(ids);
		
		return SysResult.success();
	}
	/**
	 * 下架:
	 * @param ids
	 * @return
	 */
	@RequestMapping("/instock")
	public SysResult instockItem(Long[] ids) {
		int status=2;
		itemService.updateItemStatus(status,ids);
		return SysResult.success();
	}
	@RequestMapping("/reshelf")
	public SysResult reshelfItem(Long[] ids) {
		int status=1;
		itemService.updateItemStatus(status,ids);
		return SysResult.success();
	}
	@RequestMapping("/query/item/desc/{itemId}")
	public  SysResult findDesc(@PathVariable Long itemId) {
		ItemDesc itemDesc=itemService.FindItemDescById(itemId);
		return SysResult.success(itemDesc);
	}
	
}
