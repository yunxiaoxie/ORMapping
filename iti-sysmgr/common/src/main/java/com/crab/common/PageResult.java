package com.crab.common;

import java.util.List;

public class PageResult<T> {
	
	/**
	 * 当前页码，从1开始
	 */
	private Integer pageNo;
	/**
	 * 每页行数
	 */
	private Integer pageSize;
	/**
	 * 总记录
	 */
	private Integer totalRecord;
	/**
	 * 总页数
	 */
	private Integer totalPage;
	/**
	 * 数据行
	 */
	private List<T> data;
	
	/**
	 * 计算总页数.
	 */
	public void calcPages() {
		if (totalRecord > 0 && pageSize > 0) {
			if (totalRecord % pageSize == 0) {
				setTotalPage(totalRecord / pageSize);
			}
			if (totalRecord % pageSize > 0) {
				setTotalPage(totalRecord / pageSize + 1);
			}
		}
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

}
