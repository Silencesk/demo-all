package com.mine.demo.mybatisplus.util;


/**
 * Description: 本地线程变量集合
 * author:      liutao
 * Createdate:  2016年9月14日下午4:57:28
 */
public class ThreadLocals {
    /**
     * 资源号
     * 改为可继承的本地线程,解决异步后本地线程存入的模块值变为空,数据权限设置失效问题
     */
    public static final InheritableThreadLocal<String> resCodeThl
            = new InheritableThreadLocal<String>();
    /**
     * 求和统计属性s
     * 需要统计的属性名，多个用逗号隔开
     */
    public static final ThreadLocal<String> summaryThl = new ThreadLocal<String>();

    /**
     * 获取资源号
     *
     * @return 资源号
     */
    public static String getResCode() {
        return resCodeThl.get();
    }

    /**
     * 设置资源号
     *
     * @param resCode
     */
    public static void setResCode(String resCode) {
        resCodeThl.set(resCode);
    }

    /**
     * 获取求和统计属性s
     *
     * @return
     */
    public static String getSumPropertys() {
        return summaryThl.get();
    }

    /**
     * 设置求和统计属性s
     *
     * @param sumPropertys
     */
    public static void setSumPropertys(String sumPropertys) {
        summaryThl.set(sumPropertys);
    }


}
