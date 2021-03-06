package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import lombok.Data;

import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:00:46
 * @Description: www.ciaj.com gen PO
 */
@Data
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
}
