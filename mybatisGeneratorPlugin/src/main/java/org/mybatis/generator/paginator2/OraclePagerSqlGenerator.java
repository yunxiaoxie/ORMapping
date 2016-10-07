package org.mybatis.generator.paginator2;

import org.apache.ibatis.session.RowBounds;

public class OraclePagerSqlGenerator implements PageSqlGenerator {
	public String getPagerSql(String sql, RowBounds rowBounds) {
		if (rowBounds.getLimit() == Integer.MAX_VALUE) {
			return sql;
		} else {
			return getPagerSql(sql, rowBounds.getOffset(), rowBounds.getLimit());
		}
	}

	public String getPagerSql(String sql, int offset, int limit) {
		return "select * from (select inrow.*, rownum rn from (" + sql + ") inrow where rownum <=" + (limit + offset)
				+ ") where rn >= " + offset;
	}

	public String getCountSql(String sql) {
		return "select count(*) totalCount from (" + sql + ")";
	}
}