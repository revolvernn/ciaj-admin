package com.ciaj.base;

import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtis;
import io.swagger.models.auth.In;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/1 15:45
 * @Description: 基于通用MyBatis Mapper插件的Service接口的实现
 */
@Service
@Transactional
@Log4j2
public abstract class AbstractService<PO, DTO extends BaseEntity, VO extends VOEntity> extends AbstractBase<PO, DTO, VO> implements BaseService<PO, DTO, VO> {

	@Autowired
	protected Mapper<PO, DTO, VO> mapper;

	public AbstractService() {
		super();
	}

	//*************************select***************************************

	public Page<PO> selectPOPage(PO entity) {
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<PO> list = select(entity);
		return wrapPOPage(p, list);
	}

	public Page<DTO> selectDTOPage(PO entity) {
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<PO> list = select(entity);
		return wrapDTOPage(p, list);
	}

	public Page<PO> selectPOPage(VO entity) {
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<PO> list = selectList(entity);
		return wrapPOPage(p, list);
	}

	public Page<DTO> selectDTOPage(VO entity) {
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<PO> list = selectList(entity);
		return wrapDTOPage(p, list);
	}

	public Page<VO> selectVoPage(VO entity) {
		com.github.pagehelper.Page p = PageUtis.startPageAndOrderBy();
		List<PO> list = selectList(entity);
		return wrapVoPage(p, list);
	}

	public List<DTO> selectDTOList(VO entity) {
		List<PO> list = selectList(entity);
		return posToDtos(list);
	}

	@Transactional(readOnly = true)
	public List<PO> selectAll(PO entity) {
		return mapper.selectAll(entity);
	}

	@Transactional(readOnly = true)
	public PO selectByPrimaryKey(Object key) {
		return mapper.selectByPrimaryKey(key);
	}

	@Transactional(readOnly = true)
	public List<PO> select(PO record) {
		return mapper.select(record);
	}

	@Transactional(readOnly = true)
	public List<PO> selectList(VO q) {
		return mapper.selectList(q);
	}

	@Transactional(readOnly = true)
	public PO selectOne(PO record) {
		return mapper.selectOne(record);
	}

	//*************************select***************************************
	//*************************insert***************************************
	@Transactional(rollbackFor = Exception.class)
	public int insert(PO record) {
		insertOrUpdatePre(record, "insert");
		return mapper.insert(record);
	}

	public DTO insertDTO(PO entity) {
		insert(entity);
		return poToDto(entity);
	}


	public PO insertPO(PO entity) {
		insert(entity);
		return entity;
	}


	@Transactional(rollbackFor = Exception.class)
	public int insertDtos(List<DTO> dtos) {
		int i = 0;
		for (DTO dto : dtos) {
			PO po = dtoToPo(dto);
			insertOrUpdatePre(po, "insert");
			int insert = mapper.insert(po);
			i += insert;
		}
		return i;
	}

	public DTO insertSelectiveDTO(PO entity) {
		insert(entity);
		return poToDto(entity);
	}

	public PO insertSelectivePO(PO entity) {
		insert(entity);
		return entity;
	}

	public int insertSelective(PO record) {
		insertOrUpdatePre(record, "insert");
		return mapper.insertSelective(record);
	}

	//*************************insert***************************************
	//*************************update***************************************

	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKey(PO record) {
		insertOrUpdatePre(record, "update");
		return mapper.updateByPrimaryKey(record);
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKeyAndVersion(PO record, int oldVersion) {
		insertOrUpdatePre(record, "update");
		int i = mapper.updateByPrimaryKeyAndVersion(record, oldVersion);
		if (i == 0) throw new BsRException("更新失败，数据被占用或数据不存在");
		return i;
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(PO record) {
		insertOrUpdatePre(record, "update");
		int i = mapper.updateByPrimaryKeySelective(record);
		if (i == 0) throw new BsRException("更新失败，数据被占用或数据不存在");
		return i;
	}

	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKeySelectiveAndVersion(PO record, int oldVersion) {
		insertOrUpdatePre(record, "update");
		int i = mapper.updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
		if (i == 0) throw new BsRException("更新失败，数据被占用或数据不存在");
		return i;
	}

	public PO updateByPrimaryKeyPO(PO record) {
		updateByPrimaryKey(record);
		return record;
	}

	public DTO updateByPrimaryKeyDTO(PO record) {
		updateByPrimaryKey(record);
		return poToDto(record);
	}

	public PO updateByPrimaryKeySelectivePO(PO record) {
		updateByPrimaryKeySelective(record);
		return record;
	}

	public PO updateByPrimaryKeySelectiveAndVersionPO(PO record, Integer oldVersion) {
		updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
		return record;
	}

	public DTO updateByPrimaryKeySelectiveDTO(PO record) {
		updateByPrimaryKeySelective(record);
		return poToDto(record);
	}

	public DTO updateByPrimaryKeySelectiveAndVersionDTO(PO record, Integer oldVersion) {
		updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
		return poToDto(record);
	}

	//*************************delete***************************************

	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKey(Object key) {
		int i = mapper.deleteByPrimaryKey(key);
		return i;
	}

	@Transactional(rollbackFor = Exception.class)
	public int delete(PO record) {
		int i = mapper.delete(record);
		return i;
	}

	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKeys(List<Object> keys) {
		int i = 0;
		if (CollectionUtils.isNotEmpty(keys)) {
			for (Object key : keys) {
				int delete = deleteByPrimaryKey(key);
				i += delete;
			}
		}
		return i;
	}

	//*************************delete***************************************


}
