package org.mybatis.generator.paginator2;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }))
public class MysqlPagerPlugin extends AbstractPagerPlugin {
	
	public String getPagerSql(String sql, PageRowBounds pageRowBounds) {
		int offset = (pageRowBounds.getPageNo() - 1) * pageRowBounds.getPageSize();
		return sql + "  limit " + offset + " , " + pageRowBounds.getPageSize();
	}

	public String getCountSql(String sql) {
		return "select count(*) totalCount from (" + sql + ") as total";
	}
}