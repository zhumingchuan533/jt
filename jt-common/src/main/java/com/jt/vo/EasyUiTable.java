package com.jt.vo;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
public class EasyUiTable {
	/**
	 * 数据转化为json串是调用属性的get方法.
	 * getTotal()  ~~~get去掉~~~首字母小写 生成key value:get方法获取的值
	 * 
	 * json转化为对象:
	 * 调用对象的set方法.
	 */
  private Integer total;
  private List<?>  rows;
  public  EasyUiTable() {
	  
  }
public EasyUiTable(Integer total, List<?> rows) {
	super();
	this.total = total;
	this.rows = rows;
}
  
  
}
