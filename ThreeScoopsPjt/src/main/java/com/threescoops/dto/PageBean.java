package com.threescoops.dto;

import java.util.List;

import com.threescoops.vo.Product;

public class PageBean<T> {
	private int currentPage;//현재페이지
	public static final int CNT_PER_PAGE = 4; //페이지당 보여줄 상품수
	private List<T> list; //페이지에 해당하는 목록
	private int totalCnt;  //총목록수
	private int totalPage; //총페이지수
	private int cntPerPageGroup = 3; //페이지목록 그룹수
	private int startPage; //페이지그룹의 시작페이지
	private int endPage; //페이지그룹의 끝페이지
	public PageBean(int currentPage, List<T>list, int totalCnt) {
		this.currentPage = currentPage;
		this.list = list;
		this.totalCnt = totalCnt;
		this.totalPage = (int)(Math.ceil((double)totalCnt/CNT_PER_PAGE));
		startPage = (currentPage-1)/cntPerPageGroup*cntPerPageGroup+1;
		endPage = startPage + cntPerPageGroup -1;
		if(totalPage < endPage) {
			endPage = totalPage;
		}
	}
	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getCntPerPage() {
		return CNT_PER_PAGE;
	}
	
	public List<T> getList() {
		return list;
	}
	
	public int getTotalCnt() {
		return totalCnt;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public int getCntPerPageGroup() {
		return cntPerPageGroup;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
}
