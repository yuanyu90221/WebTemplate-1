DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL,
  `mobile_no` varchar(10) NOT NULL,
  `line_id` varchar(50) NOT NULL,
  `enabled` char(1) NOT NULL,
  `create_time` date NOT NULL,
  `last_signin_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `user_id` int(10) NOT NULL,
  `role` varchar(10) NOT NULL,
  CONSTRAINT unique_role UNIQUE (`user_id`, `role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `prod_id` int(10) NOT NULL AUTO_INCREMENT,
  `prod_name` varchar(10) NOT NULL,
  PRIMARY KEY (`prod_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `consume`;
CREATE TABLE `consume` (
  `consume_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `consume_date` date NOT NULL,
  `type` tinyint(1) NOT NULL,
  `prod_name` varchar(20) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `lottery_no` varchar(8) NOT NULL,
  `prize` decimal(10,0) NOT NULL,
  `is_got` char(1) NOT NULL,
  PRIMARY KEY (`consume_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;