package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 09:20:31
 * @Description: www.ciaj.com gen PO
 */
public class SysDeptPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.name
    * 注释：部门名称 
    */
    private String name;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.code
    * 注释：部门编码 
    */
    private String code;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.area_id
    * 注释：城市ID 
    */
    private String areaId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.level
    * 注释：级别 
    */
    private Integer level;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.type
    * 注释：类型： 
    */
    private String type;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.parent_id
    * 注释：父级 
    */
    private String parentId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.parent_ids
    * 注释：部门父级id集合 
    */
    private String parentIds;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.sequence
    * 注释：排序 
    */
    private Integer sequence;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.enabled
    * 注释：是否可用Y/N 
    */
    private String enabled;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_dept.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;


    /**
    * 注释：主键 
    * @return the value of sys_dept.id
    */
    public String getId() {
         return id;
    }

    /**
    * 注释：主键 
    * @param id the value of sys_dept.id
    */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    /**
    * 注释：部门名称 
    * @return the value of sys_dept.name
    */
    public String getName() {
         return name;
    }

    /**
    * 注释：部门名称 
    * @param name the value of sys_dept.name
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    /**
    * 注释：部门编码 
    * @return the value of sys_dept.code
    */
    public String getCode() {
         return code;
    }

    /**
    * 注释：部门编码 
    * @param code the value of sys_dept.code
    */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    /**
    * 注释：城市ID 
    * @return the value of sys_dept.area_id
    */
    public String getAreaId() {
         return areaId;
    }

    /**
    * 注释：城市ID 
    * @param areaId the value of sys_dept.area_id
    */
    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }
    /**
    * 注释：级别 
    * @return the value of sys_dept.level
    */
    public Integer getLevel() {
         return level;
    }

    /**
    * 注释：级别 
    * @param level the value of sys_dept.level
    */
    public void setLevel(Integer level) {
        this.level = level;
    }
    /**
    * 注释：类型： 
    * @return the value of sys_dept.type
    */
    public String getType() {
         return type;
    }

    /**
    * 注释：类型： 
    * @param type the value of sys_dept.type
    */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    /**
    * 注释：父级 
    * @return the value of sys_dept.parent_id
    */
    public String getParentId() {
         return parentId;
    }

    /**
    * 注释：父级 
    * @param parentId the value of sys_dept.parent_id
    */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
    /**
    * 注释：部门父级id集合 
    * @return the value of sys_dept.parent_ids
    */
    public String getParentIds() {
         return parentIds;
    }

    /**
    * 注释：部门父级id集合 
    * @param parentIds the value of sys_dept.parent_ids
    */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }
    /**
    * 注释：排序 
    * @return the value of sys_dept.sequence
    */
    public Integer getSequence() {
         return sequence;
    }

    /**
    * 注释：排序 
    * @param sequence the value of sys_dept.sequence
    */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    /**
    * 注释：是否可用Y/N 
    * @return the value of sys_dept.enabled
    */
    public String getEnabled() {
         return enabled;
    }

    /**
    * 注释：是否可用Y/N 
    * @param enabled the value of sys_dept.enabled
    */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }
    /**
    * 注释：创建人 
    * @return the value of sys_dept.create_at
    */
    public String getCreateAt() {
         return createAt;
    }

    /**
    * 注释：创建人 
    * @param createAt the value of sys_dept.create_at
    */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }
    /**
    * 注释：创建时间 
    * @return the value of sys_dept.create_time
    */
    public Date getCreateTime() {
         return createTime;
    }

    /**
    * 注释：创建时间 
    * @param createTime the value of sys_dept.create_time
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
    * 注释：更新人 
    * @return the value of sys_dept.update_at
    */
    public String getUpdateAt() {
         return updateAt;
    }

    /**
    * 注释：更新人 
    * @param updateAt the value of sys_dept.update_at
    */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }
    /**
    * 注释：更新时间 
    * @return the value of sys_dept.update_time
    */
    public Date getUpdateTime() {
         return updateTime;
    }

    /**
    * 注释：更新时间 
    * @param updateTime the value of sys_dept.update_time
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
    * 注释：删除标记 
    * @return the value of sys_dept.del_flag
    */
    public String getDelFlag() {
         return delFlag;
    }

    /**
    * 注释：删除标记 
    * @param delFlag the value of sys_dept.del_flag
    */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @return the value of sys_dept.version
    */
    public Integer getVersion() {
         return version;
    }

    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @param version the value of sys_dept.version
    */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
