package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;

public interface OrderMapper extends BaseMapper<Order>{
	Order findOrderById(String id);

}
