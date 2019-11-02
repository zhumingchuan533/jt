package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{//替代 web.xml
	@Autowired
	private UserInterceptor userInterceptor;//注入自定义的拦截器
	//开启匹配后缀型配置 .html
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		
		configurer.setUseSuffixPatternMatch(true);
	}
	/**
	 * 配置拦截器
	 */
	@Override
		public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor)
		.addPathPatterns("/cart/**","/order/**");//拦截器资源路径  *所有一级目录 **所有级目录
		}
}
