package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.IPUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
	private UserService userService;
   @Autowired
   private JedisCluster jedis;
    /**
     * 根据用户信息实现数据校验
     * 返回结果:
     *  true:已经存在  false:用户可以使用
     *  
     */
    @RequestMapping("/check/{param}/{type}")
    public JSONPObject chechUser(@PathVariable String param,@PathVariable Integer type,String callback) {
    	System.out.println(param+type+"~~~~~~~~~");
    	boolean data=userService.chechUser(param,type);
    	return new JSONPObject(callback, SysResult.success(data));
    }
    /**
     * //sso.jt.com/user/query/56421231
     * 根据ticket查询用户json数据,之后将数据返回给客户端
     * 回显
     */
//   @RequestMapping("/query/{ticket}")
//   public JSONPObject doQuery(@PathVariable String ticket,String callback) {
//	  String json = jedis.get(ticket);
//	  if(StringUtils.isEmpty(json)) {
//		  //结果为null
//		  return new JSONPObject(callback, SysResult.fail());
//		  
//	  }
//	  
//	   return new JSONPObject(callback, SysResult.success(json));
//   }
    //升级
   @RequestMapping("/query/{ticket}/{username}")
   public JSONPObject doQuery(@PathVariable String ticket,String callback,@PathVariable String username,HttpServletRequest request) {
	   //1.校验用户ip地址
	   String ip=IPUtil.getIpAddr(request);
	   //当前用户正确的数据
	   String localip = jedis.hget(username, "JT_IP");
	   if(!ip.equalsIgnoreCase(localip)) {
		   return new JSONPObject(callback, SysResult.fail());
	   }
	   //2.校验ticket信息
	   String localticket = jedis.hget(username, "JT_TICKET");
	   if(!ticket.equalsIgnoreCase(localticket)) {
		   return new JSONPObject(callback, SysResult.fail());
	   }
	   //说明用户信息正确
	   String userJSON=jedis.hget(username, ticket);
	  
		   return new JSONPObject(callback, SysResult.success(userJSON));
		   
	  
	   
	 
   }
}
