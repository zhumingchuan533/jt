package com.jt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class TestZhu {
  public  void  test1() {
	int[] a =new int[10];
	int count=0;
	while(count<10) {
	int r = new Random().nextInt();
	for (int i : a) {
		boolean j = i==r;
		if(j)break;
	}
	
  }
  }
  @Test
  public void test2() {
	  int i=0;
      for(i=1;i<=20;i++) {
          System.out.println(f(i));
  }
  }
  public static int f(int x){
  {
      if(x==1 || x==2)//1 1  2 1  3 2  4 3
          return 1;
      else
          return f(x-1)+f(x-2);
  }
  }
}
