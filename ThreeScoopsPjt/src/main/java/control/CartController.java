package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {
	@GetMapping("putcart")
	@ResponseBody
	public void put(String prodNo, int quantity, HttpSession session) {
		//장바구니 세션속성(이름: cart)찾기
		Map<String,Integer> cart = (Map)session.getAttribute("cart");
		//장바구니 세션속성(이름: cart)없으면 속성추가
		if(cart == null) {
			cart = new HashMap<>();
			session.setAttribute("cart", cart);
		}
		//장바구니에 상품번호와 수량추가
		Integer quantity2 = cart.get(prodNo);
		if(quantity2 != null) {	
			//quantity += quantity2.intValue();
			quantity += quantity2;
		}
		cart.put(prodNo, quantity);
	}
	
	@GetMapping("cartlist")
	@ResponseBody
	public Map<String,Object> list(HttpSession session){
		Map<String, Integer> cart = (Map)session.getAttribute("cart");
		
		Map<String, Object> map = new HashMap<>();
		if(cart == null || cart.size() == 0) { //장바구니가 없거나 비어있는 경우
			map.put("status", 0);
		}else {
			map.put("status", 1);
			map.put("cart", cart);
		}
		return map;
	}
}
