package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threescoops.dto.PageBean;
import com.threescoops.exception.FindException;
import com.threescoops.service.ProductService;
import com.threescoops.vo.Product;

@Controller
public class ProductController {
	@Autowired
	private ProductService service;
	
	@GetMapping("productlist")
	@ResponseBody
	public Map<String, Object> list(//HttpServletRequest request, 
			                        //HttpServletResponse response,
			                        int currentPage){
//CORS해결방법
//		response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		Map<String,Object> map = new HashMap<>();
		try {			
			PageBean<Product> pb = service.getPageBean(currentPage);					
			map.put("status", 1);
			map.put("pb", pb);
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);			
		}
		return map;
	}
	@GetMapping("productdetail")
	@ResponseBody
	public Map<String, Object> detail(String prodNo) {
		//CORS해결방법
//		response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
//		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		//JSON문자열 만들기
		Map<String, Object>map = new HashMap<>();
		try {
			Product p = service.findByNo(prodNo);
			map.put("status", 1);
			map.put("p", p);			
		} catch (FindException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());						
		}
		return map;
	}
}
