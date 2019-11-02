package com.jt.util;

import com.jt.pojo.User;

public class ThreadLocalUtil {
//拦截器 线程绑定数据
	private static ThreadLocal<User> thread=new ThreadLocal<>();
	
	public  static void setUser(User user) {
		
		thread .set(user);
	}
	public  static User get() {
		return thread.get();
	}
	//内存泄漏问题 
	public  static void remove() {
		thread.remove();
	}
}
