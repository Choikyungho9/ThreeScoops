package control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.service.CustomerService;
import com.threescoops.vo.Customer;

@Controller
public class CustomerController {
	@Autowired
	private CustomerService service;
	@PostMapping("login")
	@ResponseBody
	public Map<String, Object> login(String id, String pwd, HttpSession session){
		Map<String, Object>map = new HashMap<>();
		session.removeAttribute("loginedId");
		try {
			service.login(id, pwd);
			//로그인 성공시 로그인정보 세션저장
			session.setAttribute("loginedId", id);
			map.put("status", 1);			
			map.put("msg", "로그인성공");			
		} catch (FindException e) {			
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());			
		}
		return map;
	}
	@GetMapping("logout")
	@ResponseBody
	public void logout(HttpSession session) {
		session.removeAttribute("loginedId");
		session.invalidate(); //세션제거	
	}
	
	@GetMapping("iddupchk")
	@ResponseBody
	public Map<String,Object> idDupChk(String id){
		Map<String, Object>map = new HashMap<>();
		try {
			service.idDupChk(id);
			map.put("status", 0);
			map.put("msg", "이미 사용중인 아이디입니다");
		} catch (FindException e) {
			map.put("status", 1);			
			map.put("msg", "사용가능한 아이디입니다");
			e.printStackTrace();			
		}
		return map;
	}
	
	@PostMapping("signup")
	@ResponseBody
	public Map<String, Object> signup(Customer c){
		Map<String, Object>map = new HashMap<>();
		try {
			service.signup(c);
			map.put("status", 1);
			map.put("msg", "가입성공");
		} catch (AddException e) {
			e.printStackTrace();
			map.put("status", 0);
			map.put("msg", e.getMessage());
		}
		return map;
	}
}
