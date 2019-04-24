package com.ciaj.base;

import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.validate.AddValidGroup;
import com.ciaj.comm.utils.validate.UpdateValidGroup;
import com.ciaj.comm.utils.validate.ValidatorUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/27 10:37
 * @Description:
 */
@Log4j2
@SuppressWarnings("all")
public abstract class AbstractController<PO, DTO extends BaseEntity, VO extends VOEntity> extends AbstractBase<PO, DTO, VO> {
	@Autowired
	private BaseService<PO, DTO, VO> baseService;

	public AbstractController() {
		super();
	}

	/**
	 * 根据ID获取
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity<DTO> getById(String id) {
		PO entity = baseService.selectByPrimaryKey(id);
		return new ResponseEntity<DTO>("查询成功").put(poToDto(entity));
	}

	/**
	 * 实体查询页面
	 *
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Page<PO>> listPOPage(PO entity) {
		//
		super.insertFieldByT("delFlag", DefaultConstant.FLAG_N, entity);

		Page<PO> page = baseService.selectPOPage(entity);
		//
		return new ResponseEntity<Page<PO>>("查询成功").put(page);
	}

	/**
	 * 实体查询页面
	 *
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Page<PO>> listPOPage(VO entity) {
		//
		super.setFieldByVO("delFlag", DefaultConstant.FLAG_N, entity);

		Page<PO> page = baseService.selectPOPage(entity);
		//
		return new ResponseEntity<Page<PO>>("查询成功").put(page);
	}


	/**
	 * 实体查询页面
	 *
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Page<DTO>> listDTOPage(VO entity) {
		//
		super.setFieldByVO("delFlag", DefaultConstant.FLAG_N, entity);

		Page<DTO> page = baseService.selectDTOPage(entity);
		//
		return new ResponseEntity<Page<DTO>>("查询成功").put(page);
	}

	/**
	 * 添加
	 *
	 * @param t
	 * @return
	 */
	public ResponseEntity add(PO t) {
		//
		ValidatorUtils.validateEntity(t, AddValidGroup.class);
		baseService.insert(t);

		return ResponseEntity.success("添加成功");
	}

	/**
	 * 添加
	 *
	 * @param d
	 * @return
	 */
	public ResponseEntity add(DTO d) {
		//
		ValidatorUtils.validateEntity(d, AddValidGroup.class);

		baseService.insert(dtoToPo(d));
		return ResponseEntity.success("添加成功");
	}

	/**
	 * 添加
	 *
	 * @param ds
	 * @return
	 */
	public ResponseEntity adds(List<DTO> ds) {
		baseService.insertDtos(ds);
		return ResponseEntity.success("添加成功");
	}

	/**
	 * 更新
	 *
	 * @param t
	 * @param id
	 * @return
	 */
	public ResponseEntity update(PO t, String id) {
		return this.update(t);
	}

	/**
	 * 更新
	 *
	 * @param t
	 * @return
	 */
	public ResponseEntity update(PO t) {
		ValidatorUtils.validateEntity(t, UpdateValidGroup.class);

		baseService.updateByPrimaryKey(t);
		return ResponseEntity.success("更新成功");
	}

	/**
	 * 更新
	 *
	 * @param d
	 * @return
	 */
	public ResponseEntity update(DTO d) {
		return this.update(super.dtoToPo(d));
	}

	/**
	 * 软删除，
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity deleteFlag(String id) {
		try {
			//
			PO entity = (PO) super.poClass.newInstance();
			super.insertFieldByT("id", id, entity);
			super.insertFieldByT("delFlag", DefaultConstant.FLAG_Y, entity);

			baseService.updateByPrimaryKeySelective(entity);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return ResponseEntity.success("删除成功");
	}


	/**
	 * 软删除，
	 *
	 * @param ids
	 * @return
	 */
	public ResponseEntity deleteFlag(String[] ids) {
		for (String id : ids) {
			this.deleteFlag(id);
		}
		return ResponseEntity.success("删除成功");
	}

	/**
	 * 物理删除，
	 *
	 * @param id
	 * @return
	 */
	public ResponseEntity delete(String id) {
		try {
			//
			PO entity = (PO) super.poClass.newInstance();
			super.insertFieldByT("id", id, entity);
			baseService.deleteByPrimaryKey(entity);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return ResponseEntity.success("删除成功");
	}

	/**
	 * 软删除，
	 *
	 * @param ids
	 * @return
	 */
	public ResponseEntity delete(String[] ids) {
		baseService.deleteByPrimaryKeys(Arrays.asList(ids));
		return ResponseEntity.success("删除成功");
	}

}
