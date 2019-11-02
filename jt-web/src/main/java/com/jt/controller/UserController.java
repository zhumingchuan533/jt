package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboUserService;
import com.jt.pojo.User;
import com.jt.util.CookieUtil;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	/// user/doRegister
	@Reference(check = false) // 不需要校驗
	private DubboUserService userService;
	private static final String TICKET = "JT_TICKET";
	@Autowired
    private JedisCluster jedis;
	// 注册:http://www.jt.com/user/register.html
	// 登录:http://www.jt.com/user/login.html
	@RequestMapping("{moduleName}")
	public String module(@PathVariable String moduleName) {

		return moduleName;
	}

	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		userService.saveUser(user);
		return SysResult.success();
	}

	/*
	 * /user/doRegister coo.setMaxAge(>0);存活生命周期(s) setMaxAge(0)立即删除
	 * setMaxAge(-1)当回话关闭时删除cookie
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user, HttpServletRequest request,HttpServletResponse response) {
		/*
		 * String ticket = userService.doLogin(user); if (StringUtils.isEmpty(ticket)) {
		 * // null 表示返回值不正确,给提示 return SysResult.fail("未注册或用户名或密码错误"); } //
		 * 将ticket保存到客户端的cookie Cookie coo = new Cookie(TICKET, ticket);
		 * coo.setMaxAge(60 * 60 * 24 * 7);// 七天有效 coo.setPath("/");// COOKIE权限设定(路径)
		 * coo.setDomain("jt.com");// 允许访问的域名 ,数据共享,写公用的 response.addCookie(coo);//
		 * 保存到浏览器
		 */		
		//改进:防止同一个用户在不同浏览器重复登录
		//1.动态获取用户ip  NGINX配置
		String ip = IPUtil.getIpAddr(request);//工具Api
		System.out.println(ip+"~~~~~~~~~~~~~~~");
		//2.完成用户信息校验
		String ticket = userService.doLogin(user,ip);
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		CookieUtil.addCookie(request, response, "JT_TICKET", ticket,60 * 60 * 24 * 7 ,"jt.com" );
		CookieUtil.addCookie(request, response, "JT_USERNAME", user.getUsername(),60 * 60 * 24 * 7 ,"jt.com" );
		return SysResult.success();
	}
	/**
	 * 用户退出:
	 * 获取cookies 首先获取JT_TICKET的cookie的值ticket 删除cookie 名称为JT_TICKET
	 *  删除redis 根据ticket值  
	 * 
	 * @return user/logout.html
	 */
	 
	  @RequestMapping("/logout")
	  public String logout(HttpServletRequest request,HttpServletResponse response) {
	  Cookie[] cookies = request.getCookies(); 
	  String ticket=null;
	  if(cookies.length>0) {
	  for (Cookie cookie : cookies) {
		if(cookie.getName().equals("JT_TICKET")) {
			ticket = cookie.getValue();
			delCookie(request,response);//删除cookie
			break;
		}
	}
	  }
	  if(!StringUtils.isEmpty(ticket)) {
	  jedis.del(ticket);
	  }
	  return "redirect:/";//转发 首页
	  }
	  private void delCookie(HttpServletRequest request,HttpServletResponse response) {
		 CookieUtil.addCookie(request, response, TICKET, "删除", 0, "jt.com"); //重新创建一个生命周期为0的cookie进行覆盖
	  }
	 @RequestMapping("/layui")
	 public  String lay() {
		 return "layy";
	 }
}
