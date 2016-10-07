package org.mybatis.generator.paginator2;

import org.apache.ibatis.session.RowBounds;

public class PageRowBounds extends RowBounds {
	public final static int NO_TOTAL_COUNT = -1;
	private int totalCount = NO_TOTAL_COUNT;

	public PageRowBounds() {
		super();
	}

	public PageRowBounds(int offset, int limit) {
		super(offset, limit);
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}
}
