package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.boot.modules.sys.entity.po.SysAreaPo;
import com.ciaj.base.Mapper;
import com.ciaj.boot.modules.sys.entity.vo.SysAreaVo;
import com.ciaj.boot.modules.sys.entity.dto.SysAreaDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2019-04-12 15:54:06
 * @Description: www.ciaj.com DAO
 */
public interface SysAreaMapper extends Mapper<SysAreaPo, SysAreaDto, SysAreaVo> {

    /**
     * 根据ID向上递归查询
     * @param id
     * @return
     */
    List<SysAreaDto> selectRecursiveListById(@Param("id") String id);
    /**
     * 根据ID向上递归查询
     * @param ids
     * @return
     */
    List<SysAreaDto> selectRecursiveListByIds(@Param("ids") List<String> ids);
    /**
     * 根据ID向上递归查询
     * @param id
     * @return
     */
    SysAreaDto selectListViewById(@Param("id") String id);
    /**
     * 根据ID向上递归查询
     * @param ids
     * @return
     */
    List<SysAreaDto> selectListViewByIds(@Param("ids") List<String> ids);
}
