package com.jt.dubbo.service;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Cart;
import com.jt.pojo.Item;

public interface DubboCartService {

	List<Cart> findCartByUserId(Long userId);

	void updateCartNum(Cart cart);

	

	void addCart(Cart cart);

	void deleteCartById(Cart cart);

}
