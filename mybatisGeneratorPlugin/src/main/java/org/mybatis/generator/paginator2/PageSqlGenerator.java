package org.mybatis.generator.paginator2;

import org.apache.ibatis.session.RowBounds;

public interface PageSqlGenerator {
	public String getPagerSql(String sql, RowBounds rowBounds);

	public String getCountSql(String sql);
}