package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain =true)
@NoArgsConstructor
@AllArgsConstructor
public class Image {
  private Integer error=0;//0  没错,1表示有错
  private  String url; //表示图片的虚拟路径 
  private Integer width; //宽度
  private Integer  heigth;//高度
  
  //失败方法
  public static Image fail() {
	  return new Image(1,null,null,null);
  }
	  
}
