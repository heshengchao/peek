

CREATE TABLE `appinstance` (
  `ins_id` varchar(20) PRIMARY KEY,
  `ins_name` varchar(20) DEFAULT NULL,
  `ins_ip` varchar(20) DEFAULT NULL,
  `ins_port` int(11) DEFAULT NULL,
  `group_id` varchar(20) DEFAULT NULL,
  `group_name` varchar(20) DEFAULT NULL,
  `create_user` varchar(2) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB 

CREATE TABLE `app_group` (
  `group_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `group_name` varchar(50) DEFAULT NULL,
  `create_user` varchar(30) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8