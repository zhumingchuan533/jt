package com.jt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
/**
 * 测试String类型
 * host:ip地址
 * port:端口号
 * 错误查询:
 * 1.ip端口检查
 * 2.检查Linux防火墙是否关闭
 * 3.检查redis.conf文件是否更改三处
 * 4.检查redis启动方式redis-server redis.conf
 * @throws InterruptedException
 * 
 *  分布式锁:
 *  redis创建一个锁
 *  .setnx
 *  防止死锁:.setex
 */
	
	private Jedis jedis;
	@Before//测试之前先执行它
	public void init() {
		
		 jedis=new Jedis("192.168.153.130",6379);
	}
	@Test
	public void testString() throws InterruptedException {
		jedis.set("1906", "redis入门案例");
		String value = jedis.get("1906");
		System.out.println(value);
		//2.测试key相同时 value 是否覆盖
		jedis.set("1906", "redis测试");
		System.out.println(jedis.get("1906"));
		//3.如果值以及存在,那么不允许覆盖
		jedis.setnx("1906", "100");
		System.out.println(jedis.get("1906"));
		//4.为数据 添加超时时间
		jedis.set("1907", "哈哈");
		jedis.expire("1907", 30);
		//保证数据的操作有效性(原子性)
		jedis.setex("1907", 30, "超时测试");
		Thread.sleep(1000);
		Long ttl = jedis.ttl("1907");
		System.out.println("当前数据还能活"+ttl+"s");
		//5.要求设置key存在时不允许操作,并且设定超时时间
		//nx:不允许覆盖 xx:可以覆盖    ex:单位秒 px:单位毫秒  
		jedis.set("时间", "测试是否有效", "NX", "EX", 30);
		System.out.println(jedis.get("时间"));
		
		
	}
	/**|
	 *测试hash
	 */
	@Test
	public  void testHash() {
		jedis.hset("user", "id", "100");
		jedis.hset("user", "name", "强哥");
		System.out.println(jedis.hgetAll("user"));
	}
	/**
	 * 测试list集合 
	 */
	@Test
	public void testList() {
		jedis.lpush("list", "1","2","3","4");
		for (int i = 0; i < 5; i++) {
			System.out.println(jedis.rpop("list"));
		}
	}
	/**
	 * 控制事务
	 */
	@Test
	public  void testTx() {
		//开启事务
		Transaction multi = jedis.multi();
		try {
		multi.set("q", "qq");
		multi.set("w", null);
		//事务提交
		multi.exec();
		}catch(Exception e) {
			e.printStackTrace();
			multi.discard();//事务回滚
			
		}
	}
	/**
	 * 二:实现redis分片操作
	 */
	@Test
	public void testShards() {
		List<JedisShardInfo> list=new ArrayList<>();
		list.add(new JedisShardInfo("192.168.153.130",6379));
		list.add(new JedisShardInfo("192.168.153.130",6380));
		list.add(new JedisShardInfo("192.168.153.130",6381));
		ShardedJedis jedis =new ShardedJedis(list);
		jedis.set("1906", "谁呢");
		System.out.println(jedis.get("1906"));
	}
	/**
	 * 测试哨兵
	 * 调用原来:
	 *  用户通过哨兵,连接redis的主机,进行操作
	 * masterName:主机的变量名称
	 * sentinels:redis节点信息
	 *            Set<String>
	 */
	@Test
	public void  testSentinel() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.153.130:26379");//本机ip+哨兵端口
		JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster", sentinels);//mymaster自动获取数据   
		Jedis jedis=jedisSentinelPool.getResource();
		jedis.set("a", "哨兵");
		System.out.println(jedis.get("a"));
	}
	/**
	 * 利用spring整合redis集群
	 */
	@Test
	public void testCluster() {
		Set<HostAndPort> node=new HashSet<>();
		node.add(new HostAndPort("192.168.153.130", 7000));
		node.add(new HostAndPort("192.168.153.130", 7001));
		node.add(new HostAndPort("192.168.153.130", 7002));
		node.add(new HostAndPort("192.168.153.130", 7003));
		node.add(new HostAndPort("192.168.153.130", 7004));
		node.add(new HostAndPort("192.168.153.130", 7005));
		JedisCluster jedisCluster = new JedisCluster(node);
		jedisCluster.set("1906", "测试");
		System.out.println(jedisCluster.get("1906"));
	}
}
