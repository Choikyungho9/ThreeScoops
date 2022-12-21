package com.threescoops.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.repository.OrderRepository;
import com.threescoops.vo.OrderInfo;
@Service
public class OrderService {
	@Autowired
	private OrderRepository repository;

	public void addOrder(OrderInfo info) throws AddException{
		repository.insert(info);
	}
	public List<OrderInfo> findById(String orderId) throws FindException{
		return repository.selectById(orderId);
	}
}
