package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.annotation.Cache_find;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.EasyUiTree;

@RestController
public class ItemCatController {
 @Autowired
 private ItemCatService cat;
 /**
  * 根据商品分类的id查询商品分类名称
  */
 @RequestMapping("/item/cat/queryItemName")
 public String findById(Long itemCatId) {
	 ItemCat itemcat=cat.findItemCatById(itemCatId);
	 return itemcat.getName();
 }
 /**
  * SpringMvc动态接收数据  @RequestParam(value="id",defaultValue="0",required = true)
  * 参数名称:id
  * 目的:id当做parentId使用
  * 要求:初始化时id=0;
  * @RequestParam:
  * 作用:当页面传递的参数与接收参数名不一致时使用
  * 参数介绍:
  * name/value:接收用户提交参数
  * defaultValue:设定默认值
  * required:表示参数是否必传,默认true
  * @param id
  * @return
  */
@RequestMapping("/item/cat/list")
public  List<EasyUiTree> findItemByParentId(@RequestParam(value="id",defaultValue="0")Long parentId){
	
	List<EasyUiTree> list = cat.findCatParentId(parentId);
	//List<EasyUiTree> list=cat.findCach(parentId); //采用AOP
	return list;
}

}
