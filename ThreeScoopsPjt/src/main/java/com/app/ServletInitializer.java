package com.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//Springboot에서는 WebInitializer대신 ServletInitializer 사용
//라이브러리 설치 -> Spring Web
//Web.xml을 대신할 파일
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ScoopsApplication.class);
	}
}
