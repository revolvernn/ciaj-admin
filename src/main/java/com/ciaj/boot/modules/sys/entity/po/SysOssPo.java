package com.ciaj.boot.modules.sys.entity.po;

import com.ciaj.base.BaseEntity;
import java.util.*;
import java.lang.*;
/**
 * @Author: Ciaj.
 * @Date: 2019-05-09 13:07:46
 * @Description: www.ciaj.com gen PO
 */
public class SysOssPo extends BaseEntity {
    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.id
    * 注释：主键 
    */
    private String id;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.url
    * 注释：图片URL 
    */
    private String url;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.source
    * 注释：来源 
    */
    private String source;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.create_at
    * 注释：创建人 
    */
    private String createAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.create_time
    * 注释：创建时间 
    */
    private Date createTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.update_at
    * 注释：更新人 
    */
    private String updateAt;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.update_time
    * 注释：更新时间 
    */
    private Date updateTime;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.del_flag
    * 注释：删除标记 
    */
    private String delFlag;

    /**
    *
    * This field was generated by MyBatis Generator.
    * This field corresponds to the database column sys_oss.version
    * 注释：版本号0为不可修改，1+可修改 
    */
    private Integer version;


    /**
    * 注释：主键 
    * @return the value of sys_oss.id
    */
    public String getId() {
         return id;
    }

    /**
    * 注释：主键 
    * @param id the value of sys_oss.id
    */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    /**
    * 注释：图片URL 
    * @return the value of sys_oss.url
    */
    public String getUrl() {
         return url;
    }

    /**
    * 注释：图片URL 
    * @param url the value of sys_oss.url
    */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    /**
    * 注释：来源 
    * @return the value of sys_oss.source
    */
    public String getSource() {
         return source;
    }

    /**
    * 注释：来源 
    * @param source the value of sys_oss.source
    */
    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
    /**
    * 注释：创建人 
    * @return the value of sys_oss.create_at
    */
    public String getCreateAt() {
         return createAt;
    }

    /**
    * 注释：创建人 
    * @param createAt the value of sys_oss.create_at
    */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }
    /**
    * 注释：创建时间 
    * @return the value of sys_oss.create_time
    */
    public Date getCreateTime() {
         return createTime;
    }

    /**
    * 注释：创建时间 
    * @param createTime the value of sys_oss.create_time
    */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
    * 注释：更新人 
    * @return the value of sys_oss.update_at
    */
    public String getUpdateAt() {
         return updateAt;
    }

    /**
    * 注释：更新人 
    * @param updateAt the value of sys_oss.update_at
    */
    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt == null ? null : updateAt.trim();
    }
    /**
    * 注释：更新时间 
    * @return the value of sys_oss.update_time
    */
    public Date getUpdateTime() {
         return updateTime;
    }

    /**
    * 注释：更新时间 
    * @param updateTime the value of sys_oss.update_time
    */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
    * 注释：删除标记 
    * @return the value of sys_oss.del_flag
    */
    public String getDelFlag() {
         return delFlag;
    }

    /**
    * 注释：删除标记 
    * @param delFlag the value of sys_oss.del_flag
    */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @return the value of sys_oss.version
    */
    public Integer getVersion() {
         return version;
    }

    /**
    * 注释：版本号0为不可修改，1+可修改 
    * @param version the value of sys_oss.version
    */
    public void setVersion(Integer version) {
        this.version = version;
    }
}
