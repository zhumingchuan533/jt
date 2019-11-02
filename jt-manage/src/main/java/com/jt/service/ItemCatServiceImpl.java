package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.annotation.Cache_find;
import com.jt.config.RedisConfig;
import com.jt.mapper.CatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUiTree;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService{
    @Autowired 
	private CatMapper catmapper;
//    @Autowired
    private JedisCluster jedis;
    
	@Override
	public ItemCat findItemCatById(Long itemCatId) {
		ItemCat cat = catmapper.selectById(itemCatId);
		return cat;
	}
	@Override
	@Cache_find
	public List<EasyUiTree> findCatParentId(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("parent_id", parentId);//where条件
		List<ItemCat> c = catmapper.selectList(queryWrapper);
		ArrayList<EasyUiTree> list = new ArrayList<>();
		for (ItemCat c1 : c) {
			//如果是父级closed 否则为open 
			String state = c1.getIsParent()?"closed":"open";
			
			EasyUiTree ea = new EasyUiTree(c1.getId(), c1.getName(), state);
			list.add(ea);
		}
		return list;
		
	}
	/**
	 * 缓存操作流程  垃圾     用AOP
	 * 1.先查询缓存
	 * 2.如果第一次查数据库,并保存到缓存
	 * 3.结果不为null 将json数据转化为对象
	 */
	/*
	 * @Override public List<EasyUiTree> findCach(Long parentId) { String
	 * key="ITEM_CAT"+parentId; List<EasyUiTree> list = new ArrayList<EasyUiTree>();
	 * if(StringUtils.isEmpty(jedis.get(key))) {//判断数据是否为null或""
	 * System.out.println("数据库"); list = findCatParentId(parentId);
	 * jedis.set(key,ObjectMapperUtil.listJson(list) );//数据应该允许覆盖更新 }else {
	 * System.out.println("cach"); String json = jedis.get(key);
	 * list=ObjectMapperUtil.toList(json,list); }
	 * 
	 * return list; }
	 */
	
}
