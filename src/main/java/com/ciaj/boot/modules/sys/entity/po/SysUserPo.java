package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import lombok.Data;

import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-08 15:23:02
 * @Description: www.ciaj.com gen PO
 */
@Data
public class SysUserPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.account
    * 注释：用户账号、手机号、邮箱、其他 
    */
    private String account;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.username
    * 注释：用户名 
    */
    private String username;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.nickname
    * 注释：昵称 
    */
    private String nickname;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.sex
    * 注释：性别,0，女,1，男,2，未知 
    */
    private String sex;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.mobile
    * 注释：电话号码 
    */
    private String mobile;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.email
    * 注释：邮箱 
    */
    private String email;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.birthday
    * 注释：生日 
    */
    private Date birthday;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.province
    * 注释：用户所属省 
    */
    private String province;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.city
    * 注释：用户所属城市 
    */
    private String city;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.district
    * 注释：用户所属区 
    */
    private String district;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.addr
    * 注释：用户详细地址 
    */
    private String addr;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.source
    * 注释：来源，pc/系统  app/移动端 other/其他 ... 
    */
    private String source;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.user_type
    * 注释：用户类型、org_user、组织用户，other_user、其他用户 
    */
    private String userType;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.user_status
    * 注释：用户状态，在职inservice，离职中leaveing，已离职leave，重新雇佣 
    */
    private String userStatus;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.locked
    * 注释：锁定状态 Y/N 
    */
    private String locked;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.pic_url
    * 注释：头像地址 
    */
    private String picUrl;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.dept_id
    * 注释：所在部门ID 
    */
    private String deptId;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.dept_name
    * 注释：所在部门名称 
    */
    private String deptName;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.dept_ids
    * 注释：所在部门集合ID 
    */
    private String deptIds;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.dept_names
    * 注释：所在部门集合名称 
    */
    private String deptNames;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_user.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;
}
