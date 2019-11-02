package com.jt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//修饰注解 在方法上使用
@Retention(RetentionPolicy.RUNTIME)//修饰注解 在运行时生效
public @interface Cache_find {
    //key  为null时,自动生成动态key   不为null,接收key
	String key() default "";//允许不写key
	int seconds() default 0;//生命周期  默认0,表示数据不过期
}
