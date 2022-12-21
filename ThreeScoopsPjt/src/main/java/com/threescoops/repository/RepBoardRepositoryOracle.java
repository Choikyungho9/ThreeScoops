package com.threescoops.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.exception.ModifyException;
import com.threescoops.exception.RemoveException;
import com.threescoops.sql.MyConnection;
import com.threescoops.vo.Customer;
import com.threescoops.vo.RepBoard;

import mybatis.RepBoardMapper;
@Repository
public class RepBoardRepositoryOracle implements RepBoardRepository {
	@Autowired
	private DataSource ds;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public int insert(RepBoard rb) throws AddException {
		SqlSession session=null;
		try {
			session=sqlSessionFactory.openSession();
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.insert(rb);
			return rb.getBoardNo();
//			Map<String, Object>map=new HashMap<>();
//			map.put("parentNo", rb.getParentNo());
//			map.put("boardTitle", rb.getBoardTitle());
//			map.put("boardC.id", rb.getBoardC().getId());
//			map.put("boardContent", rb.getBoardContent());
//			session.insert("com.my.mybatis.RepBoardMapper.insert",map);
			
			
//			String selectSequenceSQL = "SELECT rep_board_seq.CURRVAL from dual";
//			pstmt = conn.prepareStatement(selectSequenceSQL);
//			rs = pstmt.executeQuery();
//			rs.next();
			/*repboardMapper.xml사용할때의 버전
			return rb.getBoardNo();
			session.insert("com.my.mybatis.RepBoardMapper.insert",rb);
			*/
			//RepBoardMapper.class인터페이스를 사용할때의 버전
		

			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

	@Override
	public List<RepBoard> selectAll() throws FindException {
		List<RepBoard> list = new ArrayList<>();
		SqlSession session=null;
		try{
		session = sqlSessionFactory.openSession();
		
		RepBoardMapper mapper=session.getMapper(RepBoardMapper.class);
		list=mapper.selectAll();
		//list = session.selectList("com.my.mybatis.RepBoardMapper.selectAll");
		
		
		return list;
		}catch(
			Exception e)
		{
		e.printStackTrace();
		throw new FindException(e.getMessage());
		}finally
		{
		if(session !=null) {
			session.close();
		}
	}
}
	@Override
	public List<RepBoard> selectAll(int currentPage, int cntPerPage) throws FindException {
		List<RepBoard> list = new ArrayList<>();
		SqlSession session=null;
		
		try {
			session=sqlSessionFactory.openSession();
			int startRow = ((currentPage-1)*cntPerPage)+1;
			int endRow = currentPage * cntPerPage;
			Map<String, Integer>map=new HashMap<>();
			map.put("startRow", startRow);
			map.put("endRow", endRow);	
			//list=session.selectList("com.my.mybatis.RepBoardMapper.selectAllPage", map);
			
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			list = mapper.selectAllPage(map);
			
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		} finally {
			if(session !=null) {
				session.close();
			}
		}	
	}
	
	
	@Override
	public int selectCount() throws FindException {
		SqlSession session=null;

		try {
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			session=sqlSessionFactory.openSession();
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);	
		return mapper.selectCount();
		//session = sqlSessionFactory.openSession();
		//return session.selectOne("com.my.mybatis.RepBoardMapper.selectCount");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}
	
	@Override
	public RepBoard selectByboardNo(int boardNo) throws FindException {
		SqlSession session=null;
		try {
			session=sqlSessionFactory.openSession();
			//RepBoard rb =session.selectOne(
			//		"com.my.mybatis.RepBoardMapper.selectByboardNo",boardNo);
			
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			RepBoard rb = mapper.selectByboardNo(boardNo);
			if(rb==null) {
				throw new FindException("글번호에 해당하는 글이 없습니다.");
			}
			return rb;
	}catch(Exception e){
		e.printStackTrace();
		throw new FindException(e.getMessage());
	} finally {
		if (session != null) {
			session.close();
		}

	}
}
	@Override
	public void updateViewCnt(int boardNo) throws ModifyException {
		SqlSession session = null;
		
		try {
			//session = sqlSessionFactory.openSession();
			//session.update("com.my.mybatis.RepBoardMapper.updateViewCnt",boardNo);
			
			
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			session=sqlSessionFactory.openSession();
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.updateViewCnt(boardNo);
		} catch (Exception e) {
			e.printStackTrace();
			//throw new ModifyException(e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	@Override
	public void update(RepBoard rb) throws ModifyException {
		SqlSession session = null;
		try {
			session=sqlSessionFactory.openSession();
			session.update("com.my.mybatis.RepBoardMapper.update",rb);
			int rowcnt=session.update("com.my.mybatis.RepBoardMapper.update",rb);
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			
//			session=sqlSessionFactory.openSession();		
//			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
//			int rowcnt =mapper.update(rb);
			
			if(rowcnt == 0) {
				throw new ModifyException("글번호가 없거나 작성자가 다른경우 수정할 수 없습니다");
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

	@Override
	public void delete(int boardNo) throws RemoveException {
		SqlSession session = null;
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		String deleteSQL = "DELETE rep_board\r\n"
//				+ "WHERE board_no IN ( SELECT board_no\r\n"
//				+ "                    FROM rep_board\r\n"
//				+ "                    START WITH board_no=? \r\n"
//				+ "                   CONNECT BY PRIOR board_no = parent_no\r\n"
//				+ "                   )";
		try {
			
			//repboardmapper.xml안쓰고 자바클래스 쓰는 버전
			session=sqlSessionFactory.openSession();
			RepBoardMapper mapper = session.getMapper(RepBoardMapper.class);
			mapper.delete(boardNo);

		}catch(Exception e) {
			e.printStackTrace();
			throw new RemoveException(e.getMessage());
		}finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
