package org.mybatis.generator.paginator3;

import java.sql.Connection;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }))
public class OraclePageInterceptor extends AbstractPageInterceptor {

	@Override
	protected String getSelectTotalSql(String targetSql) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);

		int orderByPos = 0;
		if ((orderByPos = sqlBuilder.lastIndexOf(ORDER_BY)) != -1) {
			sqlBuilder.delete(orderByPos, sqlBuilder.length());
		}
		sqlBuilder.insert(0, "select count(1) as _count from ( ").append(" )");

		return sqlBuilder.toString();
	}

	@Override
	protected String getSelectPagingSql(String targetSql, PageBounds bounds) {
		String sql = targetSql.toLowerCase();
		StringBuilder sqlBuilder = new StringBuilder(sql);

		int offset = (bounds.getPageNo() - 1) * bounds.getPageSize();
		int limit = offset + bounds.getPageSize();
		sqlBuilder.insert(0, "select * from ( select table_alias.*, rownum mybatis_rowNo from (");
		sqlBuilder.append(") ");
		sqlBuilder.append("table_alias where rownum <= " + limit).append(")");
		sqlBuilder.append("where mybatis_rowNo >= " + (offset + 1));

		return sqlBuilder.toString();
	}

}