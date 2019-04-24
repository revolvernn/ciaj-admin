package com.ciaj.comm.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/12/5 10:30
 * @Description:
 */
@ApiModel(value = "Page")
public class Page<DATA> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否开启分页
	 */
	@ApiModelProperty("是否开启分页: 默认-false")
	private Boolean pageEnabled = Boolean.FALSE;

	/**
	 * 是否开启排序
	 */
	@ApiModelProperty("是否开启排序: 默认-false")
	private Boolean orderByEnabled = Boolean.FALSE;

	/**
	 * 是否开启排序
	 */
	@ApiModelProperty("排序：xxx-desc,xxx-asc,xxx")
	private String orderBy;
	/**
	 * 每页记条数
	 */
	@ApiModelProperty("每页记条数 {10, 20, 50, 100}")
	private Integer[] pageSizes = {10, 20, 50, 100};


	/**
	 * 当前页数
	 */
	@ApiModelProperty("当前页数：默认第一页")
	private int currPage = 1;
	/**
	 * 每页记录数
	 */
	@ApiModelProperty("每页记录数：默认每页十条")
	private int pageSize = 10;
	/**
	 * 总页数
	 */
	@ApiModelProperty("总页数")
	private long totalPage;
	/**
	 * 总记录数
	 */
	@ApiModelProperty("总记录数")
	private long totalCount;

	/**
	 * 列表数据
	 */
	@ApiModelProperty("列表数据")
	private List<DATA> list;

	public Page() {
	}

	/**
	 * 分页
	 *
	 * @param list       列表数据
	 * @param totalCount 总记录数
	 * @param pageSize   每页记录数
	 * @param currPage   当前页数
	 */
	public Page(List<DATA> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.pageEnabled = Boolean.TRUE;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	/**
	 * 分页
	 *
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 * @param pageEnabled 是否开启
	 */
	public Page(List<DATA> list, int totalCount, int pageSize, int currPage, Boolean pageEnabled) {
		this.list = list;
		this.pageEnabled = pageEnabled;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	public Integer[] getPageSizes() {
		return pageSizes;
	}

	public void setPageSizes(Integer[] pageSizes) {
		this.pageSizes = pageSizes;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Boolean getPageEnabled() {
		return pageEnabled;
	}

	public void setPageEnabled(Boolean pageEnabled) {
		this.pageEnabled = pageEnabled;
	}

	public Boolean getOrderByEnabled() {
		return orderByEnabled;
	}

	public void setOrderByEnabled(Boolean orderByEnabled) {
		this.orderByEnabled = orderByEnabled;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<DATA> getList() {
		return list;
	}

	public void setList(List<DATA> list) {
		this.list = list;
	}

}
