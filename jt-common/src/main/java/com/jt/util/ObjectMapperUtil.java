package com.jt.util;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
   private static final ObjectMapper MAPPER =new ObjectMapper();
   /**
    * 对象转化为字符串
    */
   public static String toJson(Object obj) {
	   String result =null;
	   try {
	   result = MAPPER.writeValueAsString(obj);
	   
	   }catch(Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	   }
	   return result;
   }
   /**
    * 字符串转换为对象
    */
   public static <T> T toObject(String s,Class<T> c) {
	   T r=null;
	   try {
	    r = MAPPER.readValue(s, c);
	   }catch(Exception e) {
		   e.printStackTrace();
		   throw new RuntimeException(e);
	   }
	   return r;
   }
   /**
    * 集合转为字符串
    */
   public static String listJson(List<?> list) {
	   String s=null;
	   try {
	   s = MAPPER.writeValueAsString(list);
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   return s;
   }
   /**
    * 字符串转化为集合
    */
   public static List toList(String s,List<?> list ){
	   List l=null;
	   System.out.println(list);
	   try {
	    l = MAPPER.readValue(s, list.getClass());
	   
	   }catch(Exception e) {
		   e.printStackTrace();
		   
	   }
	   return l;
   }
}
