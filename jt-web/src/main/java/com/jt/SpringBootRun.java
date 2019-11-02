package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
/**
 * (exclude=DataSourceAutoConfiguration.class):
 * 原因:web项目没有数据源 :spring加载时会加载继承的数据库jar包 会自动配置数据源   所以要添加数据的yml配置 导致报错
 * 作用:表示启动时不需要配置数据源  
 * @author Administrator
 *
 */
@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
public class SpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class,args);
	}
}
