package org.mybatis.generator.paginator3;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

public abstract class AbstractPageInterceptor implements Interceptor {

	private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

	private static final String FIELD_DELEGATE = "delegate";

	private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";

	private static final String FIELD_SQL = "sql";

	private static final String BLANK = " ";

	public static final String SELECT = "select";

	public static final String FROM = "from";

	public static final String ORDER_BY = "order by";

	public static final String UNION = "union";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Connection connection = (Connection) invocation.getArgs()[0];
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();

		StatementHandler handler = (StatementHandler) readField(statementHandler, FIELD_DELEGATE);
		PageBounds pageBounds = (PageBounds)handler.getParameterHandler().getParameterObject();
		MappedStatement mappedStatement = (MappedStatement) readField(handler, FIELD_MAPPEDSTATEMENT);
		BoundSql boundSql = handler.getBoundSql();

		// replace all blank
		String targetSql = replaceSqlBlank(boundSql.getSql());

		// get totalRecord
		getTotalAndSetInPagingBounds(targetSql, boundSql, pageBounds, mappedStatement, connection);
		// get page data
		String pagingSql = getSelectPagingSql(targetSql, pageBounds);
		writeDeclaredField(boundSql, FIELD_SQL, pagingSql);

		// ensure set to default
		// pageBounds.setMeToDefault();
		return invocation.proceed();
	}

	private void getTotalAndSetInPagingBounds(String targetSql, BoundSql boundSql, PageBounds bounds,
			MappedStatement mappedStatement, Connection connection) throws SQLException {
		try (PreparedStatement pstmt = connection.prepareStatement(getSelectTotalSql(targetSql));
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				bounds.setTotalRecord(totalRecord);
			}
		}
	}

	protected abstract String getSelectTotalSql(String targetSql);

	protected abstract String getSelectPagingSql(String targetSql, PageBounds pagingBounds);

	private String replaceSqlBlank(String originalSql) {
		Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
		return matcher.replaceAll(BLANK);
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof RoutingStatementHandler) {
			try {
				Field delegate = getField(RoutingStatementHandler.class, FIELD_DELEGATE);
				StatementHandler handler = (StatementHandler) delegate.get(target);
				//RowBounds rowBounds = (RowBounds) readField(handler, FIELD_ROWBOUNDS);
				Object parameter = handler.getParameterHandler().getParameterObject();
				if (parameter instanceof PageBounds) {
					return Plugin.wrap(target, this);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return target;
	}

	private void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
		if (target == null) {
			throw new IllegalArgumentException("target object must not be null");
		}
		Class<?> cls = target.getClass();
		Field field = getField(cls, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Cannot locate declared field " + cls.getName() + "." + fieldName);
		}
		field.set(target, value);
	}

	private Object readField(Object target, String fieldName) throws IllegalAccessException {
		if (target == null) {
			throw new IllegalArgumentException("target object must not be null");
		}
		Class<?> cls = target.getClass();
		Field field = getField(cls, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Cannot locate field " + fieldName + " on " + cls);
		}
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		return field.get(target);
	}

	private static Field getField(final Class<?> cls, String fieldName) {
		for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
			try {
				Field field = acls.getDeclaredField(fieldName);
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
					return field;
				}
			} catch (NoSuchFieldException ex) {
				// ignore
			}
		}
		return null;
	}

	@Override
	public void setProperties(Properties properties) {

	}
}
