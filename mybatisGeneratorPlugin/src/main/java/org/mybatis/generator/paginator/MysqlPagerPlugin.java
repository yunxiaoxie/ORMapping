package org.mybatis.generator.paginator;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }))
public class MysqlPagerPlugin extends AbstractPagePlugin {

	public String getPagerSql(String sql, int pageNo, int pageSize) {
		int offset = (pageNo - 1) * pageSize;
		return sql + "  limit " + offset + " , " + pageSize;
	}

	public String getCountSql(String sql) {
		return "select count(*) totalCount from (" + sql + ") as total";
	}
}