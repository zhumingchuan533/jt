package com.jt.dubbo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.jt.pojo.User;




public interface DubboUserService {
List<User> findAll();
	
	@Transactional
	void saveUser(User  user);
	
	
	
	String doLogin(User user,String ip);


}
