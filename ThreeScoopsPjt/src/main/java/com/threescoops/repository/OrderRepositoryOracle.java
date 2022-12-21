package com.threescoops.repository;

import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.sql.MyConnection;
import com.threescoops.vo.Customer;
import com.threescoops.vo.OrderInfo;
import com.threescoops.vo.OrderLine;
import com.threescoops.vo.Product;

import mybatis.OrderMapper;
@Repository
public class OrderRepositoryOracle implements OrderRepository {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	private void insertInfo(SqlSession session, OrderInfo info) throws Exception{
//		session 삽입은 insert(네임스페이스.Type이름 , 
//		session.insert("com.my.mybatis.OrderMapper.insertInfo", info.getOrderC().getId());
		OrderMapper mapper = session.getMapper(OrderMapper.class);
		mapper.insertInfo(info.getOrderC().getId());
	}
	private void insertLines(SqlSession session, List<OrderLine> lines)throws Exception{
		for(OrderLine line: lines) {
//			session.insert("com.my.mybatis.OrderMapper.insertLine", line);
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			mapper.insertLine(line);
		}		
	}
	
	@Override
	@Transactional(rollbackFor = {AddException.class})
	public void insert(OrderInfo info) throws AddException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			//트랜젝션 처리는 이제 AOP기술을 이용해서 선언적 트랜젝션으로 바꿀꺼다.
			//누가 소스코드에 commit rollback 하니
			//JDBC는 기본 AutoCommit으로 설정되어있다
			//conn.setAutoCommit(false); //AutoCommit해제 하기
			
			
			//주문기본정보 추가		
			insertInfo(session, info);	
			
			//주문상세정보 추가
			insertLines(session, info.getLines());	
			
			/* 주문 기본정보 추가랑 주문 상세정보 추가가 동시에 일어나야된다. 
			 * 따라서 AOP 기술을 이용해서 SPRING에서는 선언부분으로 
			 * 주문기본정보가 추가 됬는데 상세정보가 추가 안됬으면, 에러처리&롤백되도록 코드를 추가할 것이다.
			 * */
	
			//conn.commit(); //트랜잭션 성공 완료시키기 -> 현업에서는 코드에 커밋하는거 안넣는다
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<OrderInfo> selectById(String orderId) throws FindException {
		List<OrderInfo> infos = new ArrayList<>();
		
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
//			infos = session.selectList("com.my.mybatis.OrderMapper.selectById",orderId);
			OrderMapper mapper = session.getMapper(OrderMapper.class);
			infos = mapper.selectById(orderId);
			return infos;
		}catch(Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session != null) {
				session.close();
			}
		}
}			

	public static void main(String[] args) {
		OrderRepository repository = new OrderRepositoryOracle();
		//--주문 검색 테스트
//		try {
//			List<OrderInfo> infos = repository.selectById("id1");
//			for(OrderInfo info: infos) {
//				int orderNo = info.getOrderNo();
//				//String orderId = info.getOrderC().getId();
//				Date orderDt = info.getOrderDt();
//				System.out.println("주문번호 : " + orderNo);				
//				System.out.println("주문일자 : " + orderDt);
//				List<OrderLine> lines = info.getLines();
//				System.out.println("주문상품번호\t주문상품명\t주문상품가격\t주문수량");
//				for(OrderLine line: lines) {
//					Product p = line.getOrderP();
//					String prodNo = p.getProdNo();
//					String prodName = p.getProdName();
//					int prodPrice = p.getProdPrice();
//					int orderQuantity = line.getOrderQuantity();
//					System.out.println(prodNo+"\t" + prodName +"\t"+ prodPrice +"\t"+ orderQuantity);
//				}
//				System.out.println("-----------------------------------------");
//			}
//		} catch (FindException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//--주문 추가 테스트		
		OrderInfo info = new OrderInfo();
		Customer c = new Customer();  
		c.setId("id1");
		//c.setId("id3");
//		c.setId("id2");
		info.setOrderC(c);
		
		List<OrderLine> lines = new ArrayList<>();
		for(int i=1; i<=3; i++) {
			OrderLine line = new OrderLine();
			Product p = new Product();
			//p.setProdNo("C000"+i);
			//p.setProdNo("F000" + i);
			p.setProdNo("G000" + i);
			line.setOrderP(p);
			//line.setOrderQuantity(i*10);
			line.setOrderQuantity(i);
			lines.add(line);
		}
		info.setLines(lines);
		try {
			repository.insert(info);
		} catch (AddException e) {
			e.printStackTrace();
		}
	}
	
}
