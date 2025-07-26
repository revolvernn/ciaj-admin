CREATE TABLE `sys_auth` (
  `id` varchar(32)  NOT NULL COMMENT '主键',
  `user_id` varchar(32)  DEFAULT NULL COMMENT '图片URL',
  `password` varchar(255)  DEFAULT NULL COMMENT '密码',
  `salt` varchar(255)  DEFAULT NULL COMMENT '密码盐',
  `token` varchar(255)  DEFAULT NULL COMMENT 'token',
  `type` varchar(10)  DEFAULT NULL COMMENT '验证方式：pwd/token',
  `create_at` varchar(32)  DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32)  DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1)  DEFAULT 'N' COMMENT '删除标记',
  `version` int(11) DEFAULT '1' COMMENT '版本号0为不可修改，1+可修改',
  `last_modify` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统-权限验证';