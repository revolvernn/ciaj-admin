package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import lombok.Data;

import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:08:39
 * @Description: www.ciaj.com gen PO
 */
@Data
public class SysPermissionPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.name
    * 注释：权限名 
    */
    private String name;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.description
    * 注释：权限描述 
    */
    private String description;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.type
    * 注释：权限类型 
    */
    private String type;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.permission_code
    * 注释：权限码 
    */
    private String permissionCode;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.url
    * 注释：资源路径 
    */
    private String url;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.parent_id
    * 注释：父级id 
    */
    private String parentId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.parent_ids
    * 注释：父级ids 
    */
    private String parentIds;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.available
    * 注释：是否可用（Y/N） 
    */
    private String available;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_permission.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;
}
