package tk.mybatis.springboot.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "uc_role")
public class UcRole {
    /**
     * ID
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色拥有者(指定为一个用户的user_code)
     */
    @Column(name = "role_owner")
    private String roleOwner;

    /**
     * 状态
     */
    private String status;

    /**
     * 建档人
     */
    private String creator;

    /**
     * 建档时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return role_name - 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取角色拥有者(指定为一个用户的user_code)
     *
     * @return role_owner - 角色拥有者(指定为一个用户的user_code)
     */
    public String getRoleOwner() {
        return roleOwner;
    }

    /**
     * 设置角色拥有者(指定为一个用户的user_code)
     *
     * @param roleOwner 角色拥有者(指定为一个用户的user_code)
     */
    public void setRoleOwner(String roleOwner) {
        this.roleOwner = roleOwner;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取建档人
     *
     * @return creator - 建档人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置建档人
     *
     * @param creator 建档人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 获取建档时间
     *
     * @return create_time - 建档时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置建档时间
     *
     * @param createTime 建档时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}