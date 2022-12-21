package com.threescoops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threescoops.dto.PageBean;
import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.exception.ModifyException;
import com.threescoops.exception.RemoveException;
import com.threescoops.repository.RepBoardRepository;
import com.threescoops.vo.Product;
import com.threescoops.vo.RepBoard;

@Service
public class RepBoardService {
	@Autowired
	private RepBoardRepository repository;
	
	/**
	 * 게시글을 쓴다
	 * @param rb
	 * @throws AddException
	 */
	public int writeBoard(RepBoard rb) throws AddException {
		rb.setParentNo(0);
		int boardNo = repository.insert(rb);
		return boardNo;
	}
	/**
	 * 답글을 쓴다
	 * @param rb 답글
	 * @throws AddException 부모글번호가 없는 경우나 답글쓰기 실패된 경우 예외발생한다
	 */
	public void writeReply(RepBoard rb) throws AddException {
		if(rb.getParentNo() == 0) { //부모글번호가 없는 경우
			throw new AddException("부모글번호가 없습니다");
		}
		repository.insert(rb);
	}
	/**
	 * 전체 게시글 목록을 반환한다
	 * @return
	 * @throws FindException
	 */
	public List<RepBoard> findAll() throws FindException{
		return repository.selectAll();
	}
	
	/**
	 * 페이지에 해당 게시글 목록을 반환한다
	 * @param currentPage 검색할 페이지
	 * @param cntPerPage  페이지별 보여줄 목록수
	 * @return
	 * @throws FindException
	 */
	public List<RepBoard> findAll(int currentPage, int cntPerPage) throws FindException{
		return repository.selectAll(currentPage, cntPerPage);
	}
	
	/**
	 * 페이지에 해당하는 페이지빈정보를 반환한다
	 * @param currentPage
	 * @return 페이지빈 페이지빈에는 게시글목록, 총페이지수, 페이지그룹의 시작페이지, 끝페이지정보가 모두 담겨있다
	 * @throws FindException
	 */
	public PageBean<RepBoard> getPageBean(int currentPage) throws FindException{
		List<RepBoard> list = findAll(currentPage, PageBean.CNT_PER_PAGE);
		int totalCnt = repository.selectCount();
		PageBean<RepBoard> pb = new PageBean<>(currentPage, list, totalCnt);
		return pb;
	}
	
	/**
	 * 게시글 상세조회한다
	 * @param boardNo 글번호
	 * @return 게시글
	 * @throws FindException
	 */
	public RepBoard findByBoardNo(int boardNo) throws FindException {
		try {
			repository.updateViewCnt(boardNo);
		} catch (ModifyException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		return repository.selectByboardNo(boardNo);
	}
	/**
	 * 게시글 수정한다 게시글제목, 게시글내용을 변경한다
	 * @param rb
	 * @throws ModifyException
	 */
	public void modify(RepBoard rb) throws ModifyException {
		repository.update(rb);
	}
	/**
	 * 게시글 삭제한다. 게시글의 모든 답글(답글과 답답글들)을 삭제한다
	 * @param boardNo
	 * @throws RemoveException
	 */
	public void remove(int boardNo) throws RemoveException {
		repository.delete(boardNo);
	}
}




