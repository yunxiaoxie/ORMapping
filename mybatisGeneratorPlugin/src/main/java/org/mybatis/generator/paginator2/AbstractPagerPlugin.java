package org.mybatis.generator.paginator2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

public abstract class AbstractPagerPlugin implements Interceptor {

	/**
	 * build pager SQL.
	 */
	public abstract String getPagerSql(String sql, PageRowBounds pageRowBounds);

	/**
	 * build count SQL.
	 */
	public abstract String getCountSql(String sql);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object parameter = invocation.getArgs()[1];
		if (parameter instanceof PageRowBounds) {
			Executor executor = (Executor) invocation.getTarget();
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			PageRowBounds pageRowBounds = (PageRowBounds) parameter;
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			// get total count by local SQL.
			try (PreparedStatement stmt = executor.getTransaction().getConnection()
					.prepareStatement(getCountSql(boundSql.getSql())); 
					ResultSet rs = stmt.executeQuery();) {
				int totalCount = 0;
				if (rs.next()) {
					totalCount = rs.getInt("totalCount");
				}
				pageRowBounds.setTotalRecord(totalCount);
			}
			// update statement by local SQL.
			BoundSql newBoundSql = copyBoundSql(mappedStatement, boundSql, getPagerSql(boundSql.getSql(), pageRowBounds));
			MappedStatement statement = buildMappedStatement(mappedStatement, new PageSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = statement;
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	private BoundSql copyBoundSql(MappedStatement ms, BoundSql boundSql, String countSql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	@Override
	public void setProperties(Properties properties) {
	}

	private class PageSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public PageSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	private MappedStatement buildMappedStatement(MappedStatement ms, SqlSource sqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource,
				ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		return builder.build();
	}
}
