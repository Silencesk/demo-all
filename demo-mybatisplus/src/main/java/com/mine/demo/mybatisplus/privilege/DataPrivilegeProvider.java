package com.mine.demo.mybatisplus.privilege;

import com.google.common.collect.Lists;
import com.mine.demo.mybatisplus.mapper.DataPrivilegeDtlMapper;
import com.mine.demo.mybatisplus.mapper.DataPrivilegeMapper;
import com.mine.demo.mybatisplus.util.CommonUtils;
import com.mine.demo.mybatisplus.util.ThreadLocals;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DataPrivilegeProvider {
	private static final Logger logger = LoggerFactory.getLogger(DataPrivilegeProvider.class);

	@Autowired
	DataPrivilegeMapper dataPrivilegeService;

	@Autowired
	DataPrivilegeDtlMapper dataPrivilegeDtlService;

	/**
	 * 缓存数据的有效期
	 */
	private static Long redis_key_exp = 2 * 60 * 60L;
	private static final List<String> excluedTables = Lists.newArrayList();

	static {
		excluedTables.add("data_privilege");
		excluedTables.add("data_privilege_dtl");
	}

	/**
	 * 
	 * @param orgSql SQL语句
	 * @return 处理后的SQL语句，数据权限部分为子查询，like
	 * 	SELECT test_id AS id,`name`,age,test_type AS testType,`role`,phone,dept_no AS deptNo FROM user
	 * 	WHERE dept_no in (select dept_no from user_department where user_id=99)
	 * 	AND (name='liutao' AND test_id='99') LIMIT 0,12
	 */
	public String handleDataPrivilege(String orgSql) {
		// 明确可以排除的一些表，可以不进行后面数据权限逻辑判断
		if(checkTableIsExclued(orgSql)){
			return orgSql;
		}

		String resCode = ThreadLocals.getResCode();
		SystemUser systemUser = CommonUtils.getPrivilegedUser();

		logger.debug(">>>>>>>   DataPrivilegeProvider.handleDataPrivilege   >>>>>>>");
		logger.debug(">>>>resCode:"+ resCode);
		logger.debug(">>>>systemUser:"+ systemUser.toString());

		// 参数校验
		if(StringUtils.isBlank(resCode) || systemUser == null || systemUser.getIsAdmin() == true){
			return orgSql;
		}

		List<DataPrivilegeVO> dataPrivList = getDataPrivileges();
		
		for(DataPrivilegeVO data : dataPrivList){
			String useModuleList = data.getUseResCodeList();
			if(useModuleList == null || !useModuleList.contains(resCode)){
				continue;
			}
			//权限过滤
			String newAddSql = data.getPrivilegeSql().toLowerCase();
			orgSql = handleDataPrivilege(data.getId()+"", resCode, systemUser.getId()+"", orgSql, newAddSql);
		}
		return orgSql;
	}


	private boolean checkTableIsExclued(String orgSql){
		for (String s: excluedTables) {
			if(orgSql.contains(s)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据表名拼接过滤SQL
	 * @param privilegeNo 权限号
	 * @param resCode 模块号
	 * @param orgSql 持久的HQL语句
	 * @param addSql 替换的SQL
	 * @return
	 */
	private String handleDataPrivilege(String privilegeNo, String resCode, String userId, String orgSql, String addSql){
		List<DataPrivilegeDtlVO> privilegeDtls = getDataPrivilegeDtl(privilegeNo,resCode);
		
		for(DataPrivilegeDtlVO vo : privilegeDtls){
			//数据权限增加原生SQL支持
			String tableName = vo.getTableName();
			String columnName = vo.getColumnName();
			if(StringUtils.isBlank(tableName) || StringUtils.isBlank(columnName)){
				continue;
			}
			
			if(orgSql.toLowerCase().contains(tableName.toLowerCase()) && StringUtils.isNotBlank(addSql)){
				//查询到结果就进行过滤
				addSql = SqlHandlerUtils.matcherPrivilegeSql(addSql, userId);
				orgSql = SqlHandlerUtils.handlerSql(orgSql, addSql,userId,tableName, columnName);
			}
		}
		return orgSql;
	}
	
	/**
	 * 获取权限配置信息
	 * 1.先从缓存取数据,如果缓存没有则去数据库取
	 * @param privilegeNo 权限编号
	 * @return
	 */
	private List<DataPrivilegeDtlVO> getDataPrivilegeDtl(String privilegeNo, String resCode){
		List<DataPrivilegeDtlVO> list = null;
		// TODO 从缓存取
		// 数据库
		if(list == null && dataPrivilegeService != null){
			list = dataPrivilegeDtlService.getDataPrivilegeDtl(privilegeNo, resCode);
			// TODO 存缓存
		}
		return (list==null ? new ArrayList<DataPrivilegeDtlVO>():list);
	}
	
	/**
	 * 获取数据权限配置表</p>
	 * 1.先从缓存取数据,如果缓存没有则去数据库取
	 * @return
	 */
	private List<DataPrivilegeVO> getDataPrivileges(){
		List<DataPrivilegeVO> list = null;
		// TODO 从缓存取

		//数据库
		if(list == null && dataPrivilegeService != null){
			list = dataPrivilegeService.getDataPrivileges();
			// TODO 存缓存

		}
		
		return (list==null ? new ArrayList<DataPrivilegeVO>():list);
	}
}
