package com.jt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
 /**
  * 通过cookie名称获得cookie值
  */
	public static String getCookieValue(HttpServletRequest request,String cookieName) {
		Cookie[] cookies = request.getCookies();
		String cookieValue=null;
		if(cookies==null||cookies.length==0) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(cookieName)) {
				cookieValue=cookie.getValue();
				break;
			}
		}
		return cookieValue;
	}
	public static void addCookie(HttpServletRequest request,HttpServletResponse response,String cookieName,String cookieValue,int seconds,
			String domain) {
		Cookie cookie = new Cookie(cookieName,cookieValue);
		cookie.setMaxAge(seconds);//设置cookie生命时间
		 cookie.setPath("/");// COOKIE权限设定(路径)
		 cookie.setDomain(domain);// 允许访问的域名 ,数据共享,
		 response.addCookie(cookie);//存到浏览器
	}
}
