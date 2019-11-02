package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.dubbo.service.DubboCartService;
import com.jt.pojo.Cart;
import com.jt.pojo.Item;
import com.jt.pojo.User;
import com.jt.util.ThreadLocalUtil;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
@Reference(check = false) // 不需要校驗	
private DubboCartService cartService;

/**购物车跳转:
 * http://www.jt.com/cart/show.html
 * 查询用户的全部购物记录
 */
@RequestMapping("/show")
public String doCart(Model model ,HttpServletRequest request) {
	User user = ThreadLocalUtil.get();
	Long userId=user.getId();
	List<Cart> list=cartService.findCartByUserId(userId);
	model.addAttribute("cartList", list);
	return "cart";
}
/**
 *  http://www.jt.com/cart/update/num/562379/6
 * @return
 */
@RequestMapping("/update/num/{itemId}/{num}")
@ResponseBody//json数据返回
public SysResult updateNum( Cart cart) {
	Long userId=ThreadLocalUtil.get().getId();
	cart.setUserId(userId);
	cartService.updateCartNum(cart);
	System.out.println(cart);
	return SysResult.success();
}
/**
 * /cart/add/${item.id}
 */
@RequestMapping("/add/{itemId}")
public String add( Cart cart) {
	User user = ThreadLocalUtil.get();
	Long id = user.getId();
	System.out.println(id );
	cart.setUserId(id);
	cartService.addCart(cart);
	System.out.println(cart);
	//System.out.println(itemId);
	return "redirect:/cart/show";//重定向
}
/**
 * 
 * @param id
 * @param itemId
 * @return
 */
 @RequestMapping("/delete/{itemId}")
 public String deleteCart(Cart cart) {
	 Long userId=ThreadLocalUtil.get().getId();
	 cart.setUserId(userId);
      cartService.deleteCartById(cart);
	 return"redirect:/cart/show";
 }
 
}
