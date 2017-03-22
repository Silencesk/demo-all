package com.mine.demo.mybatisplus.privilege;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户中心统一登陆对象 User的简化
 * @author liutao
 */
@Data
public class SystemUser implements Serializable {
    private static final long serialVersionUID = -8094604492554763459L;

    private Long id;	//用户ID

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * email地址
     */
    private String email;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin = false;

}