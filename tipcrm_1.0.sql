CREATE DATABASE  IF NOT EXISTS `TIP_CRM` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `TIP_CRM`;
-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: www.potafish.com    Database: TIP_CRM
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `communication`
--

DROP TABLE IF EXISTS `communication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `communication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `customer_contact_id` int(11) NOT NULL,
  `follow_user_id` int(11) NOT NULL,
  `communicate_time` datetime(3) NOT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `next_communicate_time` datetime(3) DEFAULT NULL,
  `on_side` tinyint(1) NOT NULL COMMENT '是否上门服务',
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `communicationI1` (`customer_id`),
  KEY `communicationI3` (`follow_user_id`),
  KEY `communicationI2` (`customer_contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `communication`
--

LOCK TABLES `communication` WRITE;
/*!40000 ALTER TABLE `communication` DISABLE KEYS */;
/*!40000 ALTER TABLE `communication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `key` varchar(100) COLLATE utf8_bin NOT NULL,
  `value` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `configuration` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES
(1,'REGISTABLE','TRUE');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `follow_user_id` int(11) NOT NULL,
  `follow_department_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `sign_time` datetime(3) NOT NULL,
  `review_id` int(11) NOT NULL,
  `review_status_id` int(11) NOT NULL,
  `review_time` datetime(3) NOT NULL,
  `review_note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contractI1` (`customer_id`),
  KEY `contractI2` (`follow_user_id`),
  KEY `contractI3` (`follow_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_project`
--

DROP TABLE IF EXISTS `contract_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contract_projectI1` (`contract_id`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_project`
--

LOCK TABLES `contract_project` WRITE;
/*!40000 ALTER TABLE `contract_project` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`id`),
  KEY `customerI1` (`name`),
  KEY `customerI2` (`follow_user_id`),
  KEY `customerI3` (`follow_department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_approval`
--

DROP TABLE IF EXISTS `customer_approval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_approval` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `opt_type` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `customer_approvalI1` (`name`),
  KEY `customer_approvalI2` (`follow_user_id`),
  KEY `customer_approvalI3` (`follow_department_id`),
  KEY `customer_approvalI4` (`customer_id`)
  KEY `customer_approvalI5` (`opt_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_approval`
--

LOCK TABLES `customer_approval` WRITE;
/*!40000 ALTER TABLE `customer_approval` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_approval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_contact`
--

DROP TABLE IF EXISTS `customer_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `approval_id` int(11) DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `phone_no` varchar(20) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `job` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `note` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `active` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_contactI1` (`customer_id`),
  KEY `customer_contactI2` (`name`,`phone_no`)
  KEY `customer_contactI3` (`approval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_contact`
--

LOCK TABLES `customer_contact` WRITE;
/*!40000 ALTER TABLE `customer_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `departmentI1` (`name`),
  KEY `departmentI2` (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goal` decimal(10,2) NOT NULL,
  `goal_time` datetime(3) NOT NULL,
  `entity_type` int(11) NOT NULL,
  `entity_id` int(10) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `goalI1` (`entity_type`,`entity_id`,`goal_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal` DISABLE KEYS */;
/*!40000 ALTER TABLE `goal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `default_payment_percent` decimal(4,2) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `deletable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `levelI1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level` DISABLE KEYS */;
INSERT INTO `level` VALUES
(1,'NEW_USER','新员工',0.00,-1,NOW(),NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list_box`
--

DROP TABLE IF EXISTS `list_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_box` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `sequence` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `editable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `list_boxI1` (`category_name`,`name`),
  UNIQUE KEY `list_boxI2` (`category_name`,`sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_box`
--

LOCK TABLES `list_box` WRITE;
/*!40000 ALTER TABLE `list_box` DISABLE KEYS */;
INSERT INTO `list_box` VALUES
(1,'CUSTOMER_STATUS','NEW_CUSTOMER','新客户',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(2,'CUSTOMER_STATUS','INTENTIONAL_CUSTOMER','意向客户',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(3,'CUSTOMER_STATUS','SIGNING_CUSTOMER','签约客户',3,-1,NOW(),NULL,NULL,NULL,NULL,0),
(4,'CUSTOMER_STATUS','EXPIRED_CUSTOMER','过期客户',4,-1,NOW(),NULL,NULL,NULL,NULL,0),
(5,'APPROVAL_STATUS','PENDING','待审批',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(6,'APPROVAL_STATUS','REJECTED','已驳回',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(7,'APPROVAL_STATUS','APPROVEED','已通过',3,-1,NOW(),NULL,NULL,NULL,NULL,0),
(8,'GOAL_TYPE','USER','员工目标',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(9,'GOAL_TYPE','DEPARTMENT','部门目标',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(10,'USER_STATUS','ACTIVE','正常',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(11,'USER_STATUS','FROZEN','冻结',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(12,'NOTIFICATION_TYPE','SYSTEM_NOTIFICATION','系统通知',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(13,'NOTIFICATION_TYPE','USER_NOTIFICATION','用户通知',2,-1,NOW(),NULL,NULL,NULL,NULL,0),
(14,'NOTIFICATION_READ_STATUS','READ','已读',1,-1,NOW(),NULL,NULL,NULL,NULL,0),
(15,'NOTIFICATION_READ_STATUS','UNREAD','未读',2,-1,NOW(),NULL,NULL,NULL,NULL,0);
(16,'OPERATION_TYPE','ADD','新增',1,-1,NOW(),NULL,NULL,NULL,NULL,0);
(17,'OPERATION_TYPE','UPDATE','修改',2,-1,NOW(),NULL,NULL,NULL,NULL,0);
(18,'OPERATION_TYPE','REMOVE','删除',3,-1,NOW(),NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `list_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `icon` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `permission` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `menuI1` (`name`),
  KEY `menuI2` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES
(1,'HOME','主页','home',NULL,NULL,'index'),
(2,'MY_CUSTOMER','我的客户','team',NULL,NULL,'my/customer'),
(3,'MY_SUMMARY','销售报表','pie-chart',NULL,NULL,'my/summary'),
(4,'FINANCE','财务管理','pay-circle-o',NULL,NULL,'finance'),
(5,'MATERIAL','资料库','file-text',NULL,NULL,'material'),
(6,'SETTING','系统设置','setting',NULL,NULL,''),
(7,'CUSTOMER_MANAGEMENT','客户管理','team',6,NULL,'management/customer'),
(8,'USER_MANAGEMENT','员工管理','usergroup-add',6,'user:view','management/user'),
(9,'APPROVAL_MANAGEMENT','审批管理','check-circle-o',6,NULL,'management/approval'),
(10,'PERMISSION_MANAGEMENT','权限管理','unlock',6,NULL,'management/permission'),
(11,'ROLE_MANAGEMENT','角色管理','solution',6,NULL,'management/role'),
(12,'MATERIAL_MANAGEMENT','资料管理','book',6,NULL,'management/material');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `to_user_id` int(11) NOT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `content` text,
  `type` int(11) NOT NULL,
  `read_status` int(11) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notificationI1` (`to_user_id`),
  KEY `notificationI2` (`type`),
  KEY `notificationI3` (`read_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `permissionI2` (`value`),
  KEY `permissionI1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES
(1,'客户管理','CUSTOMER_ADD_UPDATE','customer:add_update','添加/修改客户','-1',NOW(),NULL,NULL,NULL,NULL),
(2,'客户管理','CUSTOMER_DELETE','customer:delete','删除客户','-1',NOW(),NULL,NULL,NULL,NULL),
(3,'客户管理','CUSTOMER_TRANSFER','customer:transfer','转移客户','-1',NOW(),NULL,NULL,NULL,NULL),
(4,'客户管理','CUSTOMER_APPROVAL','cusotmer:approval','审批客户','-1',NOW(),NULL,NULL,NULL,NULL),
(5,'员工管理','USER_ADD_UPDATE','user:add_update','添加/修改员工','-1',NOW(),NULL,NULL,NULL,NULL),
(6,'员工管理','USER_DELETE','user:delete','员工离职','-1',NOW(),NULL,NULL,NULL,NULL),
(7,'角色与权限','ROLE_ADD','role:add','添加角色','-1',NOW(),NULL,NULL,NULL,NULL),
(8,'角色与权限','ROLE_ASSIGN','role:assign','角色分配','-1',NOW(),NULL,NULL,NULL,NULL),
(9,'部门管理','DEPARTMENT_ADD_UPDATE','department:add_update','添加/修改部门','-1',NOW(),NULL,NULL,NULL,NULL),
(10,'部门管理','DEPARTMENT_DELETE','department:delete','删除部门','-1',NOW(),NULL,NULL,NULL,NULL);

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `projectI1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `display_name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  `delete_id` int(11) DEFAULT NULL,
  `delete_time` datetime(3) DEFAULT NULL,
  `editable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleI1` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES
(1,'GENERAL_MANAGER','总经理',-1,NOW(),NULL,NULL,NULL,NULL,0),
(2,'NORMAL','员工',-1,NOW(),NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `deletable` tinyint(1) NOT NULL,
  `entry_id` int(11) NOT NULL,
  `entry_time` datetime(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permissionI1` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES
(1,1,1,0,-1,NOW()),
(2,1,2,0,-1,NOW()),
(3,1,3,0,-1,NOW()),
(4,1,4,0,-1,NOW()),
(5,1,5,0,-1,NOW()),
(6,1,6,0,-1,NOW()),
(7,1,7,0,-1,NOW()),
(8,1,8,0,-1,NOW());
(9,1,9,0,-1,NOW());
(10,1,10,0,-1,NOW());
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security`
--

DROP TABLE IF EXISTS `security`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security` (
  `user_id` int(11) NOT NULL,
  `password` varchar(50) NOT NULL,
  `salt` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security`
--

LOCK TABLES `security` WRITE;
/*!40000 ALTER TABLE `security` DISABLE KEYS */;
/*!40000 ALTER TABLE `security` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `email` varchar(50) COLLATE utf8_bin NOT NULL,
  `id_card` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime(3) DEFAULT NULL,
  `phone_no` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `motto` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) NOT NULL,
  `hire_id` int(11) NOT NULL,
  `hire_time` datetime(3) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `payment_percent` decimal(4,2) NOT NULL,
  `dismiss_id` int(11) DEFAULT NULL,
  `dismiss_date` datetime(3) DEFAULT NULL,
  `dismiss_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_id` int(11) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userI2` (`email`),
  UNIQUE KEY `userI4` (`phone_no`),
  KEY `userI1` (`username`),
  KEY `userI3` (`status`),
  KEY `userI5` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(-1,'SYSTEM','crm@tip.com',NULL,NULL,NULL,NULL,NULL,10,-1,NOW(),NULL,NULL,0.00,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_roleI1` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-14  8:43:38