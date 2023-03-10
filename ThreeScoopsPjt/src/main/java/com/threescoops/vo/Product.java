package com.threescoops.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import lombok.Data;
//@Data : @Setter@Getter@ToString@HashCode@Equals
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor

public class Product {
	private String prodNo;
	private String prodName;
	private int prodPrice;
	private String prodInfo;
	@Override
	public String toString() {
		return "Product [prodNo=" + prodNo + ", prodName=" + prodName + ", prodPrice=" + prodPrice + ", prodInfo="
				+ prodInfo + "]";
	}

}