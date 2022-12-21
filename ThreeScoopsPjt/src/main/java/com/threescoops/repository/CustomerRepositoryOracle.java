package com.threescoops.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.sql.MyConnection;
import com.threescoops.vo.Customer;

@Repository("customerRepository")
public class CustomerRepositoryOracle implements CustomerRepository {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataSource ds;
	
	@Override
	public void insert(Customer c) throws AddException {
		
		//2)Connection
		Connection conn = null;
		PreparedStatement pstmt = null;
		String insertSQL = "INSERT INTO customer(id,pwd,name,USER_EMAIL,USER_PHONE,USER_ADDRESS,USER_BIRTH) VALUES(?,?,?,?,?,?,?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, c.getId());
			pstmt.setString(2, c.getPwd());
			pstmt.setString(3, c.getName());
			pstmt.setString(4, c.getEmail());
			pstmt.setString(5, c.getPhone());
			pstmt.setString(6, c.getAddress());
			pstmt.setString(7, c.getBirth());
			int rowCnt = pstmt.executeUpdate();
			System.out.println(rowCnt +"건 추가되었습니다");
			
		} catch (SQLException e) {
			if(e.getErrorCode() == 1) {
				throw new AddException("이미 사용중인 아이디입니다");
			}
			e.printStackTrace();
			throw new AddException(e.getMessage());
		} finally {			//5.DB와 연결닫기
			MyConnection.close(pstmt, conn);
		}	

	}

	@Override
	public Customer selectById(String id) throws FindException {
		log.debug("selectById()의 id="+id);
		Connection conn = null;
		
		//2.SQL송신
		PreparedStatement pstmt = null;
		//3.결과를 수신
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			String selectSQL = 
					"SELECT * "
				  + " FROM customer"
				  + " WHERE id=?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			//4.결과 활용
			if(rs.next()) {  
				return new Customer(rs.getString("id"), rs.getString("pwd"), rs.getString("name"),rs.getString("USER_EMAIL"),rs.getString("USER_PHONE"),rs.getString("USER_ADDRESS"),rs.getString("USER_BIRTH"));
			}
			throw new FindException("ID에 해당하는 고객이 없습니다");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			MyConnection.close(rs, pstmt, conn);
		}
	}

}