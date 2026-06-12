DROP TABLE IF EXISTS `boot_db`.`task_scheduler`;

CREATE TABLE `boot_db`.`task_scheduler` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `type` varchar(32)  NOT NULL COMMENT '类型fixedRate、fixedDelay、cron',
  `name` varchar(255)  NOT NULL COMMENT '名称',
  `remark` varchar(255)  DEFAULT NULL COMMENT '描述',
  `bean_name` varchar(255)  NOT NULL COMMENT 'bean名称',
  `cron` varchar(56)  DEFAULT NULL COMMENT 'cron表达式',
  `delay` bigint(11) DEFAULT NULL COMMENT '间隔毫秒',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '0 ok 1 禁用',
  `create_at` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) DEFAULT 'N' COMMENT '删除标记',
  `version` int(11) DEFAULT '1' COMMENT '版本号0为不可修改，1+可修改',
  `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_bean_name` (`bean_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='任务调度';
