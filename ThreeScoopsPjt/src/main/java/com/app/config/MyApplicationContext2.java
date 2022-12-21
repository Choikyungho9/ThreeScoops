package com.app.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration	//configuration 어노테이션을 통해서 bean을 설정한다.
@ComponentScan(basePackages = {"com.threescoops.repository","com.threescoops.service"})
@MapperScan(basePackages = {"mybatis"})
@EnableTransactionManagement // 설정용 xml파일만들때, <tx:annotation-drive>태그와 같음

public class MyApplicationContext2 {
	@Bean
	public com.zaxxer.hikari.HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		//DriverSpy실행시 log4jdbc.log4j2.properties파일 필요
		config.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@127.0.0.1:1521:XE");
		//config.setJdbcUrl("jdbc:log4jdbc:oracle:thin:@172.30.1.89:1521:XE");
		config.setUsername("kosa");
		config.setPassword("kosa");
		config.setMinimumIdle(2);
		return config;
	}
	
	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(hikariConfig());
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean  sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		org.springframework.core.io.Resource resource = new ClassPathResource("mybatis-config.xml");
		sqlSessionFactory.setConfigLocation(resource);
		//sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/config/mybatis-config.xml"));
		return (SqlSessionFactory)sqlSessionFactory.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager txManager() {
		DataSourceTransactionManager tx = 
			new DataSourceTransactionManager(dataSource());
		return tx;
	}

	private void DataSourceTransactionManager() {
		// TODO Auto-generated method stub
		
	}
	
	
}
