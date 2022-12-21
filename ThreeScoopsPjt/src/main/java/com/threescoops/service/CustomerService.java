package com.threescoops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.threescoops.exception.AddException;
import com.threescoops.exception.FindException;
import com.threescoops.repository.CustomerRepository;
import com.threescoops.vo.Customer;
@Service("customerService")
public class CustomerService {
	@Autowired
	private CustomerRepository repository;
	public CustomerService() {}
	/**
	 * 아이디와 비밀번호가 일치하지 않으면 FindException예외발생한다
	 * @param id 아이디
	 * @param pwd 비밀번호
	 */
	public void login(String id, String pwd) throws FindException{
		Customer c = repository.selectById(id);
		if(!c.getPwd().equals(pwd)) {
			throw new FindException("로그인 실패");
		}
	}
	/**
	 * 아이디가 이미 존재하는지 확인한다
	 * 아이디가 없는 경우에는 FindException발생한다
	 * @param id 아이디
	 */
	public void idDupChk(String id) throws FindException{
		repository.selectById(id);		
	}
	
	public Customer info(String id) throws FindException {
		return repository.selectById(id);
	}
	
	/**
	 * 고객정보를 가입한다
	 * 가입실패된 경우 AddException발생한다
	 * @param c 고객정보
	 */
	public void signup(Customer c) throws AddException{
		repository.insert(c);
	}
}
