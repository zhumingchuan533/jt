package com.jt.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.dubbo.service.DubboUserService;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
@Service//dubbo包
public class DubboUserServiceImpl implements DubboUserService{
 
	@Autowired
	private UserMapper usermapper;
	@Autowired 
	private JedisCluster jedis;
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
    //用户密码加密 MD5
	@Override
	public void saveUser(User user) {
		//防止eamil为null报错,使用电话号码代替
		user.setEmail(user.getPhone()).setCreated(new Date()).setUpdated(user.getCreated());
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());//加密 无需导包
		user.setPassword(md5Pass).setEmail(user.getPhone()).setCreated(new Date()).setUpdated(user.getCreated());
		       usermapper.insert(user);
	}

	
	/**
	 * 根据用户和密码查询数据库
	 * 结果:没有记录 说明用户和密码错误 return null
	 * 生成ticket加密 userJson串 将数据保存到redis
	 */
	/**
	 * 1.校验用户是否正确
	 * 2.将
	 */
	@Override
	public String doLogin(User user,String ip) {
//	User userDB=	findUserByUP( user);//查询 获取用户对象 密码加密
//	if(userDB!=null) {
//		//生成秘钥
//		//将user对象转化为json串
//		String uuid = UUID.randomUUID().toString();//生成铭文
//		String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());//生成秘钥
//		//敏感数据进行脱敏处理
//		userDB.setPassword("大猪蹄子");
//		String json = ObjectMapperUtil.toJson(userDB);
//		jedis.setex(ticket,7*24*3600 , json);//设置生命周期的set方法
//		return ticket;
//	}
		String md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper =new QueryWrapper<>(user);
		User userOne = usermapper.selectOne(queryWrapper);
		
		if(userOne==null) {
			//说明用户密码不正确
			return null;
		}
		//表示用户信息真确 ,保存ticket/ip/userjson
		String uuid=UUID.randomUUID().toString();//生成铭文
		String ticket = DigestUtils.md5DigestAsHex(uuid.getBytes());//生成秘钥
		//敏感数据进行脱敏处理
		userOne.setPassword("大猪蹄子");
		String userJSON = ObjectMapperUtil.toJson(userOne);
		HashMap<String, String> hash = new HashMap<>();
		hash.put("JT_TICKET", ticket);
		hash.put(ticket, userJSON);
		hash.put("JT_IP", ip);
		jedis.hmset(user.getUsername(), hash);
		jedis.expire(user.getUsername(), 7*24*3600);//设置生命周期
		return ticket;
	}
	/*
	 * public User findUserByUP(User user) { String
	 * md5Pass=DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
	 * user.setPassword(md5Pass); QueryWrapper<User> queryWrapper =new
	 * QueryWrapper<>(user); User selectOne = usermapper.selectOne(queryWrapper);
	 * return selectOne; }
	 */

}
