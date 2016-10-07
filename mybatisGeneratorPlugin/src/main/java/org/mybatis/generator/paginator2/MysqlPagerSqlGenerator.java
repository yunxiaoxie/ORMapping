package org.mybatis.generator.paginator2;

import org.apache.ibatis.session.RowBounds;

public class MysqlPagerSqlGenerator implements PageSqlGenerator {
	public String getPagerSql(String sql, RowBounds rowBounds) {
		if (rowBounds.getLimit() == Integer.MAX_VALUE) {
			return sql;
		} else {
			return getPagerSql(sql, rowBounds.getOffset(), rowBounds.getLimit());
		}
	}

	public String getPagerSql(String sql, int offset, int limit) {
		return sql + "  limit " + offset	+ "," + limit;
	}

	public String getCountSql(String sql) {
		return "select count(*) totalCount from (" + sql + ") as total";
	}
}