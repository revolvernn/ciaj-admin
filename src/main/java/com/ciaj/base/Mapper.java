package com.ciaj.base;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/5/30 15:45
 * @Description: mapper 基类 // extends MySqlMapper<T>, tk.mybatis.mapper.common.Mapper<T>
 */
public interface Mapper<PO, DTO extends BaseEntity, VO extends VOEntity> {

	// delete ==========================================

	/**
	 * @param record
	 * @return
	 */
	int delete(PO record);

	/**
	 * @param key
	 * @return
	 */
	int deleteByPrimaryKey(Object key);

	// insert ==========================================

	/**
	 * @param entity
	 * @return
	 */
	int insert(PO entity);

	/**
	 * @param entity
	 * @return
	 */
	int insertSelective(PO entity);

	// update ==========================================

	/**
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(PO record);

	/**
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(PO record);

	// select ==========================================

	/**
	 * @param key
	 * @return
	 */
	PO selectByPrimaryKey(Object key);

	/**
	 * @param record
	 * @return
	 */
	PO selectOne(PO record);

	/**
	 * @param record
	 * @return
	 */
	List<PO> select(PO record);

	/**
	 * 查询
	 *
	 * @param entity
	 * @return List<T>
	 */
	List<PO> selectAll(PO entity);

	/**
	 * @param entity
	 * @return
	 */
	List<PO> selectList(VO entity);

	/**
	 * @param entity
	 * @return
	 */
	List<DTO> selectDTOList(VO entity);
}
