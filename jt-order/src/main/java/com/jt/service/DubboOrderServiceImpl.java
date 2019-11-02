package com.jt.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.dubbo.service.DubboOrderService;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
@Service
public class DubboOrderServiceImpl implements DubboOrderService{
    @Autowired
	private OrderMapper ordermapper;
    @Autowired
    private OrderItemMapper orderitemmapper;
    @Autowired
    private OrderShippingMapper ordershippingmapper;

	/**
	 *  业务:完成订单的入库操作,并且返回oderId
	 * 自己动态生成orderId 用uuid
	 * 同时生成三张表入库  注意事务控制
	 * status:1.未付款 2.已付款 3.
	 * 订单号:登录用户id+时间戳
	 */
	@Override
	@Transactional//控制事务
	public String saveOrder(Order order) {
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		order.setOrderId(orderId).setStatus(1).setCreated(new  Date()).setUpdated(order.getCreated());
		ordermapper.insert(order);
		System.out.println("成功");
		OrderShipping orderShipping=order.getOrderShipping();
		orderShipping.setOrderId(orderId).setCreated(new Date()).setUpdated(orderShipping.getCreated());
		ordershippingmapper.insert(orderShipping);
		System.out.println("成功");
		List<OrderItem> orderItems=order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderId).setCreated(new Date()).setUpdated(orderItem.getCreated());
			orderitemmapper.insert(orderItem);
		}
		System.out.println("成功");
		return orderId;
	}
    /**
     * 1.利用mybatis-plus方式来查询数据
     * 2.利用sql语句关联查询 3张表
     */
	@Override
	public Order findById(String id) {
		/*效率不高
		 * Order order = ordermapper.selectById(id); System.out.println(order);
		 * QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
		 * queryWrapper.eq("order_id", id); List<OrderItem> orderitems =
		 * orderitemmapper.selectList(queryWrapper); order.setOrderItems(orderitems);
		 * QueryWrapper<OrderShipping> queryWrapper1 = new QueryWrapper<>();
		 * queryWrapper1.eq("order_id", id); OrderShipping orderShipping =
		 * ordershippingmapper.selectOne(queryWrapper1);
		 * order.setOrderShipping(orderShipping); System.out.println(order);
		 */
		
		return ordermapper.findOrderById( id);
	}
    
}
