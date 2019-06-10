package com.ciaj.base;

import com.ciaj.comm.ResponseEntity;
import com.ciaj.comm.constant.DefaultConfigConstant;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.CommUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.ShiroUtils;
import com.ciaj.comm.validate.AddValidGroup;
import com.ciaj.comm.validate.UpdateValidGroup;
import com.ciaj.comm.validate.ValidatorUtils;
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
		super.insertFieldByPO("delFlag", DefaultConstant.FLAG_N, entity);

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

		baseService.insertSelective(dtoToPo(d));
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
	 * 根据版本号更新
	 *
	 * @param t
	 * @param id
	 * @return
	 */
	public ResponseEntity updateByVersion(PO t, String id, Integer oldVersion) {
		return this.updateByVersion(t, oldVersion);
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
	 * 根据版本号更新
	 *
	 * @param t
	 * @return
	 */
	public ResponseEntity updateByVersion(PO t, Integer oldVersion) {
		ValidatorUtils.validateEntity(t, UpdateValidGroup.class);
		if (!checkUpdateOrDeleteDefaultData(oldVersion)) {
			super.updateFieldByPO(VERSION, oldVersion + 1, t);
		}
		baseService.updateByPrimaryKeySelectiveAndVersion(t, oldVersion);
		return ResponseEntity.success("更新成功");
	}

	/**
	 * 检查数据版本修改权限
	 *
	 * @param oldVersion
	 * @return
	 */
	public boolean checkUpdateOrDeleteDefaultData(Integer oldVersion) {
		//如果数据版本为不可修改的版本，并没有修改的权限，抛出异常
		if (DefaultConfigConstant.DEFAULT_FINAL_DATA_VERSION.equals(oldVersion)) {
			//没有权限或不是超管
			if (CommUtil.getLoginUser().isSuperAdmin()) {
			} else if (!ShiroUtils.checkPermissions(DefaultConfigConstant.DEFAULT_FINAL_DATA_PERMISSION_UPDATE_OR_DELETE)) {
				throw new BsRException("对不起，您没有操作这条数据的权限!");
			}
			return true;
		}
		return false;
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
			super.insertFieldByPO(ID, id, entity);
			super.insertFieldByPO(DEL_FLAG, DefaultConstant.FLAG_Y, entity);

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
	 * @param id
	 * @return
	 */
	public ResponseEntity deleteFlagVersion(String id, Integer oldVersion) {
		try {
			//
			PO entity = (PO) super.poClass.newInstance();
			super.updateFieldByPO(ID, id, entity);
			super.updateFieldByPO(DEL_FLAG, DefaultConstant.FLAG_Y, entity);

			this.updateByVersion(entity, oldVersion);
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
			super.insertFieldByPO(ID, id, entity);
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
