package com.mine.demo.mybatisplus.privilege;

import com.mine.demo.mybatisplus.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SqlHandlerUtils {
	
	private SqlHandlerUtils(){
	}
	
	private static final String sql_where="WHERE";
	private static final String SQL_FROM="FROM";

	private static final String sql_left_join="LEFT JOIN ";

	/**
	 * 处理sql，加上过滤条件
	 * @param originSql
	 * @param addSql
	 * @param userId
	 * @param tableName
	 * @param tableField
	 * @return
	 */
	public static String handlerSql(String originSql,String addSql,String userId,String tableName,String tableField){
		originSql=originSql.replace("\t", " ").replace("\n", " ");
		originSql=originSql.replaceAll("\\bfrom\\b", SQL_FROM);		// 将单词from替换为FROM
		originSql=originSql.replaceAll(" where ", " "+sql_where+" ");
		originSql=originSql.replace("left", "LEFT");
		originSql=originSql.replace("join", "JOIN");
		originSql=originSql.replaceAll("LEFT[ ]+JOIN[ ]+", sql_left_join);
		// 添加where 1=1至没有带where的from
		originSql = appendWhereAfterFrom(originSql);
		
		List<String> matcherList = matcherTableSql(originSql, tableName);
		for(String matcherSql:matcherList){
		  if(originSql.contains(sql_left_join + matcherSql) )continue;
		  String privliesql=getPrivilegeSql(matcherSql, tableField, tableName, addSql);
		  String newMatcherSql=mergeSql(matcherSql, privliesql);
		  originSql=originSql.replace(matcherSql, newMatcherSql);
		}
		return originSql;
	}

	public static String appendWhereAfterFrom(String input){
		String[] arr = input.split("(?i)FROM");
		int i = 0;
		StringBuilder sb = new StringBuilder(input.length());
		for (String s : arr){
			if(i++ == 0){
				sb.append(s);
				continue;
			}
			sb.append(" FROM ");
			if(s.matches("(.*)(?i)WHERE(.*)")){
				sb.append(s);
			}else{
				if(s.indexOf(")") >0){
					sb.append(s.replaceFirst("\\)", " WHERE 1=1)"));
				} else if (s.matches("(.*)(?i)\\((.*)SELECT(.*)")){
					sb.append(s);
				}
				else{
					sb.append(s).append(" WHERE 1=1 ");
				}
			}
		}
//		System.out.println("sb="+sb.toString());
		return sb.toString();
	}
	

	public static String matcherPrivilegeSql(String addSql,String userId){
		String newAddSql = addSql.toLowerCase();
		newAddSql = newAddSql.replace("[userid]", userId);
		
		//表名
		String regex = "from[ ,](.*?)where";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(newAddSql);
		while (matcher.find()) {
			String strMa = matcher.group();
			if(strMa.indexOf("inner") >=0){
				//有inner join 多表
			}else if(strMa.indexOf(",") >=0){
				//无inner join，单用多表用","关联
				
			}else{
				//处理表名首字母大写
				String tableName = getAfterFirstWord(strMa, "from");
				String str = tableName.substring(0,1).toLowerCase();
				String newTableName = str+tableName.substring(1);
				String newStrMa = strMa.replace(tableName, newTableName);
				newAddSql = newAddSql.replace(strMa, newStrMa);
			}
		}
		
		return newAddSql;
	}
	
	/**
	 * 获得指定单词后第一个单词
	 * @param str
	 * @param word
	 * @return
	 */
	public static String getAfterFirstWord(String str,String word){
		  String regex = "\\b"+word+"\\b\\s+(\\w+)\\b";
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(str);
	        while (matcher.find()) {
	        	return matcher.group(1).trim();
	        }
	        return "";
	}
	
	/**
	 * 获得指定单词后第二个单词
	 * @param str
	 * @param word
	 * @return
	 */
	public static String getAfterSecondWord(String str,String word){
		 String reg = word+"\\s+[a-zA-Z0-9]+\\s+(\\w+)\\b";
	     Pattern pattern = Pattern.compile(reg);
	     Matcher matcher = pattern.matcher(str);
	     while (matcher.find()) {
	        return matcher.group(1).trim();
	     }
	     return "";
	}
	
	/**
	 * 要查询的属性加上别名
	 * @param originSql
	 * @param tableField
	 * @param tableName
	 * @return
	 */
	public static String getPrivilegeField(String originSql,String tableField,String tableName){
		String  otherName = getAfterFirstWord(originSql,tableName);//解析别名  指定单词后第一个单词
		if(StringUtils.isNotBlank(otherName) && otherName.toLowerCase().equals("as")){//如果第一个单词为 as ，取后面第二个
			otherName=getAfterSecondWord(originSql, tableName);
		}
		
		return  (StringUtils.isBlank(otherName) ||otherName.equals(sql_where))?tableField:otherName+"."+tableField;
	}
	
	/**
	 * 查询条件过滤
	 * @param originSql
	 * @param tableField
	 * @param tableName
	 * @param privilegeSql
	 * @return
	 */
	public static String getPrivilegeSql(String originSql, String tableField,String tableName, String privilegeSql) {
		privilegeSql = getPrivilegeField(originSql, tableField, tableName)
				+ " IN (" + privilegeSql + ") AND ";
		return privilegeSql;
	}
	
	/**
	 * 合并sql
	 * @param originSql
	 * @param privilegeSql
	 * @return
	 */
	public static String mergeSql(String originSql,String privilegeSql){
		return originSql.replace(sql_where, sql_where +" "+privilegeSql);
	}
	
	/**
	 * 配置符合
	 * @param originSql
	 * @param tableName
	 * @return
	 */
	public static List<String> matcherTableSql(String originSql,String tableName){
		 List<String> matcharList = new ArrayList<String>();
		 String regex = tableName + "[ ,](.*?)WHERE";
	     Pattern pattern = Pattern.compile(regex);
	     Matcher matcher = pattern.matcher(originSql);
	     while (matcher.find()) {
	        matcharList.add(matcher.group());
	     }
	     return matcharList;
	}
	
	/**
	 * 查统计sql
	 * @param sql
	 * @return
	 */
	public static String getSumSql(String sql,String sumPropertys){
		if(StringUtils.isBlank(sumPropertys))return sql;
		String annotationSql="";
		if(sql.contains("*/")){
			int index=sql.lastIndexOf("*/");
			annotationSql=sql.substring(0,index+2);
			sql=sql.substring(index+2);
		}
		String sumStr=" SELECT ";
		String[] strArr=sumPropertys.split(",");
		int len=strArr.length-1;
		String propertyName="";
		for(int i=0;i<=len;i++){
			propertyName = CommonUtils.convertJaveBeanStrToUnderLine(strArr[i]);
			sumStr+="sum(sum_table."+propertyName+") "+propertyName;
		    if(i<len){sumStr+=",";}
		 };
		 sql=sumStr+" FROM ("+sql+")sum_table";
		 sql=annotationSql+ sql;
		return sql;
	};
	
//	 public static void main(String[] args) {
//		 String sql="SELECT\r\n"
//		 
//+"bco.line_id, bco.bill_no, bco.bill_type_no, bco.ref_bill_no, bco.ref_bill_type_no, bco.contract_no, bco.brand_no,"
//		+"bco.division_no, bco.order_type, bco.bill_status, bco.bill_status_max, bco.customer_no,bco.customer_type,"
//		+"bco.order_date, bco.currency_no, bco.payment_clause_no, bco.order_source, bco.deliver_date, bco.creator,"
//		+"bco.create_time,"
//		+"bco.modifier,"
//		+"bco.modify_time, bco.auditor, bco.audit_time, bco.remarks,"
//	 
//		 
//		+"bs.division_name as divisionName,"
//		+"bb.brand_cname as brandCname,"
//		+"bm.customer_name as customerName\r\n\t\n\t,\t\n,\t"
//	 
//		+"FROM bl_co bco"
//		+" inner  JOIN bas_brand  as  bb1 ON bco.brand_no=bb.brand_no"
//		+" left JOIN bas_division bs ON bco.division_no=bs.division_no"
//		+" LEFT JOIN  bas_customer bm ON bco.customer_no=bm.customer_no"
//		+" WHERE 1=1";
//	        String str = " SELECT *   FROM bl_co x,bl_co_dtl    WHERE 1=1 ";
////	        String regex = "bl_co(.*?)WHERE";
//	        String addsql="select brand_no from user_brand    as a1_A where userid=[userid]";
//	        
////	        sql=sql.replaceAll("LEFT[ ]+JOIN[ ]+", "LEFT JOIN ");
//	        String reg="user_brand\\s+[a-zA-Z0-9]+\\s+(\\w+)\\b";
//	        String regex = "\\b"+"user_brand"+"\\b\\s+(\\w+)\\b";
//	        //SystemUser s=new SystemUser();
//	        //s.setUserId(1);
//	        //SessionUtils.set(SysConstans.SESSION_USER, s);
//	        ThreadLocals.setAddNoNullFilter("1");
////	        System.out.println( handlerSql(sql, addsql, "bas_brand", "bill_no"));
//	        
//	        //URL url = JsqlparserUtils.class.getResource("sql.json");
//	        String sqlsum="";
//			try {
//				sqlsum = FileUtils.readFileToString(FileUtils.toFile(null),"utf-8");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	        
////		    String ss="aa";
////		    String resultstr="select ";
////		    String[] strArr=ss.split(",");
////		    int len=strArr.length-1;
////		    for(int i=0;i<=len;i++){
////		    	resultstr+="sum("+strArr[i]+") "+strArr[i];
////		    	if(i<len){resultstr+=",";}
////		    };
////		    System.out.println(resultstr);
////		    resultstr+=" from ("+sqlsum+")";
//			
//		    System.out.println(getSumSql(sqlsum,"sizeQty"));
//		    
////	        Pattern pattern = Pattern.compile(reg);
////	        Matcher matcher = pattern.matcher(addsql);
////	        while (matcher.find()) {
////	        	System.out.println( matcher.group(1).trim());
////	        }
//
//	      
//	 }
	
//	public static void main(String[] args) {
//		String str="itg_user_brand";
//		
//		String sf = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
//		System.out.println(sf);
//	}	 
}
