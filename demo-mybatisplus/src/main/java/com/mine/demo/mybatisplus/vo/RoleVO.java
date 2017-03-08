package com.mine.demo.mybatisplus.vo;

import lombok.Data;

/**
 * role
 */
@Data
public class RoleVO {
    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;


	/**
     * 启用状态(0=禁用 1=启用)
     */
    private Integer enableFlag;

    /**
     * 备注
     */
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * {@linkplain #roleName}
     *
     * @return the value of itg_role_list.role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 
     * {@linkplain #roleName}
     * @param roleName the value for itg_role_list.role_name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 
     * {@linkplain #enableFlag}
     *
     * @return the value of itg_role_list.enable_flag
     */
    public Integer getEnableFlag() {
        return enableFlag;
    }

    /**
     * 
     * {@linkplain #enableFlag}
     * @param enableFlag the value for itg_role_list.enable_flag
     */
    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }


    /**
     * 
     * {@linkplain #remarks}
     *
     * @return the value of itg_role_list.remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 
     * {@linkplain #remarks}
     * @param remarks the value for itg_role_list.remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}