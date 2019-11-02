package com.jt.aop;

import javax.management.RuntimeErrorException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.jt.annotation.Cache_find;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Aspect//表示切面
@Component//交给spring管理
public class CacheAspect {
	//当切面位于common中,必须添加required=false ,需要用时在注入
    @Autowired(required = false)
    private JedisCluster jedis;//redis集群 效率低于分片 但能防止宕机
    //private Jedis jedis;//哨兵机制 升级
	//private ShardedJedis jedis;//分片机制
    /**
     * 通知选择:
     *    是否需要控制目标方法执行,使用环绕通知
     * 步骤:
     * 		1.动态生成key  包名.类名.方法名+方法参数(::parentId)
     *      2.通过key查询缓存
     */
    @Around("@annotation(cachefind)")//直接获取注解的对象
    public Object around(ProceedingJoinPoint joinPoint,Cache_find cachefind) {//环绕通知的切入方法   参数直接获取目标方法上注解对象
    	String key=getKey( joinPoint, cachefind);
    	String result = jedis.get(key);
    	Object data=null;
    	try {
    	if(StringUtils.isEmpty(result)) {
    		//为null 执行目标方法
    		data=joinPoint.proceed();
    		//将数据转成字符串
    		String json = ObjectMapperUtil.toJson(data);
            //判断生命周期 
    		if(cachefind.seconds()==0) {
    			jedis.set(key, json);
    		}else {
    			jedis.setex(key, cachefind.seconds(), json);
    		}
    		return data;
    	}else {
    		//缓存有数据
    		Class c=getClass(joinPoint);
    		Object object = ObjectMapperUtil.toObject(result,c );
    		return object;
    	}
    	}catch(Throwable e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	}
    	
    	
    }
    //获取key
    public  String getKey(ProceedingJoinPoint joinPoint,Cache_find cachefind) {
    	//1.判断用户是否传值
    	String key = cachefind.key();
    	if(!StringUtils.isEmpty(key)) {
    		return key;//返回用户自己定义的key
    	}
    	//1.表示需要自动生成key
    	String name = joinPoint.getSignature().getDeclaringTypeName();//获取目标方法所在的类名
    	String name2 = joinPoint.getSignature().getName();//获取目标方法名
    	Object[] args=joinPoint.getArgs();//获取目标方法的参数
    	key=name+"."+name2+"::"+args[0];
    	return key;
    	
    }
    private Class getClass(ProceedingJoinPoint joinPoint) {
    	MethodSignature signature=(MethodSignature)joinPoint.getSignature();
    	return signature.getReturnType();//获取目标方法返回值类型
    }
}
