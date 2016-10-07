package org.mybatis.generator.paginator2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@SuppressWarnings("rawtypes")
@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }))
public class PagePlugin implements Interceptor {
	private static PageSqlGenerator sqlGenerator = new MysqlPagerSqlGenerator();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Object[] args = invocation.getArgs();
		RowBounds rowBounds = (RowBounds) args[2];
		if (rowBounds instanceof PageRowBounds) {
			Executor executor = (Executor) invocation.getTarget();
			MappedStatement mappedStatement = (MappedStatement) args[0];
			Object parameter = args[1];
			PageRowBounds pageRowBounds = (PageRowBounds) args[2];
			ResultHandler resultHandler = (ResultHandler) args[3];
			BoundSql boundSql = mappedStatement.getBoundSql(parameter);
			PageSqlSqlSource sqlSource = new PageSqlSqlSource(boundSql);
			MappedStatement statement = buildMappedStatement(mappedStatement, sqlSource);
			MetaObject msObject = SystemMetaObject.forObject(statement);
			msObject.setValue("sqlSource.boundSql.sql", sqlGenerator.getPagerSql(boundSql.getSql(), rowBounds));
			args[0] = statement;
			StatementHandler statementHandler = mappedStatement.getConfiguration().newStatementHandler(executor,
					mappedStatement, parameter, rowBounds, resultHandler, boundSql);
			ResultSet rs = null;
			try (PreparedStatement stmt = executor.getTransaction().getConnection()
					.prepareStatement(sqlGenerator.getCountSql(boundSql.getSql()))) {
				statementHandler.parameterize(stmt);
				rs = stmt.executeQuery();
				int totalCount = 0;
				if (rs.next()) {
					totalCount = rs.getInt("totalCount");
				}
				pageRowBounds.setTotalCount(totalCount);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (Throwable ignored) {
					}
				}
			}
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
