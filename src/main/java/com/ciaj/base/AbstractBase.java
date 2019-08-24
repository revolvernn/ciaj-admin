package com.ciaj.base;

import com.ciaj.boot.component.config.shiro.ShiroUser;
import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.utils.CommUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/27 10:37
 * @Description:
 */
@Log4j2
public abstract class AbstractBase<PO, DTO extends BaseEntity, VO extends VOEntity> {

	public static final String INSERT = "insert";
	public static final String UPDATE = "update";
	public static final String CREATE_TIME = "createTime";
	public static final String UPATE_TIME = "updateTime";
	public static final String CREATE_AT = "createAt";
	public static final String UPATE_AT = "updateAt";
	public static final String DEL_FLAG = "delFlag";
	public static final String ID = "id";
	public static final String VERSION = "version";

	protected Class<PO> poClass;    // 当前泛型PO真实类型的Class
	protected Class<DTO> dtoClass;    // 当前泛型DTO真实类型的Class
	protected Class<VO> voClass;    // 当前泛型QUERYPO真实类型的Class

	public AbstractBase() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		poClass = (Class<PO>) pt.getActualTypeArguments()[0];
		dtoClass = (Class<DTO>) pt.getActualTypeArguments()[1];
		voClass = (Class<VO>) pt.getActualTypeArguments()[2];
	}

	/**
	 * 反射设置字段值 T
	 *
	 * @param fieldName
	 * @param fiedValue
	 * @param record
	 */
	void insertFieldByPO(String fieldName, Object fiedValue, PO record) {
		try {
			Field field = this.poClass.getDeclaredField(fieldName);

			field.setAccessible(true);
			Object value = field.get(record);
			if (value == null && fiedValue != null) {
				field.set(record, fiedValue);
			}
			field.setAccessible(false);
		} catch (IllegalAccessException e) {
		} catch (NoSuchFieldException e) {
		}
	}


	/**
	 * 反射设置字段值 T
	 *
	 * @param fieldName
	 * @param fiedValue
	 * @param record
	 */
	void updateFieldByPO(String fieldName, Object fiedValue, PO record) {
		try {
			Field field = this.poClass.getDeclaredField(fieldName);

			field.setAccessible(true);
			if (fiedValue != null) {
				field.set(record, fiedValue);
			}
			field.setAccessible(false);
		} catch (IllegalAccessException e) {
		} catch (NoSuchFieldException e) {
		}
	}


	/**
	 * 反射设置字段值 Q
	 *
	 * @param fieldName
	 * @param fiedValue
	 * @param record
	 */
	void setFieldByVO(String fieldName, Object fiedValue, VO record) {
		try {
			Field field = this.voClass.getSuperclass().getDeclaredField(fieldName);

			field.setAccessible(true);
			Object value = field.get(record);
			if (value == null && fiedValue != null) {
				field.set(record, fiedValue);
			}
			field.setAccessible(false);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} catch (NoSuchFieldException e) {
			log.error(e.getMessage(), e);
		}
	}


	/**
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<PO> wrapPOPage(com.github.pagehelper.Page p, List<PO> list) {
		return wrapPagePO(p, list);
	}

	/**
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<DTO> wrapDTOPage(com.github.pagehelper.Page p, List<PO> list) {
		List<DTO> ds = posToDtos(list);
		return wrapPageDTO(p, ds);
	}

	/**
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<VO> wrapVoPage(com.github.pagehelper.Page p, List<PO> list) {
		List<VO> ds = posToVos(list);
		return wrapPageVo(p, ds);
	}

	/**
	 * 包装分页数据
	 *
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<DTO> wrapPageDTO(com.github.pagehelper.Page p, List<DTO> list) {
		if (p == null) {
			Page page = new Page(list, list.size(), list.size(), 1, false);
			if (PageUtils.isOrderBy()) {
				page.setOrderByEnabled(true);
				page.setOrderBy(PageUtils.getPageFromThreadLocal().getOrderBy());
			}
			return page;
		} else {
			Page page = PageUtils.getPageFromThreadLocal();
			page.setCurrPage(p.getPageNum());
			page.setPageSize(p.getPageSize());
			page.setTotalPage(p.getPages());
			page.setTotalCount(p.getTotal());
			page.setList(list);
			return page;
		}
	}


	/**
	 * 包装分页数据
	 *
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<VO> wrapPageVo(com.github.pagehelper.Page p, List<VO> list) {
		if (p == null) {
			Page page = new Page(list, list.size(), list.size(), 1, false);
			if (PageUtils.isOrderBy()) {
				page.setOrderByEnabled(true);
				page.setOrderBy(PageUtils.getPageFromThreadLocal().getOrderBy());
			}
			return page;
		} else {
			Page page = PageUtils.getPageFromThreadLocal();
			page.setCurrPage(p.getPageNum());
			page.setPageSize(p.getPageSize());
			page.setTotalPage(p.getPages());
			page.setTotalCount(p.getTotal());
			page.setList(list);
			return page;
		}
	}

	/**
	 * 包装分页数据
	 *
	 * @param p
	 * @param list
	 * @return
	 */
	public Page<PO> wrapPagePO(com.github.pagehelper.Page p, List<PO> list) {
		if (p == null) {
			Page page = new Page(list, list.size(), list.size(), 1, false);
			if (PageUtils.isOrderBy()) {
				page.setOrderByEnabled(true);
				page.setOrderBy(PageUtils.getPageFromThreadLocal().getOrderBy());
			}
			return page;
		} else {
			Page page = PageUtils.getPageFromThreadLocal();
			page.setCurrPage(p.getPageNum());
			page.setPageSize(p.getPageSize());
			page.setTotalPage(p.getPages());
			page.setTotalCount(p.getTotal());
			page.setList(list);
			return page;
		}
	}


	/**
	 * 包装VO
	 *
	 * @param t
	 * @return
	 */
	public VO poToVo(PO t) {
		VO d = null;
		try {
			d = voClass.newInstance();
			BeanUtils.copyProperties(t, d);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return d;
	}


	/**
	 * 包装VO
	 *
	 * @param t
	 * @return
	 */
	public VO dtoToVo(DTO t) {
		VO d = null;
		try {
			d = voClass.newInstance();
			BeanUtils.copyProperties(t, d);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return d;
	}


	/**
	 * 包装DTO
	 *
	 * @param t
	 * @return
	 */
	public DTO poToDto(PO t) {
		DTO d = null;
		try {
			d = dtoClass.newInstance();
			BeanUtils.copyProperties(t, d);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return d;
	}


	/**
	 * 包装T
	 *
	 * @param d
	 * @return
	 */
	public PO dtoToPo(DTO d) {
		PO t = null;
		try {
			t = poClass.newInstance();
			BeanUtils.copyProperties(d, t);
		} catch (InstantiationException e) {
			log.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return t;
	}


	/**
	 * 包装DTOs
	 *
	 * @param ts
	 * @return
	 */
	public List<DTO> posToDtos(List<PO> ts) {
		List<DTO> ds = new ArrayList<>();
		if (ts != null && ts.size() > 0) {
			for (PO t : ts) {
				ds.add(poToDto(t));
			}
		}
		return ds;
	}


	/**
	 * 包装VOs
	 *
	 * @param ts
	 * @return
	 */
	public List<VO> dtosToVos(List<DTO> ts) {
		List<VO> vs = new ArrayList<>();
		if (ts != null && ts.size() > 0) {
			for (DTO t : ts) {
				vs.add(dtoToVo(t));
			}
		}
		return vs;
	}

	/**
	 * 包装VOs
	 *
	 * @param ts
	 * @return
	 */
	public List<VO> posToVos(List<PO> ts) {
		List<VO> vs = new ArrayList<>();
		if (ts != null && ts.size() > 0) {
			for (PO t : ts) {
				vs.add(poToVo(t));
			}
		}
		return vs;
	}

	/**
	 * 插入或更新前处理时间创建人更新人删除标记
	 *
	 * @param record
	 * @param opt
	 */
	public void insertOrUpdatePre(PO record, String opt) {
		ShiroUser loginUser = null;
		try {
			loginUser = CommUtil.getLoginUser();
		} catch (Exception e) {
		}
		String userId = loginUser != null ? loginUser.getId() : DefaultConstant.SUPER_ADMIN_ID;
		if (INSERT.equals(opt)) {
			insertFieldByPO(VERSION, 1, record);
			insertFieldByPO(CREATE_AT, userId, record);
			insertFieldByPO(CREATE_TIME, new Date(), record);
			insertFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, record);
		}
		if (UPDATE.equals(opt) || INSERT.equals(opt)) {
			updateFieldByPO(UPATE_AT, userId, record);
			updateFieldByPO(UPATE_TIME, new Date(), record);
		}
	}

	/**
	 * 插入或更新前处理时间创建人更新人删除标记
	 *
	 * @param records
	 * @param opt
	 */
	public void insertOrUpdatePre(List<PO> records, String opt) {
		if (records == null || records.size() == 0) return;
		ShiroUser loginUser = null;
		try {
			loginUser = CommUtil.getLoginUser();
		} catch (Exception e) {
		}
		String userId = loginUser != null ? loginUser.getId() : DefaultConstant.SUPER_ADMIN_ID;
		for (PO record : records) {
			if (INSERT.equals(opt)) {
				insertFieldByPO(VERSION, 1, record);
				insertFieldByPO(CREATE_AT, userId, record);
				insertFieldByPO(CREATE_TIME, new Date(), record);
				insertFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, record);
			}
			if (UPDATE.equals(opt) || INSERT.equals(opt)) {
				updateFieldByPO(UPATE_AT, userId, record);
				updateFieldByPO(UPATE_TIME, new Date(), record);
			}
		}
	}
}
