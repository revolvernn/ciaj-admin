DROP TABLE IF EXISTS `boot_db`.`wpe_electrician_record`;

CREATE TABLE `boot_db`.`wpe_electrician_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `project_id` varchar(32) DEFAULT NULL COMMENT '工程项目ID',
  `workday` datetime DEFAULT NULL COMMENT '工作日',
  `work_start` datetime DEFAULT NULL COMMENT '工作开始时间',
  `work_end` datetime DEFAULT NULL COMMENT '工作日结束时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '工作状态',
  `create_at` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `version` int(11) DEFAULT '1' COMMENT '版本号0为不可修改，1+可修改',
  `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='水电工记录表';

DROP TABLE IF EXISTS `boot_db`.`wpe_project`;

CREATE TABLE `boot_db`.`wpe_project` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `project_name` varchar(255) DEFAULT NULL COMMENT '工程项目名称',
  `addr` varchar(255) DEFAULT NULL COMMENT '地址',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `house_type` VARCHAR(32) DEFAULT NULL COMMENT '项目户型',
  `decoration_type` VARCHAR(32) DEFAULT NULL COMMENT '装修类型',
  `create_at` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT 'N' COMMENT '删除标记',
  `version` int(11) DEFAULT '1' COMMENT '版本号0为不可修改，1+可修改',
  `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='水电工项目表';
