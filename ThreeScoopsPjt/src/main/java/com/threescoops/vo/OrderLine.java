package com.threescoops.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter  @Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {
	private int orderNo;
	//private String orderProdNo; //??
	private Product orderP;
	private int orderQuantity;
	@Override
	public String toString() {
		return "OrderLine [orderNo=" + orderNo + ", orderP=" + orderP + ", orderQuantity=" + orderQuantity + "]";
	}
	
}
