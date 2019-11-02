package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper usermapper;
    
    //Type为类型，可选参数1 username、2 phone、3 email
	@Override
	public boolean chechUser(String param, Integer type) {
		Map<Integer,String> map=new HashMap<>();
		map.put(1, "username");
		map.put(2,"phone");
		map.put(3, "email");
		String colum=map.get(type);
		QueryWrapper<User> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq(colum, param);
		User user=usermapper.selectOne(queryWrapper);
		return user==null?false:true;
	}
	
}
