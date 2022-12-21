package com.app.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@GetMapping("a")
	@ResponseBody
	public void a() {
		System.out.println("a......");
	}

	@GetMapping("h")
	public void h() {
		System.out.println("TestController빈의 h()메서드입니다.");
	}
	
	
}
