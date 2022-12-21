package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.repository.TestRepository;

@Service
public class TestService {
	@Autowired
	private TestRepository repository;
	public String test() {
		System.out.println("in TestService");
		return repository.test();
	}
}
