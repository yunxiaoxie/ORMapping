package org.mybatis.generator.paginator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@SuppressWarnings("rawtypes")
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {

		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);

		Page page = getPage(parameter);

		if (page != null) {
			String countSql = getCountSql(boundSql.getSql());
			try (Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource()
					.getConnection();
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					ResultSet rs = countStmt.executeQuery();) {
				int totpage = 0;
				if (rs.next()) {
					totpage = rs.getInt(1);
				}
				page.setTotalRecord(totpage);
			}

			// 对原始Sql追加limit
			int offset = (page.getPageNo() - 1) * page.getPageSize();
			StringBuffer sb = new StringBuffer();
			sb.append(boundSql.getSql()).append(" limit ").append(offset).append(",").append(page.getPageSize());
			BoundSql newBoundSql = copyBoundSql(mappedStatement, boundSql, sb.toString());
			MappedStatement newMs = buildMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		return invocation.proceed();

	}

	/**
	 * 参数是否有Page对象
	 */
	private Page getPage(Object obj) {
		if (obj != null) {
			if (obj instanceof Page) {
				return (Page) obj;
			} else if (obj instanceof Map) {
				for (Object o : ((Map) obj).values()) {
					if (getPage(o) != null) {
						return getPage(o);
					}
				}
			}
		}
		return null;
	}

	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement buildMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
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

	/**
	 * 复制BoundSql对象
	 */
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

	private String getCountSql(String sql) {
		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
	}

	class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties arg0) {
	}

}
