package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration // 标识配置类
@PropertySource("classpath:/properties/redis.properties") // 引入配置文件
public class RedisConfig {
	/*
	 * @Value("${redis.host}") private String host;
	 * 
	 * @Value("${redis.port}") private Integer port;
	 * 
	 * @Bean public Jedis jedis() { return new Jedis(host,port); }
	 */
	@Value("${redis.nodes}")
	private String nodes;

	/**
	 * 配置哨兵
	 * 
	 * @return
	 */
//	@Bean
//	@Scope("prototype")//实现多例对象
//	public Jedis jedis(JedisSentinelPool jedisSentinelPool) {//加了Bean的方法参数对象自动(Aoutweid)赋值
//		
//		Jedis jedis = jedisSentinelPool.getResource();
//		return jedis;
//	}
//	//单独创建池  实现池单例
//	@Bean
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(nodes);// 本机ip+哨兵端口
//		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);// mymaster自动获取数据
//		return jedisSentinelPool;
//	}
	/**
	 * 集群
	 * @param node
	 * @return
	 */
	@Bean
	@Scope("prototype")//实现多例对象
	public JedisCluster jedisCluster(Set<HostAndPort> node) {//加了Bean的方法参数对象自动(Aoutweid)赋值
		
		return new JedisCluster(node);
	}
	@Bean
	public Set<HostAndPort> getNode(){
		Set<HostAndPort> node=new HashSet<>();
		String[] arrynode = nodes.split(",");//拆分字符串
		for (String n : arrynode) {
			String[] ns = n.split(":");
			int port=Integer.parseInt(ns[1]);
			node.add(new HostAndPort(ns[0],port));
		}
		return node;
	}
	/**
	 * 分片机制
	 */
//	  @Bean 
//	  public ShardedJedis shardedJedis() { 
//		  List<JedisShardInfo> list
//	  =getList(); return new ShardedJedis(list); } private List<JedisShardInfo>
//	  getList(){ List<JedisShardInfo> list=new ArrayList<>(); String[]
//	  arrynodes=nodes.split(","); //node=ip:port for (String node : arrynodes) {
//	  String host=node.split(":")[0]; int
//	  port=Integer.parseInt(node.split(":")[1]);//才分字符串 并转化为int类型 list.add(new
//	  JedisShardInfo(host,port)); }
//	  
//	  return list; }
//	  
//	 
//	  list.add(new JedisShardInfo("192.168.153.130",6379)); list.add(new
//	  JedisShardInfo("192.168.153.130",6380)); list.add(new
//	  JedisShardInfo("192.168.153.130",6381)); ShardedJedis jedis =new
//	  ShardedJedis(list); jedis.set("1906", "谁呢");
//	  System.out.println(jedis.get("1906"));
	 
}
