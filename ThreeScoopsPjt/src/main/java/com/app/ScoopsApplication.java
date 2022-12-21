package com.app;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.app.repository.TestRepository;

@SpringBootApplication
public class ScoopsApplication {
	private static Logger log  = LoggerFactory.getLogger(ScoopsApplication.class);
//	@Bean
//  스프링빈 설정 구간
	
	public static void main(String[] args) {
//		스프링 컨테이너 구동은 .run 메서드로 한다.
//		사용할 설정파일 : Demo1Application.class
//					: 현재패키지 또는 하위패키지에 있는 @Configuration 클래스들
		
		SpringApplication.run(ScoopsApplication.class, args);
		
		log.trace("@@@trace-스프링부트@@@@@@@@@@@@@@");
		log.debug("@@@debug-스프링부트@@@@@@@@@@@@@@");
		log.info("@@@info-스프링부트@@@@@@@@@@@@@@");
		log.warn("@@@warn-스프링부트@@@@@@@@@@@@@@");
		log.error("@@@error-스프링부트@@@@@@@@@@@@@@");
		
		
		System.out.println(getBean(com.app.repository.TestRepository.class));
		System.out.println(getBean(com.app.service.TestService.class));
		
		Object bean = 
				getBean(com.app.repository.TestRepository.class);
		TestRepository repository = (TestRepository)bean;
		System.out.println(repository.test());

	}
	public static Object getBean(Class<?> classType) {
	    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
	    return applicationContext.getBean(classType);
	}


}
