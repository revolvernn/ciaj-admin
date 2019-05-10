package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:00:46
 * @Description: www.ciaj.com gen PO
 */
public class SysMenuPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.name
    * 注释：name 
    */
    private String name;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.parent_id
    * 注释：父级 
    */
    private String parentId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.parent_ids
    * 注释：父节点集合 
    */
    private String parentIds;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.url
    * 注释：菜单url 
    */
    private String url;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.permission_code
    * 注释：权限码 
    */
    private String permissionCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.type
    * 注释：类型：0\目录   1\菜单   2\按钮 
    */
    private String type;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.icon
    * 注释：图标 
    */
    private String icon;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.sequence
    * 注释：排序 
    */
    private Integer sequence;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.enabled
    * 注释：是否启用：Y/N 
    */
    private String enabled;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_menu.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;


    /**
    * 注释：主键 
    * @return the value of sys_menu.id
    */
    public String getId() {
         return id;
    }

    /**
    * 注释：主键 
    * @param id the value of sys_menu.id
    */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    /**
    * 注释：name 
    * @return the value of sys_menu.name
    */
    public String getName() {
         return name;
    }

    /**
    * 注释：name 
    * @param name the value of sys_menu.name
    */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    /**
    * 注释：父级 
    * @return the value of sys_menu.parent_id
    */
    public String getParentId() {
         return parentId;
    }

    /**
    * 注释：父级 
    * @param parentId the value of sys_menu.parent_id
    */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
    /**
    * 注释：父节点集合 
    * @return the value of sys_menu.parent_ids
    */
    public String getParentIds() {
         return parentIds;
    }

    /**
    * 注释：父节点集合 
    * @param parentIds the value of sys_menu.parent_ids
    */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }
    /**
    * 注释：菜单url 
    * @return the value of sys_menu.url
    */
    public String getUrl() {
         return url;
    }

    /**
    * 注释：菜单url 
    * @param url the value of sys_menu.url
    */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    /**
    * 注释：权限码 
    * @return the value of sys_menu.permission_code
    */
    public String getPermissionCode() {
         return permissionCode;
    }

    /**
    * 注释：权限码 
    * @param permissionCode the value of sys_menu.permission_code
    */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode == null ? null : permissionCode.trim();
    }
    /**
    * 注释：类型：0\目录   1\菜单   2\按钮 
    * @return the value of sys_menu.type
    */
    public String getType() {
         return type;
    }

    /**
    * 注释：类型：0\目录   1\菜单   2\按钮 
    * @param type the value of sys_menu.type
    */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
    /**
    * 注释：图标 
    * @return the value of sys_menu.icon
    */
    public String getIcon() {
         return icon;
    }

    /**
    * 注释：图标 
    * @param icon the value of sys_menu.icon
    */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }
    /**
    * 注释：排序 
    * @return the value of sys_menu.sequence
    */
    public Integer getSequence() {
         return sequence;
    }

    /**
    * 注释：排序 
    * @param sequence the value of sys_menu.sequence
    */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    /**
    * 注释：是否启用：Y/N 
    * @return the value of sys_menu.enabled
    */
    public String getEnabled() {
         return enabled;
    }

    /**
    * 注释：是否启用：Y/N 
    * @param enabled the value of sys_menu.enabled
    */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }
    /**
    * 注释：创建人 
    * @return the value of sys_menu.create_at
    */
    public String getCreateAt() {
         return createAt;
    }

    /**
    * 注释：创建人 
    * @param createAt the value of sys_menu.create_at
    */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }
    /**
    * 注释：创建时间 
    * @return the value of sys_menu.create_time
    */
    public Date getCreateTime() {
         return createTime;
    }

    /**
    * 注释：创建时间 
    * @param createTime the value of sys_menu.create_time
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
    * 注释：更新人 
    * @return the value of sys_menu.update_at
    */
    public String getUpdateAt() {
         return updateAt;
    }

    /**
    * 注释：更新人 
    * @param updateAt the value of sys_menu.update_at
    */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }
    /**
    * 注释：更新时间 
    * @return the value of sys_menu.update_time
    */
    public Date getUpdateTime() {
         return updateTime;
    }

    /**
    * 注释：更新时间 
    * @param updateTime the value of sys_menu.update_time
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
    * 注释：删除标记 
    * @return the value of sys_menu.del_flag
    */
    public String getDelFlag() {
         return delFlag;
    }

    /**
    * 注释：删除标记 
    * @param delFlag the value of sys_menu.del_flag
    */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @return the value of sys_menu.version
    */
    public Integer getVersion() {
         return version;
    }

    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @param version the value of sys_menu.version
    */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
