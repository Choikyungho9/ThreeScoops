package com.app.repository;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.threescoops.vo.RepBoard;

@Repository
public class TestRepository {
	@Autowired
	private SqlSessionFactory sqlSessionFactory;	//마이바티스용 스프링빈, 
	// + jdbc드라이버, spring-jdbc라이브러리, mybatis라이브러리, mybatis-spring라이브러리 필요, log4jdbc라이브러리필요
	//private DataSource dataSource; // HikariCP 스프링빈 또는 SimpleDriverDataSource
	public String test() {
		SqlSession session = null;
		session = sqlSessionFactory.openSession();
		RepBoard rb = session.selectOne("com.my.mybatis.RepBoardMapper.selectByboardNo", 69);
		String boardTitle = rb.getBoardTitle();
		return "TestRepository빈의 test()메서드입니다 게시글번호(69)의 제목:" + boardTitle;
	}

}
