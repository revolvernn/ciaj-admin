package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import lombok.Data;

import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 09:15:20
 * @Description: www.ciaj.com gen PO
 */
@Data
public class SysAreaPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.name
    * 注释：城市名称 
    */
    private String name;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.code
    * 注释：城市编码 
    */
    private String code;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.parent_id
    * 注释：父级 
    */
    private String parentId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.parent_ids
    * 注释：父节点集合 
    */
    private String parentIds;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.type
    * 注释：类型：province\省   city\市 business_district 区 
    */
    private String type;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.level
    * 注释：级别 
    */
    private Integer level;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.sequence
    * 注释：排序 
    */
    private Integer sequence;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.enabled
    * 注释：是否可用Y/N 
    */
    private String enabled;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.create_at
    * 注释：创建人
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.create_time
    * 注释：创建时间
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.update_at
    * 注释：更新人
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.update_time
    * 注释：更新时间
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.del_flag
    * 注释：删除标记
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_area.version
    * 注释：版本号0为不可修改，1+可修改
    */
    private Integer version;
}
