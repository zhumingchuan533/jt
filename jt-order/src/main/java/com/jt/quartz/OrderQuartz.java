package com.jt.quartz;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;
	/**
	 * 目的:每隔一分钟 将超时订单状态修改
	 * 条件:now-cerated>30分钟 
	 * 		状态 satatus=1
	 * sql:
	 * 		update tb_order set status=6,updated=#{date} where status=1 and created<now-30分钟
	 */
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//超时时间
		//获取当前时间日历对象
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -30);//按分钟算 -30分钟
		Date timeout=calendar.getTime();
		Order order=new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("status", 1).lt("created", timeout);
		orderMapper.update(order, updateWrapper);
		System.out.println("成功~~~~~~");
		
	}
}
