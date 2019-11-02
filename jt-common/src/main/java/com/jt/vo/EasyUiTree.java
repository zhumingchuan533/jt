package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true) 
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全部参数构造
public class EasyUiTree {
 private Long id;  //节点id
 private String  text;  //节点名称
 private String state;  //节点状态  open/closed
}
