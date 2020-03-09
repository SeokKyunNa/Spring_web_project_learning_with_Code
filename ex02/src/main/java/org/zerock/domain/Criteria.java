package org.zerock.domain;

public class Criteria {

	private int page;
	private int numPerPage;
	
	public Criteria() {
		
		this.page = 1;
		this.numPerPage = 10;
	}
	
	public void setPage(int page) {
		
		if(page <= 0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setNumPerPage(int numPerPage) {
		
		if(numPerPage <= 0 || numPerPage > 100) {
			this.numPerPage = 10;
			return;
		}
		
		this.numPerPage = numPerPage;
	}
	
	public int getPage() {
		
		return page;
	}
	
	public int getStartRow() {
		
		return ((this.page - 1) * numPerPage) + 1;
	}
	
	public int getEndRow() {
		
		return (((this.page - 1) * numPerPage) + 1 + numPerPage) - 1;
	}
	
	public int getNumPerPage() {
		
		return this.numPerPage;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", numPerPage=" + numPerPage + "]";
	}
	
}
