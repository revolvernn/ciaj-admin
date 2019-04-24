package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import java.util.Date;
/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 16:54:59
 * @Description: www.ciaj.com PO
 */
public class SysRolePermissionRelPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.role_id
    * 注释：角色id 
    */
    private String roleId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.permission_id
    * 注释：权限id 
    */
    private String permissionId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role_permission_rel.del_flag
    * 注释：删除标记 
    */
    private String delFlag;


    /**
    * 注释：主键 
    * @return the value of sys_role_permission_rel.id
    */
    public String getId() {
         return id;
    }

    /**
    * 注释：主键 
    * @param id the value of sys_role_permission_rel.id
    */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    /**
    * 注释：角色id 
    * @return the value of sys_role_permission_rel.role_id
    */
    public String getRoleId() {
         return roleId;
    }

    /**
    * 注释：角色id 
    * @param roleId the value of sys_role_permission_rel.role_id
    */
    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
    /**
    * 注释：权限id 
    * @return the value of sys_role_permission_rel.permission_id
    */
    public String getPermissionId() {
         return permissionId;
    }

    /**
    * 注释：权限id 
    * @param permissionId the value of sys_role_permission_rel.permission_id
    */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }
    /**
    * 注释：创建人 
    * @return the value of sys_role_permission_rel.create_at
    */
    public String getCreateAt() {
         return createAt;
    }

    /**
    * 注释：创建人 
    * @param createAt the value of sys_role_permission_rel.create_at
    */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }
    /**
    * 注释：创建时间 
    * @return the value of sys_role_permission_rel.create_time
    */
    public Date getCreateTime() {
         return createTime;
    }

    /**
    * 注释：创建时间 
    * @param createTime the value of sys_role_permission_rel.create_time
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
    * 注释：更新人 
    * @return the value of sys_role_permission_rel.update_at
    */
    public String getUpdateAt() {
         return updateAt;
    }

    /**
    * 注释：更新人 
    * @param updateAt the value of sys_role_permission_rel.update_at
    */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }
    /**
    * 注释：更新时间 
    * @return the value of sys_role_permission_rel.update_time
    */
    public Date getUpdateTime() {
         return updateTime;
    }

    /**
    * 注释：更新时间 
    * @param updateTime the value of sys_role_permission_rel.update_time
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
    * 注释：删除标记 
    * @return the value of sys_role_permission_rel.del_flag
    */
    public String getDelFlag() {
         return delFlag;
    }

    /**
    * 注释：删除标记 
    * @param delFlag the value of sys_role_permission_rel.del_flag
    */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}
