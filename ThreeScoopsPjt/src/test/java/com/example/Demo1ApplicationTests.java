package com.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void test() {
		log.warn("warn-단위테스트");
		log.info("info-단위테스트");
	}
	
}
