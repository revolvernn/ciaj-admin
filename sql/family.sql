DROP TABLE IF EXISTS `my_family`;
CREATE TABLE `my_family`  (
  `id` varchar(32)  NOT NULL COMMENT '主键',
  `name` varchar(50)  DEFAULT NULL COMMENT '家庭名称',
  `code` varchar(50)  DEFAULT NULL COMMENT '家庭编码',
  `enabled` char(1)  DEFAULT 'Y' COMMENT '是否可用Y/N',
  `area_id` varchar(32)  DEFAULT NULL COMMENT '家庭区域',
  `addr` varchar(255)  DEFAULT NULL COMMENT '家庭详细地址',
  `description` varchar(255)  DEFAULT NULL COMMENT '家庭描述',
  `create_at` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT 'N' COMMENT '删除标记',
  `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `version` int DEFAULT 1 COMMENT '版本号0为不可修改，1+可修改',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '我的家庭' ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `my_family_member`;
CREATE TABLE `my_family_member`  (
 `id` varchar(32)  NOT NULL COMMENT '主键',
 `family_id` varchar(32)  NOT NULL COMMENT '家庭ID',
 `user_id` varchar(32)  NOT NULL COMMENT '用户ID',
 `type` char(1)  DEFAULT '0' COMMENT '成员类型',
 `create_at` varchar(32)  DEFAULT NULL COMMENT '创建人',
 `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
 `update_at` varchar(32)  DEFAULT NULL COMMENT '更新人',
 `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
 `del_flag` char(1)  DEFAULT 'N' COMMENT '删除标记',
 `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
 `version` int DEFAULT 1 COMMENT '版本号0为不可修改，1+可修改',
 PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '我的家庭-成员' ROW_FORMAT = Dynamic;