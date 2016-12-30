package org.mybatis.generator.paginator2;

public class PageRowBounds {
	
	private Integer pageNo; // 当前页码
	private Integer pageSize; // 每页行数
	private Integer totalRecord; // 总记录数
	private Integer totalPage; // 总页数

	public PageRowBounds() {
	}

	public PageRowBounds(Integer pageNo, Integer pageSize) {
		this.setPageNo(pageNo);
		this.setPageSize(pageSize);
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

}
