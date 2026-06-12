package com.ciaj.base;

import com.ciaj.comm.constant.DefaultConstant;
import com.ciaj.comm.exception.BsRException;
import com.ciaj.comm.utils.CollectionUtil;
import com.ciaj.comm.utils.Page;
import com.ciaj.comm.utils.PageUtils;
import lombok.extern.log4j.Log4j2;
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

    List<IAddServiceListener<PO>> iAddServiceListeners;
    List<IUpdateServiceListener<PO>> iUpdateServiceListeners;

    @Autowired(required = false)
    public void setAddServiceListeners(List<IAddServiceListener<PO>> iAddServiceListeners) {
        this.iAddServiceListeners = iAddServiceListeners;
    }

    @Autowired(required = false)
    public void setUpdateServiceListeners(List<IUpdateServiceListener<PO>> iUpdateServiceListeners) {
        this.iUpdateServiceListeners = iUpdateServiceListeners;
    }

    public AbstractService() {
        super();
    }

    //*************************select***************************************

    @Override
    public Page<PO> selectPOPage(PO entity) {
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<PO> list = select(entity);
        return wrapPOPage(p, list);
    }

    @Override
    public Page<DTO> selectDTOPage(PO entity) {
        setFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<PO> list = select(entity);
        return wrapDTOPage(p, list);
    }

    @Override
    public Page<PO> selectPOPage(VO entity) {
        setFieldByVO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<PO> list = selectList(entity);
        return wrapPOPage(p, list);
    }

    @Override
    public Page<DTO> selectDTOPage(VO entity) {
        setFieldByVO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<PO> list = selectList(entity);
        return wrapDTOPage(p, list);
    }

    @Override
    public Page<VO> selectVoPage(VO entity) {
        setFieldByVO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        com.github.pagehelper.Page p = PageUtils.startPageAndOrderBy();
        List<PO> list = selectList(entity);
        return wrapVoPage(p, list);
    }

    @Override
    public List<DTO> selectDTOList(VO entity) {
        setFieldByVO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        List<PO> list = selectList(entity);
        return posToDtos(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PO> selectAll(PO entity) {
        setFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        return mapper.selectAll(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PO selectByPrimaryKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PO> select(PO record) {
        setFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, record);
        return mapper.select(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PO> selectList(VO entity) {
        setFieldByVO(DEL_FLAG, DefaultConstant.FLAG_N, entity);
        return mapper.selectList(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PO selectOne(PO record) {
        setFieldByPO(DEL_FLAG, DefaultConstant.FLAG_N, record);
        return mapper.selectOne(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PO> selectListByKeys(Object[] keys) {
        return mapper.selectListByKeys(keys);
    }

    //*************************select***************************************

    /**
     * 添加前调用
     *
     * @param po
     */
    void preAdd(PO po) {
        insertOrUpdatePre(po,INSERT);
        if (CollectionUtil.isNotEmpty(iAddServiceListeners)) {
            iAddServiceListeners.parallelStream().forEach(listener -> listener.preAdd(po));
        }
    }

    /**
     * 添加后调用
     *
     * @param po
     */
    void postAdd(PO po) {
        if (CollectionUtil.isNotEmpty(iAddServiceListeners)) {
            iAddServiceListeners.parallelStream().forEach(listener -> listener.postAdd(po));
        }
    }

    //*************************insert***************************************
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(PO record) {
        preAdd(record);
        int insert = mapper.insert(record);
        postAdd(record);
        return insert;
    }


    @Override
    public DTO insertDTO(PO entity) {
        insert(entity);
        return poToDto(entity);
    }


    @Override
    public PO insertPO(PO entity) {
        preAdd(entity);
        insert(entity);
        postAdd(entity);
        return entity;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPOs(List<PO> pos) {
        int i = 0;
        for (PO po : pos) {
            preAdd(po);
            int insert = mapper.insert(po);
            i += insert;
            postAdd(po);
        }
        return i;
    }

    @Override
    public DTO insertSelectiveDTO(PO entity) {
        insert(entity);
        return poToDto(entity);
    }

    @Override
    public PO insertSelectivePO(PO entity) {
        insert(entity);
        return entity;
    }

    @Override
    public int insertSelective(PO record) {
        preAdd(record);
        int i = mapper.insertSelective(record);
        postAdd(record);
        return i;
    }

    //*************************insert***************************************
    //*************************update***************************************

    /**
     * 在更新前调用
     *
     * @param po
     */
    void preUpdate(PO po) {
        insertOrUpdatePre(po,UPDATE);
        if (CollectionUtil.isNotEmpty(iUpdateServiceListeners)) {
            iUpdateServiceListeners.parallelStream().forEach(listener -> listener.preUpdate(po));
        }
    }

    /**
     * 在更新后调用
     *
     * @param po
     */
    void postUpdate(PO po) {
        if (CollectionUtil.isNotEmpty(iUpdateServiceListeners)) {
            iUpdateServiceListeners.parallelStream().forEach(listener -> listener.postUpdate(po));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(PO record) {
        preUpdate(record);
        int i = mapper.updateByPrimaryKey(record);
        postUpdate(record);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeyAndVersion(PO record, int oldVersion) {
        preUpdate(record);
        int i = mapper.updateByPrimaryKeyAndVersion(record, oldVersion);
        if (i == 0) {
            throw new BsRException("更新失败，数据被占用或数据不存在");
        }
        postUpdate(record);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(PO record) {
        preUpdate(record);
        int i = mapper.updateByPrimaryKeySelective(record);
        if (i == 0) {
            throw new BsRException("更新失败，数据被占用或数据不存在");
        }
        postUpdate(record);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelectiveAndVersion(PO record, int oldVersion) {
        preUpdate(record);
        int i = mapper.updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
        if (i == 0) {
            throw new BsRException("更新失败，数据被占用或数据不存在");
        }
        postUpdate(record);
        return i;
    }

    @Override
    public PO updateByPrimaryKeyPO(PO record) {
        updateByPrimaryKey(record);
        return record;
    }

    @Override
    public DTO updateByPrimaryKeyDTO(PO record) {
        updateByPrimaryKey(record);
        return poToDto(record);
    }

    @Override
    public PO updateByPrimaryKeySelectivePO(PO record) {
        updateByPrimaryKeySelective(record);
        return record;
    }

    @Override
    public PO updateByPrimaryKeySelectiveAndVersionPO(PO record, Integer oldVersion) {
        updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
        return record;
    }

    @Override
    public DTO updateByPrimaryKeySelectiveDTO(PO record) {
        updateByPrimaryKeySelective(record);
        return poToDto(record);
    }

    @Override
    public DTO updateByPrimaryKeySelectiveAndVersionDTO(PO record, Integer oldVersion) {
        updateByPrimaryKeySelectiveAndVersion(record, oldVersion);
        return poToDto(record);
    }

    //*************************delete***************************************

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Object key) {
        int i = mapper.deleteByPrimaryKey(key);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(PO record) {
        int i = mapper.delete(record);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKeys(List<Object> keys) {
        int i = 0;
        if (CollectionUtil.isNotEmpty(keys)) {
            for (Object key : keys) {
                int delete = deleteByPrimaryKey(key);
                i += delete;
            }
        }
        return i;
    }

    //*************************delete***************************************


}
