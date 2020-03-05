package org.zerock.domain;

public class PageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	
	private int displayPageNum = 10;	// 현재 화면에 보여줄 페이지의 개수
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	private void calcData() {
		
		// 현재 페이지 기준 마지막 페이지 계산
		endPage = (int)(Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		
		// 현재 페이지 기준 첫번째 페이지 계산
		startPage = (endPage - displayPageNum) + 1;
		
		// 전체 데이터상에서 마지막 페이지
		int tempEndPage = (int)(Math.ceil(totalCount/ (double)cri.getNumPerPage()));
		
		// 전체 데이터상의 마지막 페이지와 현재 데이터상의 마지막 페이지를 비교하여 마지막 페이지 설정
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// 현재 페이지 기준 첫번째 페이지가 1페이지가 맞는지 확인
		prev = startPage == 1 ? false : true;
		
		// 현재 페이지 기준 마지막 페이지가 마지막 페이지가 맞는지 확인
		next = endPage * cri.getNumPerPage() >= totalCount ? false : true;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public Criteria getCri() {
		return cri;
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}
	
}
