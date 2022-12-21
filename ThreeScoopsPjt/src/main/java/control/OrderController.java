package control;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.service.OrderService;
import com.threescoops.vo.Customer;
import com.threescoops.vo.OrderInfo;
import com.threescoops.vo.OrderLine;
import com.threescoops.vo.Product;

@Controller
public class OrderController {
	@Autowired
	private OrderService service;
	@GetMapping("addorder")
	@ResponseBody
	public Map<String,Object> add(HttpSession session) {
		Map<String,Object> map = new HashMap<>();
		//장바구니 내용 얻기
		Map<String,Integer> cart = (Map)session.getAttribute("cart");
		
		
//		String loginedId = (String)session.getAttribute("loginedId"); //TODO
		String loginedId = "id1";
		System.out.println(loginedId + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
		if(cart == null || cart.size() == 0) { //장바구니가 없거나 비어있는 경우
			map.put("status", 0);
			map.put("msg", "장바구니가 비었습니다");
		}else if(loginedId == null) {
			map.put("status", 0);
			map.put("msg", "로그인하세요");
			System.out.println(loginedId + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}else {

			//장바구니내용을 OrderInfo객체화한다
			OrderInfo info = new OrderInfo();

			Customer c = new Customer();
			c.setId("id1");
//			c.setId(loginedId);
			info.setOrderC(c);

			List<OrderLine> lines = new ArrayList<>();
			for(String prodNo : cart.keySet()) {
				int quantity = cart.get(prodNo);
				OrderLine line = new OrderLine();
				Product p = new Product();
				p.setProdNo(prodNo);
				line.setOrderP(p);
				line.setOrderQuantity(quantity);
				lines.add(line);
			}
			info.setLines(lines);

			//주문한다
			try {
				service.addOrder(info);				
				//장바구니 삭제한다
				session.removeAttribute("cart");				
				map.put("status", 1);				
			} catch (AddException e) {
				e.printStackTrace();
				map.put("status", 0);
				map.put("msg", e.getMessage());
			}
		}
		return map;
	}
	
	@GetMapping("orderlist")
	public void list(HttpSession session, Model model){
		//String id = (String)session.getAttribute("loginedId");
		String id = "id1";
		if(id == null) {
			model.addAttribute("status", 0);
			model.addAttribute("msg", "로그인하세요@@@@@@");
		}else {
			try {
				List<OrderInfo> infos = service.findById(id);
				model.addAttribute("status", 1);
				model.addAttribute("orderlist", infos);
			} catch (FindException e) {
				e.printStackTrace();
				model.addAttribute("status", 0);
				model.addAttribute("msg", e.getMessage());
			}
		}
	}
}
