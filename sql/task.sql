DROP TABLE IF EXISTS `task_scheduler`;

CREATE TABLE `task_scheduler` (
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

/**
字典
**/
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('f382fdba5f1111f190300242ac110002', 'cron', 'cron', '688ac6b95f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002,688ac6b95f1111f190300242ac110002', 'taskType', 3, 0, 'Y', '定义：使用 Unix/Linux 风格的 cron 表达式 语法：秒 分 时 日 月 周 年（可选） 特点： 基于日历的调度 执行时间固定 适合在特定时间点执行任务', '1', '2026-06-03 14:05:12', '1', '2026-06-03 14:09:39', 'N', '2026-06-03 06:07:59', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('e72286f25f1111f190300242ac110002', 'fixedDelay', 'fixedDelay', '688ac6b95f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002,688ac6b95f1111f190300242ac110002', 'taskType', 3, 0, 'Y', '定义：固定延迟执行，从上一次结束时间开始计算间隔 特点： 保证任务执行间隔 不会出现任务重叠 适合需要保证任务串行执行的场景', '1', '2026-06-03 14:04:51', '1', '2026-06-03 14:09:06', 'N', '2026-06-03 06:07:27', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('d329edc05f1111f190300242ac110002', 'fixedRate', 'fixedRate', '688ac6b95f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002,688ac6b95f1111f190300242ac110002', 'taskType', 3, 0, 'Y', '定义：固定频率执行，从上一次开始时间开始计算间隔 特点： 固定频率，不关心任务执行时间 如果任务执行时间超过间隔，会立即开始下一次执行 可能造成任务重叠', '1', '2026-06-03 14:04:18', '1', '2026-06-03 14:09:25', 'N', '2026-06-03 06:07:45', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('a04e83cf5f1111f190300242ac110002', '停止', '1', '76c93e685f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002,76c93e685f1111f190300242ac110002', 'taskStatus', 3, 1, 'Y', NULL, '1', '2026-06-03 14:02:52', '1', '2026-06-03 14:02:52', 'N', '2026-06-03 06:01:13', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('917695b35f1111f190300242ac110002', '启动', '0', '76c93e685f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002,76c93e685f1111f190300242ac110002', 'taskStatus', 3, 0, 'Y', NULL, '1', '2026-06-03 14:02:27', '1', '2026-06-03 14:02:27', 'N', '2026-06-03 06:00:48', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('76c93e685f1111f190300242ac110002', '任务状态', 'taskStatus', '0ac96ba05f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002', 'task', 2, 0, 'Y', NULL, '1', '2026-06-03 14:01:43', '1', '2026-06-03 14:01:43', 'N', '2026-06-03 06:00:03', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('688ac6b95f1111f190300242ac110002', '任务类型', 'taskType', '0ac96ba05f1111f190300242ac110002', '0ac96ba05f1111f190300242ac110002', 'task', 2, 0, 'Y', NULL, '1', '2026-06-03 14:01:19', '1', '2026-06-03 14:01:19', 'N', '2026-06-03 05:59:39', 1);
INSERT INTO `sys_dict`(`id`, `name`, `code`, `parent_id`, `parent_ids`, `type`, `level`, `sequence`, `enabled`, `description`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('0ac96ba05f1111f190300242ac110002', '任务调度', 'task', '0', '0', 'taskScheduler', 1, 0, 'Y', NULL, '1', '2026-06-03 13:58:41', '1', '2026-06-03 14:00:58', 'N', '2026-06-03 05:59:19', 1);

/**
菜单
**/
INSERT INTO `sys_menu`(`id`, `name`, `parent_id`, `parent_ids`, `url`, `permission_code`, `type`, `icon`, `sequence`, `enabled`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `version`, `last_modify`) VALUES ('aa6e31b55f0c11f190300242ac110002', '任务调度', '6d0e33045f0c11f190300242ac110002', '6d0e33045f0c11f190300242ac110002', 'modules/task/scheduler.html', NULL, '1', 'el-icon-loading', 0, 'Y', '1', '2026-06-03 13:27:22', '1', '2026-06-03 13:27:22', 'N', 1, '2026-06-03 05:25:42');
INSERT INTO `sys_menu`(`id`, `name`, `parent_id`, `parent_ids`, `url`, `permission_code`, `type`, `icon`, `sequence`, `enabled`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `version`, `last_modify`) VALUES ('6d0e33045f0c11f190300242ac110002', '任务管理', '0', '0', NULL, NULL, '0', 'el-icon-loading', 0, 'Y', '1', '2026-06-03 13:25:39', '1', '2026-06-03 13:25:39', 'N', 1, '2026-06-03 05:23:59');

/**
权限
**/
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('cf8a1a6a5f0d11f190300242ac110002', '任务调度-删除', NULL, 'fun', 'task:scheduler:delFlag', 'task/scheduler/delFlag', '2b161dbf5f0d11f190300242ac110002', '009d45a05f0c11f190300242ac110002,2b161dbf5f0d11f190300242ac110002', 'Y', '1', '2026-06-03 13:35:33', '1', '2026-06-03 13:35:33', 'N', '2026-06-03 05:33:54', 1);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('b46ba5d05f0d11f190300242ac110002', '任务调度-导出', '', 'fun', 'task:scheduler:list:export', 'task/scheduler/list/export', '2b161dbf5f0d11f190300242ac110002', '009d45a05f0c11f190300242ac110002,2b161dbf5f0d11f190300242ac110002', 'Y', '1', '2026-06-03 13:34:48', '1', '2026-06-03 13:34:48', 'N', '2026-06-03 05:33:09', 1);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('836d58da5f0d11f190300242ac110002', '任务调度-新增', NULL, 'fun', 'task:scheduler:add', 'task/scheduler/add', '2b161dbf5f0d11f190300242ac110002', '009d45a05f0c11f190300242ac110002,2b161dbf5f0d11f190300242ac110002', 'Y', '1', '2026-06-03 13:33:26', '1', '2026-06-03 13:33:26', 'N', '2026-06-03 05:31:46', 1);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('697b7a605f0d11f190300242ac110002', '任务调度-更新', NULL, 'fun', 'task:scheduler:update', 'task/scheduler/update', '2b161dbf5f0d11f190300242ac110002', '009d45a05f0c11f190300242ac110002,2b161dbf5f0d11f190300242ac110002', 'Y', '1', '2026-06-03 13:32:42', '1', '2026-06-03 13:32:42', 'N', '2026-06-03 05:31:03', 1);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('2b161dbf5f0d11f190300242ac110002', '任务调度', NULL, 'menu', NULL, 'aa6e31b55f0c11f190300242ac110002', '009d45a05f0c11f190300242ac110002', '009d45a05f0c11f190300242ac110002', 'Y', '1', '2026-06-03 13:30:58', '1', '2026-06-03 13:31:38', 'N', '2026-06-03 05:29:59', 2);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('fe2630735f0c11f190300242ac110002', '任务调度-列表', '', 'fun', 'task:scheduler:list,task:scheduler:getById', 'task/scheduler/list,task/scheduler/getById', '2b161dbf5f0d11f190300242ac110002', '009d45a05f0c11f190300242ac110002,2b161dbf5f0d11f190300242ac110002', 'Y', '1', '2026-06-03 13:29:42', '1', '2026-06-03 13:31:48', 'N', '2026-06-03 05:30:09', 2);
INSERT INTO `sys_permission`(`id`, `name`, `description`, `type`, `permission_code`, `url`, `parent_id`, `parent_ids`, `available`, `create_at`, `create_time`, `update_at`, `update_time`, `del_flag`, `last_modify`, `version`) VALUES ('009d45a05f0c11f190300242ac110002', '任务管理', NULL, 'dir', NULL, NULL, '0', '0', 'Y', '1', '2026-06-03 13:22:37', '1', '2026-06-03 13:28:05', 'N', '2026-06-03 05:26:25', 2);