package com.ciaj.base;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/12 10:11
 * @Version 1.0
 */
public class VOEntity implements Serializable {
	private static final long serialVersionUID = -1796168234561456320L;
	/**
	 * 关键字查询
	 */
	@ApiModelProperty(value = "查询关键字", hidden = true)
	@Transient
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
