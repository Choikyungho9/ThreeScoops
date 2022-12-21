package com.threescoops.repository;

import java.util.List;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.exception.ModifyException;
import com.threescoops.exception.RemoveException;
import com.threescoops.vo.RepBoard;

public interface RepBoardRepository {
	/**
	 * 글을 추가한다
	 * 추가된 글번호 조회하여 반환한다
	 * @param rb 글
	 * @throws AddException
	 */
	int insert(RepBoard rb) throws AddException;
	/**
	 * 모든 글을 검색한다 
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectAll() throws FindException;
	/**
	 * 페이지에 해당하는 글을 검색한다
	 * @param currentPage
	 * @param cntPerPage
	 * @return
	 * @throws FindException
	 */
	List<RepBoard> selectAll(int currentPage, int cntPerPage) throws FindException;
	
	/**
	 * 총 게시글수를 반환한다
	 * @return 게시글개수
	 * @throws FindException
	 */
	int selectCount() throws FindException;
	/**
	 * 글번호에 해당하는 글을 검색한다
	 * @param boardNo 글번호
	 * @return
	 * @throws FindException
	 */
	RepBoard selectByboardNo(int boardNo) throws FindException; 
	
	/**
	 * 조회수1증가
	 * @param boardNo 글번호
	 * @throws FindException
	 */
	void updateViewCnt(int boardNo) throws ModifyException;
	
	/**
	 * 글제목,글내용수정
	 * @param rb
	 * @throws ModifyException
	 */
	void update(RepBoard rb) throws ModifyException;
	
	/**
	 * 글번호에 해당하는 글삭제
	 * @param boardNo
	 * @throws RemoveException
	 */
	void delete(int boardNo) throws RemoveException;
}
