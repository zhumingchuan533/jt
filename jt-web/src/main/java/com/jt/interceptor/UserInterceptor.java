package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.ThreadLocalUtil;

import redis.clients.jedis.JedisCluster;

@Component//交给spring管理
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedis;
	//private static final String USER="JT_USER";
    /**
     * 拦截器:
     *  1.定义拦截器类
     *  2.配置拦截器配置文件
     *  HandlerInterceptor:拦截器接口
     */
	/**
	 * 业务:用户不登录不能访问购物车
	 *     思路:
	 *     1.获取cookie
	 *     2.获取redis
	 */
	//方法执行之前执行: boolean:表示是否放行  true:放行 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket=CookieUtil.getCookieValue(request, "JT_TICKET");//cookieName固定了
		String username = CookieUtil.getCookieValue(request, "JT_USERNAME");
		if(!StringUtils.isEmpty(ticket)) {
			String json = jedis.hget(username, ticket);
			if(!StringUtils.isEmpty(json)) {
				User user = ObjectMapperUtil.toObject(json, User.class);
				//user对象怎么专递到controller
				ThreadLocalUtil.setUser(user);//线程绑定 将数据贯穿整个线程
				return true;
			}
		}
		//一般拦截器中的false和重定向联用 ,应该重定向到登录页面
		response.sendRedirect("/user/login.html");
		return false;//表示拦截
	}
	//方法执行之后执行:
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("方法执行之后");
		
	}
	//方法完成的最后阶段 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//清除 数据
		ThreadLocalUtil.remove();//关闭线程,清空数据
	}
}
