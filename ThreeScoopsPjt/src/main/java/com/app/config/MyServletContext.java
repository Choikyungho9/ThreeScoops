package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = {"control", "advice"})
@EnableWebMvc
public class MyServletContext implements WebMvcConfigurer {
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver r = new CommonsMultipartResolver();
		//r.setUploadTempDir(null);
		r.setDefaultEncoding("UTF-8");
		r.setMaxUploadSize(1024*10);
		r.setMaxUploadSizePerFile(1024*10);
		return r;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/h/**").addResourceLocations("classpath:/static/html/");
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
		
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		//WebMvcConfigurer.super.addCorsMappings(registry);
		
//		CorsRegistration cr = registry.addMapping("/**");
//		cr.allowCredentials(true);
//		cr.allowedOrigins("*");
		
//		registry.addMapping("/**").allowCredentials(true).allowedOrigins("*").allowedMethods("GET","POST","PUT","DELETE");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://172.30.1.89:9999/demomvc1").allowedMethods("GET","POST","PUT","DELETE");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://172.30.1.89:5500").allowedMethods("GET","POST","PUT","DELETE");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://192.168.2.25:9999/demomvc1").allowedMethods("GET","POST","PUT","DELETE");
		registry.addMapping("/**").allowCredentials(true).allowedOrigins("http://192.168.2.25:5500").allowedMethods("GET","POST","PUT","DELETE");
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/view/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	
	
}
