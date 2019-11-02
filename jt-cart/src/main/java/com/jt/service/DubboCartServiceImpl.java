package com.jt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.dubbo.service.DubboCartService;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.pojo.Item;

@Service
public class DubboCartServiceImpl implements DubboCartService {
    @Autowired
	private CartMapper cartmapper;

	@Override
	public List<Cart> findCartByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> list = cartmapper.selectList(queryWrapper);
		return list;
	}
   /**
    * sql: update tb_cart set num=#{num},updated=#{updated}
    * where user_id=#{userId} and item_id=#{itemId};
    * mybatis plus 修改方法 第一个参数:要修改的数据  第二个参数:修改的条件
    */
	@Override
	public void updateCartNum(Cart cart) {
		Cart cart1=new Cart();
		cart1.setNum(cart.getNum()).setUpdated(new Date());
		UpdateWrapper<Cart> uw = new UpdateWrapper<>();
		
		uw.eq("item_id", cart.getItemId()).eq("user_id", cart.getUserId());
		cartmapper.update(cart1, uw);
		
	}
	/**
	 * 添加商品:
	 *   根据商品id查询商品表添加数据
	 *   1.购物车是否有记录
	 *     null:新增
	 *     !null:更新num
	 */
@Override
public void addCart(Cart cart) {
	QueryWrapper< Cart> queryWrapper=new QueryWrapper<>();
	queryWrapper.eq("user_id", cart.getUserId());
	queryWrapper.eq("item_id", cart.getItemId());
	Cart cart1 = cartmapper.selectOne(queryWrapper);
	if(cart1==null) {
		//购物侧没有记录 新增
		cartmapper.insert(cart);
	}else {
		//购物车有记录 更新 num
		int num=cart1.getNum()+cart.getNum();
		cart1.setNum(num).setUpdated(new Date());
		//更新 num  updated
		
		cartmapper.updateById(cart1);
	}
	
	
}
	@Override
	public void deleteCartById(Cart cart) {
		
		QueryWrapper< Cart> queryWrapper=new QueryWrapper<>(cart);
		
		cartmapper.delete(queryWrapper);
		
	}

	

}
