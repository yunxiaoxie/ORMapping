package org.mybatis.generator.paginator3;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }))
public class MysqlPageInterceptor extends AbstractPageInterceptor {

	@Override
	protected String getSelectTotalSql(String targetSql) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);

		int orderByPos = 0;
		if ((orderByPos = sqlBuilder.lastIndexOf(ORDER_BY)) != -1) {
			sqlBuilder.delete(orderByPos, sqlBuilder.length());
		}
		sqlBuilder.insert(0, "select count(1) as _count from ( ").append(" ) as total");

		return sqlBuilder.toString();
	}

	@Override
	protected String getSelectPagingSql(String targetSql, PageBounds bounds) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);

		sqlBuilder.insert(0, "select * from ( ");
		sqlBuilder.append(") as page limit ");
		sqlBuilder.append(bounds.getOffset()).append(",").append(bounds.getLimit());

		return sqlBuilder.toString();
	}

}