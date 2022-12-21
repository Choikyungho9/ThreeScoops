package com.threescoops.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor

public class OrderInfo {
	private int orderNo;
	//private String orderId; //???
	private Customer orderC;
	private java.util.Date orderDt;
	private List<OrderLine> lines;
	@Override
	public String toString() {
		return "OrderInfo [orderNo=" + orderNo + ", orderC=" + orderC + ", orderDt=" + orderDt + "]";
	}
}
