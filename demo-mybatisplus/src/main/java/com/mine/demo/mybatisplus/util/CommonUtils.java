package com.mine.demo.mybatisplus.util;

import com.mine.demo.mybatisplus.privilege.SystemUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liutao
 * @create 2017-03-17 下午3:58
 */

public class CommonUtils {

    private CommonUtils(){};

    /**
     * javabean驼峰格式转下划线格式 如：testName test_name
     *
     * @param javeBeanStr
     * @return
     */
    public static String convertJaveBeanStrToUnderLine(String javeBeanStr) {
        StringBuffer buf = new StringBuffer();
        Pattern p = Pattern.compile("[A-Z]");
        Matcher m = p.matcher(javeBeanStr);
        while (m.find()) {
            m.appendReplacement(buf, "_" + m.group(0));
        }
        m.appendTail(buf);
        return buf.toString().toLowerCase();
    }

    /**
     * 下划线格式转javabean驼峰格式 如： test_name testName
     *
     * @param underLineStr
     * @return
     */
    public static String convertUnderLineStrToJaveBean(String underLineStr) {
        StringBuffer buf = new StringBuffer(underLineStr);
        Matcher mc = Pattern.compile("_").matcher(underLineStr);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            // 如果'_'为最后的字符,则直接退出循环,解决db2中'rownumber_'字符串数组越界问题
            if (position == underLineStr.length()) {
                break;
            }
            buf.replace(position - 1, position + 1, buf.substring(position, position + 1).toUpperCase());
        }
        return buf.toString();
    }

    public static SystemUser getPrivilegedUser(){
        SystemUser user = new SystemUser();
        user.setUserCode("lt");
        user.setUserName("liutao");
        user.setIsAdmin(false);
        user.setId(Long.valueOf(99));
        return user;
    }
}
