package com.threescoops.vo;

import java.io.Serializable;

public class Customer extends Person implements Serializable{
	private String id;
	private String pwd;
	private String email;
	private String phone;
	private String address;
	private String birth;
	
	
	public Customer(){
	}
	
	public Customer(String id, String pwd, String name,String email,String phone, String address, String birth){
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email=email;
		this.phone=phone;
		this.address=address;
		this.birth=birth;
		
		//setName(name);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void printInfo() {
		System.out.println("id:" + id 
				+", pwd:" + pwd 
				+ ", name:" + name);//getName());
	}
}