package com.ciaj.base;

import com.ciaj.comm.utils.Page;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author: Ciaj.
 * @Date: 2018/6/1 10:27
 * @Description:
 */
public interface BaseService<PO, DTO extends BaseEntity, VO extends VOEntity> extends Mapper<PO, DTO, VO> {
	void insertOrUpdatePre(PO record, String opt);
	void insertOrUpdatePre(List<PO> records, String opt);
	/**
	 * T TO D
	 *
	 * @param t
	 * @return DTO
	 */
	DTO poToDto(PO t);

	/**
	 * D TO T
	 *
	 * @param d
	 * @return PO
	 */
	PO dtoToPo(DTO d);

	/**
	 *
	 * @param pos PO
	 * @return List<DTO>
	 */
	List<DTO> posToDtos(List<PO> pos);


	/**
	 *
	 * @param dtos DTO
	 * @return List<VO>
	 */
	List<VO> dtosToVos(List<DTO> dtos);

	/**
	 * 返回PO
	 *
	 * @param entity PO
	 * @return PO
	 */
	PO insertPO(PO entity);

	/**
	 *
	 * @param pos PO
	 * @return int
	 */
	int insertPOs(List<PO> pos);

	/**
	 * 返回DTO
	 *
	 * @param entity PO
	 * @return DTO
	 */
	DTO insertDTO(PO entity);

	/**
	 * 返回DTO
	 *
	 * @param entity PO
	 * @return DTO
	 */
	DTO insertSelectiveDTO(PO entity);

	/**
	 * 返回PO
	 *
	 * @param entity PO
	 * @return PO
	 */
	PO insertSelectivePO(PO entity);

	/**
	 * @param record PO
	 * @return PO
	 */
	PO updateByPrimaryKeyPO(PO record);

	/**
	 * @param record PO
	 * @return DTO
	 */
	DTO updateByPrimaryKeyDTO(PO record);

	/**
	 * @param record
	 * @return
	 */
	PO updateByPrimaryKeySelectivePO(PO record);

	/**
	 *
	 * @param record
	 * @param oldVersion
	 * @return
	 */
	PO updateByPrimaryKeySelectiveAndVersionPO(PO record, Integer oldVersion);

	/**
	 * @param record
	 * @return
	 */
	DTO updateByPrimaryKeySelectiveDTO(PO record);


	/**
	 * @param record
	 * @param oldVersion
	 * @return
	 */
	DTO updateByPrimaryKeySelectiveAndVersionDTO(PO record, Integer oldVersion);

	/**
	 *
	 * @param keys
	 * @return
	 */
	int deleteByPrimaryKeys(List<Object> keys);

	/**
	 * 分页查询
	 *
	 * @param entity PO
	 * @return 	Page<PO>
	 */
	Page<PO> selectPOPage(PO entity);


	/**
	 * 分页查询
	 *
	 * @param entity PO
	 * @return Page<DTO>
	 */
	Page<DTO> selectDTOPage(PO entity);

	/**
	 * 分页查询
	 *
	 * @param entity VO
	 * @return Page<PO>
	 */
	Page<PO> selectPOPage(VO entity);

	/**
	 * 分页查询
	 *
	 * @param entity VO
	 * @return Page<DTO>
	 */
	Page<DTO> selectDTOPage(VO entity);


	/**
	 * 分页查询
	 *
	 * @param entity VO
	 * @return Page<VO>
	 */
	Page<VO> selectVoPage(VO entity);

	/**
	 * @param entity VO
	 * @return List<DTO>
	 */
    @Override
    List<DTO> selectDTOList(VO entity);

}
