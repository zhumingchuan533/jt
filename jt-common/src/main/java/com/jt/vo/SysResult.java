package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class SysResult {
  private Integer status;//状态码 200正常
  private String msg; //服务器返回客户端的消息
  private Object data; //服务器返回客户端的数据
  
  public static SysResult success() {
	  return new SysResult(200,null,null);
  }
  public static SysResult success(String msg,Object object) {
	  return new SysResult(200,msg,object);
  }
  public static SysResult success(Object data) {
	  return new SysResult(200,null,data);
  }
  
  public static SysResult fail() {
	  return new SysResult(201,null,null);
  }
  public static SysResult fail(String msg) {
	  return new SysResult(201,msg,null);
  }
 
 
}
