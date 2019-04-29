package com.ciaj.boot.modules.sys.service.impl;

import com.ciaj.boot.modules.sys.mapper.SysCommMapper;
import com.ciaj.boot.modules.sys.service.SysCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author Ciaj.
 * @Date 2019/4/29 16:48
 * @Version 1.0
 */
@Service
public class SysCommServiceImpl implements SysCommService {

	@Autowired
	private SysCommMapper sysCommMapper;
	@Override
	public List<Map<String, Object>> selectTableStatus() {
		return sysCommMapper.selectTableStatus();
	}
}
