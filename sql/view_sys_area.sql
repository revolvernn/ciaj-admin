CREATE OR REPLACE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `view_sys_area` AS SELECT
	`a`.`id` AS `id`,
	`a`.`name` AS `name`,
	`a`.`type` AS `type`,
	`a`.`level` AS `level`,
	`a`.`enabled` AS `enabled`,
	`a`.`parent_ids` AS `parent_ids`,
	`a`.`parent_id` AS `parent_id`,
	`a`.`sequence` AS `sequence`,
	`a`.`name` AS `area_name`,
	`a`.`id` AS `nationality_id`,
	`a`.`name` AS `nationality_name`,
	`a`.`type` AS `nationality_type`,
	`a`.`level` AS `nationality_level`,
	`a`.`enabled` AS `nationality_enabled`,
	NULL AS `province_id`,
	NULL AS `province_name`,
	NULL AS `province_type`,
	NULL AS `province_level`,
	NULL AS `province_enabled`,
	NULL AS `city_id`,
	NULL AS `city_name`,
	NULL AS `city_type`,
	NULL AS `city_level`,
	NULL AS `city_enabled`,
	NULL AS `district_id`,
	NULL AS `district_name`,
	NULL AS `district_type`,
	NULL AS `district_level`,
	NULL AS `district_enabled`
FROM
	`sys_area` `a`
WHERE
	( `a`.`type` = 'nationality' ) UNION ALL
SELECT
	`b`.`id` AS `id`,
	`b`.`name` AS `name`,
	`b`.`type` AS `type`,
	`b`.`level` AS `level`,
	`b`.`enabled` AS `enabled`,
	`b`.`parent_ids` AS `parent_ids`,
	`b`.`parent_id` AS `parent_id`,
	`b`.`sequence` AS `sequence`,
	concat_ws( '-', `a`.`name`, `b`.`name` ) AS `area_name`,
	`a`.`id` AS `nationality_id`,
	`a`.`name` AS `nationality_name`,
	`a`.`type` AS `nationality_type`,
	`a`.`level` AS `nationality_level`,
	`a`.`enabled` AS `nationality_enabled`,
	`b`.`id` AS `province_id`,
	`b`.`name` AS `province_name`,
	`b`.`type` AS `province_type`,
	`b`.`level` AS `province_level`,
	`b`.`enabled` AS `province_enabled`,
	NULL AS `city_id`,
	NULL AS `city_name`,
	NULL AS `city_type`,
	NULL AS `city_level`,
	NULL AS `city_enabled`,
	NULL AS `district_id`,
	NULL AS `district_name`,
	NULL AS `district_type`,
	NULL AS `district_level`,
	NULL AS `district_enabled`
FROM
	(
		`sys_area` `a`
		JOIN `sys_area` `b` ON (((
					`a`.`id` = `b`.`parent_id`
					)
			AND ( `b`.`type` = 'province' ))))
WHERE
	( `a`.`type` = 'nationality' ) UNION ALL
SELECT
	`c`.`id` AS `id`,
	`c`.`name` AS `name`,
	`c`.`type` AS `type`,
	`c`.`level` AS `level`,
	`c`.`enabled` AS `enabled`,
	`c`.`parent_ids` AS `parent_ids`,
	`c`.`parent_id` AS `parent_id`,
	`c`.`sequence` AS `sequence`,
	concat_ws( '-', `a`.`name`, `b`.`name`, `c`.`name` ) AS `area_name`,
	`a`.`id` AS `nationality_id`,
	`a`.`name` AS `nationality_name`,
	`a`.`type` AS `nationality_type`,
	`a`.`level` AS `nationality_level`,
	`a`.`enabled` AS `nationality_enabled`,
	`b`.`id` AS `province_id`,
	`b`.`name` AS `province_name`,
	`b`.`type` AS `province_type`,
	`b`.`level` AS `province_level`,
	`b`.`enabled` AS `province_enabled`,
	`c`.`id` AS `city_id`,
	`c`.`name` AS `city_name`,
	`c`.`type` AS `city_type`,
	`c`.`level` AS `city_level`,
	`c`.`enabled` AS `city_enabled`,
	NULL AS `district_id`,
	NULL AS `district_name`,
	NULL AS `district_type`,
	NULL AS `district_level`,
	NULL AS `district_enabled`
FROM
	((
			`sys_area` `a`
			JOIN `sys_area` `b` ON (((
						`a`.`id` = `b`.`parent_id`
						)
				AND ( `b`.`type` = 'province' ))))
		JOIN `sys_area` `c` ON (((
					`b`.`id` = `c`.`parent_id`
					)
			AND ( `c`.`type` = 'city' ))))
WHERE
	( `a`.`type` = 'nationality' ) UNION ALL
SELECT
	`d`.`id` AS `id`,
	`d`.`name` AS `name`,
	`d`.`type` AS `type`,
	`d`.`level` AS `level`,
	`d`.`enabled` AS `enabled`,
	`d`.`parent_ids` AS `parent_ids`,
	`d`.`parent_id` AS `parent_id`,
	`d`.`sequence` AS `sequence`,
	concat_ws( '-', `a`.`name`, `b`.`name`, `c`.`name`, `d`.`name` ) AS `area_name`,
	`a`.`id` AS `nationality_id`,
	`a`.`name` AS `nationality_name`,
	`a`.`type` AS `nationality_type`,
	`a`.`level` AS `nationality_level`,
	`a`.`enabled` AS `nationality_enabled`,
	`b`.`id` AS `province_id`,
	`b`.`name` AS `province_name`,
	`b`.`type` AS `province_type`,
	`b`.`level` AS `province_level`,
	`b`.`enabled` AS `province_enabled`,
	`c`.`id` AS `city_id`,
	`c`.`name` AS `city_name`,
	`c`.`type` AS `city_type`,
	`c`.`level` AS `city_level`,
	`c`.`enabled` AS `city_enabled`,
	`d`.`id` AS `district_id`,
	`d`.`name` AS `district_name`,
	`d`.`type` AS `district_type`,
	`d`.`level` AS `district_level`,
	`d`.`enabled` AS `district_enabled`
FROM
	(((
				`sys_area` `a`
				JOIN `sys_area` `b` ON (((
							`a`.`id` = `b`.`parent_id`
							)
					AND ( `b`.`type` = 'province' ))))
			JOIN `sys_area` `c` ON (((
						`b`.`id` = `c`.`parent_id`
						)
				AND ( `c`.`type` = 'city' ))))
		JOIN `sys_area` `d` ON (((
					`c`.`id` = `d`.`parent_id`
					)
			AND ( `d`.`type` = 'district' ))))
WHERE
	(
	`a`.`type` = 'nationality');