package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:11:15
 * @Description: www.ciaj.com gen PO
 */
public class SysRolePo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.name
    * 注释：角色名 
    */
    private String name;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.code
    * 注释：角色编码 
    */
    private String code;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.type
    * 注释：角色类型 
    */
    private String type;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.description
    * 注释：角色描述 
    */
    private String description;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.available
    * 注释：角色是否可用（Y/N） 
    */
    private String available;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_role.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;


    /**
    * 注释：主键 
    * @return the value of sys_role.id
    */
    public String getId() {
         return id;
    }

    /**
    * 注释：主键 
    * @param id the value of sys_role.id
    */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    /**
    * 注释：角色名 
    * @return the value of sys_role.name
    */
    public String getName() {
         return name;
    }

    /**
    * 注释：角色名 
    * @param name the value of sys_role.name
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    /**
    * 注释：角色编码 
    * @return the value of sys_role.code
    */
    public String getCode() {
         return code;
    }

    /**
    * 注释：角色编码 
    * @param code the value of sys_role.code
    */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    /**
    * 注释：角色类型 
    * @return the value of sys_role.type
    */
    public String getType() {
         return type;
    }

    /**
    * 注释：角色类型 
    * @param type the value of sys_role.type
    */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    /**
    * 注释：角色描述 
    * @return the value of sys_role.description
    */
    public String getDescription() {
         return description;
    }

    /**
    * 注释：角色描述 
    * @param description the value of sys_role.description
    */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    /**
    * 注释：角色是否可用（Y/N） 
    * @return the value of sys_role.available
    */
    public String getAvailable() {
         return available;
    }

    /**
    * 注释：角色是否可用（Y/N） 
    * @param available the value of sys_role.available
    */
    public void setAvailable(String available) {
        this.available = available == null ? null : available.trim();
    }
    /**
    * 注释：创建人 
    * @return the value of sys_role.create_at
    */
    public String getCreateAt() {
         return createAt;
    }

    /**
    * 注释：创建人 
    * @param createAt the value of sys_role.create_at
    */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }
    /**
    * 注释：创建时间 
    * @return the value of sys_role.create_time
    */
    public Date getCreateTime() {
         return createTime;
    }

    /**
    * 注释：创建时间 
    * @param createTime the value of sys_role.create_time
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
    * 注释：更新人 
    * @return the value of sys_role.update_at
    */
    public String getUpdateAt() {
         return updateAt;
    }

    /**
    * 注释：更新人 
    * @param updateAt the value of sys_role.update_at
    */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }
    /**
    * 注释：更新时间 
    * @return the value of sys_role.update_time
    */
    public Date getUpdateTime() {
         return updateTime;
    }

    /**
    * 注释：更新时间 
    * @param updateTime the value of sys_role.update_time
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
    * 注释：删除标记 
    * @return the value of sys_role.del_flag
    */
    public String getDelFlag() {
         return delFlag;
    }

    /**
    * 注释：删除标记 
    * @param delFlag the value of sys_role.del_flag
    */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @return the value of sys_role.version
    */
    public Integer getVersion() {
         return version;
    }

    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @param version the value of sys_role.version
    */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
