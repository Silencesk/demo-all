/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mine.demo.mybatisplus.config;

import com.baomidou.mybatisplus.MybatisDefaultParameterHandler;
import com.baomidou.mybatisplus.entity.CountOptimize;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.pagination.DialectFactory;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.toolkit.IOUtils;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.mine.demo.mybatisplus.privilege.DataPrivilegeProvider;
import com.mine.demo.mybatisplus.util.SpringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * <p>
 * mabatis查询拦截器，实现 [分页 & 数据权限] 功能
 *
 * </p>
 *
 * @author hubin && liutao
 * @Date 2016-01-23
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MybatisQueryInterceptor implements Interceptor {

	/* 溢出总页数，设置第一页 */
	private boolean overflowCurrent = false;
	/* Count优化方式 */
	private String optimizeType = "default";
	/* 方言类型 */
	private String dialectType;
	/* 方言实现类 */
	private String dialectClazz;

	private DataPrivilegeProvider dataPrivilegeProvider;

	private DataPrivilegeProvider getDataPrivilegeProvider(){
		if (dataPrivilegeProvider != null) {
			return dataPrivilegeProvider;
		}
		return SpringUtils.getBean(DataPrivilegeProvider.class);
	}

	public Object intercept(Invocation invocation) throws Throwable {

		Object target = invocation.getTarget();
		if (target instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) target;
			MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);

			// 若拦截的Statement非select，则不进行分页与数据权限的处理
			SqlCommandType sqlCommandType = (SqlCommandType)metaStatementHandler.getValue("delegate.mappedStatement.sqlCommandType");
			if(sqlCommandType != SqlCommandType.SELECT){
				return invocation.proceed();
			}

			// 分页与数据权限过滤处理
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			String originalSql = (String) boundSql.getSql();
			// @lt：处理数据权限；因切query无法改变其sql，只能切prepare；但只有执行查询语句才进行进行数据权限处理
			originalSql = getDataPrivilegeProvider().handleDataPrivilege(originalSql);

			RowBounds rowBounds = (RowBounds) metaStatementHandler.getValue("delegate.rowBounds");
			/* 不需要分页的场合 */
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				// 重新设置boundSql
				metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
				return invocation.proceed();
			}

			/**
			 * <p>
			 * 分页逻辑
			 * </p>
			 * <p>
			 * 查询总记录数 count
			 * </p>
			 */
			if (rowBounds instanceof Pagination) {
				Pagination page = (Pagination) rowBounds;
				boolean orderBy = true;
				if (page.isSearchCount()) {
					/*
					 * COUNT 查询，去掉 ORDER BY 优化执行 SQL
					 */
					CountOptimize countOptimize = SqlUtils.getCountOptimize(originalSql, optimizeType, dialectType,
							page.isOptimizeCount());
					orderBy = countOptimize.isOrderBy();
				}

				/* 执行 SQL */
				String buildSql = SqlUtils.concatOrderBy(originalSql, page, orderBy);
				originalSql = DialectFactory.buildPaginationSql(page, buildSql, dialectType, dialectClazz);

			}

			/**
			 * 查询 SQL 设置
			 */
			metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
		} else {
			MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
			Object parameterObject = null;
			RowBounds rowBounds = null;
			if (invocation.getArgs().length > 1) {
				parameterObject = invocation.getArgs()[1];
				// TODO 获取分页参数为固定的第二个，这个是否存在问题
				rowBounds = (RowBounds) invocation.getArgs()[2];
			}
			/* 不需要分页的场合 */
			if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
				return invocation.proceed();
			}

			BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
			/*
			 * <p> 禁用内存分页 </p> <p> 内存分页会查询所有结果出来处理（这个很吓人的），如果结果变化频繁这个数据还会不准。
			 * </p>
			 */
			String originalSql = (String) boundSql.getSql();

			/**
			 * <p>
			 * 分页逻辑
			 * </p>
			 * <p>
			 * 查询总记录数 count
			 * </p>
			 */
			if (rowBounds instanceof Pagination) {
				Connection connection = null;
				try {
					Pagination page = (Pagination) rowBounds;
					if (page.isSearchCount()) {
						connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
						/*
						 * COUNT 查询，去掉 ORDER BY 优化执行 SQL
						 */
						CountOptimize countOptimize = SqlUtils.getCountOptimize(originalSql, optimizeType, dialectType,
								page.isOptimizeCount());
						page = this.count(countOptimize.getCountSQL(), connection, mappedStatement, boundSql, page);
						/** 总数 0 跳出执行 */
						if (page.getTotal() <= 0) {
							return invocation.proceed();
						}
					}
				} finally {
					IOUtils.closeQuietly(connection);
				}
			}
		}

		return invocation.proceed();

	}

	/**
	 * 查询总记录条数
	 *
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	public Pagination count(String sql, Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Pagination page) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// @lt：处理数据权限，count sql也需要做数据权限过滤
		sql = getDataPrivilegeProvider().handleDataPrivilege(sql);

		try {
			pstmt = connection.prepareStatement(sql);
			DefaultParameterHandler parameterHandler = new MybatisDefaultParameterHandler(mappedStatement,
					boundSql.getParameterObject(), boundSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			int total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			page.setTotal(total);
			/*
			 * 溢出总页数，设置第一页
			 */
			if (overflowCurrent && (page.getCurrent() > page.getPages())) {
				page = new Pagination(1, page.getSize());
				page.setTotal(total);
			}
		} catch (Exception e) {
			throw new MybatisPlusException("count sql execute error, sql:[" + sql + "]", e);
		} finally {
			IOUtils.closeQuietly(pstmt, rs);
		}
		return page;
	}

	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		}
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		}
		return target;
	}

	public void setProperties(Properties prop) {
		String dialectType = prop.getProperty("dialectType");
		String dialectClazz = prop.getProperty("dialectClazz");
		if (StringUtils.isNotEmpty(dialectType)) {
			this.dialectType = dialectType;
		}
		if (StringUtils.isNotEmpty(dialectClazz)) {
			this.dialectClazz = dialectClazz;
		}
	}

	public void setDialectType(String dialectType) {
		this.dialectType = dialectType;
	}

	public void setDialectClazz(String dialectClazz) {
		this.dialectClazz = dialectClazz;
	}

	public void setOverflowCurrent(boolean overflowCurrent) {
		this.overflowCurrent = overflowCurrent;
	}

	public void setOptimizeType(String optimizeType) {
		this.optimizeType = optimizeType;
	}
}
