package com.threescoops.repository;

import java.util.List;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.vo.OrderInfo;

public interface OrderRepository {
	/**
	 * 주문정보(주문기본정보+상세정보들)을 추가한다
	 * @param info
	 * @throws AddException 
	 */
	void insert(OrderInfo info) throws AddException;
	
	/**
	 * 주문자아이디에 해당하는 주문목록을 검색한다
	 * @param orderId 주문자아이디
	 * @return 주문목록
	 * @throws FindException
	 */
	List<OrderInfo> selectById(String orderId) throws FindException;
}
