/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.20-0ubuntu0.16.04.1 : Database - TIP_CRM
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`TIP_CRM` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `TIP_CRM`;

/*Table structure for table `communication` */

DROP TABLE IF EXISTS `communication`;

CREATE TABLE `communication` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `customer_contact_id` int(11) NOT NULL,
  `follow_user_id` int(11) NOT NULL,
  `communicate_time` datetime(3) NOT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `next_communicate_time` datetime(3) DEFAULT NULL,
  `on_side` tinyint(1) NOT NULL COMMENT '是否上门服务',
  PRIMARY KEY (`oid`),
  KEY `communicationI1` (`customer_id`),
  KEY `communicationI3` (`follow_user_id`),
  KEY `communicationI2` (`customer_contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `configuration` */

DROP TABLE IF EXISTS `configuration`;

CREATE TABLE `configuration` (
  `oid` int(11) NOT NULL,
  `key` varchar(100) COLLATE utf8_bin NOT NULL,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `configuration` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `contract` */

DROP TABLE IF EXISTS `contract`;

CREATE TABLE `contract` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `customer_master_id` int(11) NOT NULL,
  `follow_user_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `sign_time` datetime(3) NOT NULL,
  `review_id` int(11) NOT NULL,
  `review_status_id` int(11) NOT NULL,
  `review_time` datetime(3) NOT NULL,
  `review_note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `contractI1` (`customer_master_id`),
  KEY `contractI2` (`follow_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `contract_project` */

DROP TABLE IF EXISTS `contract_project`;

CREATE TABLE `contract_project` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `contract_projectI1` (`contract_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `status_id` int(11) NOT NULL,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `follow_user_id` int(11) DEFAULT NULL,
  `follow_department_id` int(11) DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `customerI1` (`name`),
  KEY `customerI2` (`follow_user_id`),
  KEY `customerI3` (`follow_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `customer_approval` */

DROP TABLE IF EXISTS `customer_approval`;

CREATE TABLE `customer_approval` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `status_id` int(11) NOT NULL,
  `address` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `follow_user_id` int(11) DEFAULT NULL,
  `follow_department_id` bigint(20) DEFAULT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `review_id` int(11) DEFAULT NULL,
  `review_time` datetime(3) DEFAULT NULL,
  `review_status_id` int(11) DEFAULT NULL,
  `review_note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `customer_approvalI1` (`name`),
  KEY `customer_approvalI2` (`follow_user_id`),
  KEY `customer_approvalI3` (`follow_department_id`),
  KEY `customer_approvalI4` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `customer_contact` */

DROP TABLE IF EXISTS `customer_contact`;

CREATE TABLE `customer_contact` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `phone_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `job` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `customer_contactI1` (`customer_id`),
  KEY `customer_contactI2` (`name`,`phone_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `departmentI1` (`name`),
  KEY `departmentI2` (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `goal` */

DROP TABLE IF EXISTS `goal`;

CREATE TABLE `goal` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `goal` decimal(10,2) NOT NULL,
  `goal_time` datetime(3) NOT NULL,
  `entity_type` int(11) NOT NULL,
  `entity_id` int(10) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `goalI1` (`entity_type`,`entity_id`,`goal_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `level` */

DROP TABLE IF EXISTS `level`;

CREATE TABLE `level` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `default_payment_percent` decimal(4,2) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `levelI1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `list_box` */

DROP TABLE IF EXISTS `list_box`;

CREATE TABLE `list_box` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sequence` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `dalete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `editable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `list_boxI1` (`category_name`,`name`,`sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `icon` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `permission` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `menuI1` (`name`),
  KEY `menuI2` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `to_user_id` int(11) NOT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `content` text,
  `type` bigint(20) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `read_status` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  KEY `notificationI1` (`to_user_id`),
  KEY `notificationI2` (`type`),
  KEY `notificationI3` (`read_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `value` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `permissionI2` (`value`),
  KEY `permissionI1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `oid` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `projectI1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `editable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `roleI1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `role_permission` */

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `role_permissionI1` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `security` */

DROP TABLE IF EXISTS `security`;

CREATE TABLE `security` (
  `user_id` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `oid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `id_card` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime(3) DEFAULT NULL,
  `phone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` int(11) NOT NULL,
  `hire_id` int(11) NOT NULL,
  `hire_time` datetime(3) NOT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `dismiss_id` int(11) DEFAULT NULL,
  `dismiss_date` datetime(3) DEFAULT NULL,
  `dismiss_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `payment_percent` decimal(4,2) NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `userI2` (`email`),
  UNIQUE KEY `userI4` (`phone_no`),
  KEY `userI1` (`username`),
  KEY `userI3` (`status`),
  KEY `userI5` (`manager_id`),
  KEY `userI6` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `oid` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`oid`),
  UNIQUE KEY `user_roleI1` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
insert  into `configuration`(`oid`,`key`,`value`) values
(1,'REGISTABLE','TRUE');

insert  into `list_box`(`oid`,`category_name`,`name`,`display_name`,`sequence`,`entry_id`,`entry_time`,`update_id`,`update_time`,`dalete_id`,`delete_time`,`editable`) values
(1,'CUSTOMER_STATUS','NEW_CUSTOMER','新客户',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(2,'CUSTOMER_STATUS','INTENTIONAL_CUSTOMER','意向客户',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(3,'CUSTOMER_STATUS','SIGNING_CUSTOMER','签约客户',3,-1,NOW(),NULL,NULL,NULL,NULL,0),
(4,'CUSTOMER_STATUS','EXPIRED_CUSTOMER','过期客户',4,-1,NOW(),NULL,NULL,NULL,NULL,0),
(5,'APPROVAL_STATUS','PENDING','待审批',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(6,'APPROVAL_STATUS','REJECT','驳回',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(7,'APPROVAL_STATUS','APPROVE','通过',3,-1,NOW(),NULL,NULL,NULL,NULL,0),
(8,'GOAL_TYPE','USER','员工目标',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(9,'GOAL_TYPE','DEPARTMENT','部门目标',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(10,'USER_STATUS','ACTIVE','正常',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(11,'USER_STATUS','FROZEN','冻结',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(12,'NOTIFICATION_TYPE','SYSTEM_NOTIFICATION','系统通知',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(13,'NOTIFICATION_TYPE','USER_NOTIFICATION','用户通知',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(14,'NOTIFICATION_READ_STATUS','READ','已读',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(15,'NOTIFICATION_READ_STATUS','UNREAD','未读',2,-1,NOW(),NULL,NULL,NULL,NULL,0);

insert  into `menu`(`oid`,`name`,`display_name`,`icon`,`parent_id`,`permission`,`url`) values
(12,'HOME','主页','home',NULL,NULL,'index'),
(13,'MY_CUSTOMER','我的客户','team',NULL,NULL,'my/customer'),
(14,'MY_SUMMARY','销售报表','pie-chart',NULL,NULL,'my/summary'),
(15,'FINANCE','财务管理','pay-circle-o',NULL,NULL,'finance'),
(16,'MATERIAL','资料库','file-text',NULL,NULL,'material'),
(17,'SETTING','系统设置','setting',NULL,NULL,''),
(18,'CUSTOMER_MANAGEMENT','客户管理','team',17,NULL,'management/customer'),
(19,'USER_MANAGEMENT','员工管理','usergroup-add',17,'user:view','management/user'),
(20,'APPROVAL_MANAGEMENT','审批管理','check-circle-o',17,NULL,'management/approval'),
(21,'PERMISSION_MANAGEMENT','权限管理','unlock',17,NULL,'management/permission'),
(22,'ROLE_MANAGEMENT','角色管理','solution',17,NULL,'management/role'),
(23,'MATERIAL_MANAGEMENT','资料管理','book',17,NULL,'management/material');

insert  into `permission`(`oid`,`group_name`,`name`,`value`,`display_name`,`entry_id`,`entry_time`,`update_id`,`update_time`,`delete_id`,`delete_time`) values
(1,'客户管理','ADD_UPDATE_CUSTOMER','customer:add_update','添加/修改客户',-1,NOW(),NULL,NULL,NULL,NULL),
(2,'客户管理','DELETE_CUSTOMER','customer:delete','删除客户',-1,NOW(),NULL,NULL,NULL,NULL),
(3,'客户管理','TRANSFER_CUSTOMER','customer:transfer','转移客户',-1,NOW(),NULL,NULL,NULL,NULL),
(4,'客户管理','APPROVAL_CUSTOMER','cusotmer:approval','审批客户',-1,NOW(),NULL,NULL,NULL,NULL),
(5,'员工管理','USER_ADD','user:add_update','添加/修改员工',-1,NOW(),NULL,NULL,NULL,NULL),
(6,'员工管理','USER_DELETE','user:delete','员工离职',-1,NOW(),NULL,NULL,NULL,NULL),
(7,'角色与权限','ROLE_ADD','role:add','添加角色',-1,NOW(),NULL,NULL,NULL,NULL),
(8,'角色与权限','ROLE_ASSIGN','role:assign','角色分配',-1,NOW(),NULL,NULL,NULL,NULL);

insert  into `role`(`oid`,`name`,`display_name`,`entry_id`,`entry_time`,`update_id`,`update_time`,`delete_id`,`delete_time`,`editable`) values
(1,'GENERAL_MANAGER','总经理',-1,NOW(),NULL,NULL,NULL,NULL,0),
(2,'NORMAL','员工',-1,NOW(),NULL,NULL,NULL,NULL,0);

insert  into `user`(`oid`,`username`,`email`,`id_card`,`birthday`,`phone_no`,`avatar`,`status`,`hire_id`,`hire_time`,`manager_id`,`department_id`,`level_id`,`dismiss_id`,`dismiss_date`,`dismiss_reason`,`update_id`,`update_time`,`payment_percent`) values
(-1,'SYSTEM','crm@tip.com',NULL,NULL,NULL,NULL,10,-1,NOW(),NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0.00);