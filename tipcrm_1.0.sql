CREATE DATABASE IF NOT EXISTS `TIP_CRM` /*!40100 DEFAULT CHARACTER SET utf8
  COLLATE utf8_bin */;
USE `TIP_CRM`;
-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: www.potafish.com    Database: TIP_CRM
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `approval_request`
--

DROP TABLE IF EXISTS `approval_request`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `approval_request` (
  `id`               INT(11) NOT NULL              AUTO_INCREMENT,
  `approval_type`    INT(11) NOT NULL,
  `approval_id`      INT(11) NOT NULL,
  `sequence`         INT(11) NOT NULL,
  `review_id`        INT(11) NOT NULL,
  `review_time`      DATETIME(3)                   DEFAULT NULL,
  `review_status_id` INT(11)                       DEFAULT NULL,
  `review_note`      VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_approval_requestI1` (`approval_id`),
  KEY `customer_approval_requestI2` (`review_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `approval_request`
--

LOCK TABLES `approval_request` WRITE;
/*!40000 ALTER TABLE `approval_request`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `approval_request`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `communication`
--

DROP TABLE IF EXISTS `communication`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `communication` (
  `id`                    INT(11)     NOT NULL          AUTO_INCREMENT,
  `customer_id`           INT(11)     NOT NULL,
  `customer_contact_id`   INT(11)     NOT NULL,
  `follow_user_id`        INT(11)     NOT NULL,
  `communicate_time`      DATETIME(3) NOT NULL,
  `note`                  VARCHAR(255) COLLATE utf8_bin DEFAULT NULL,
  `next_communicate_time` DATETIME(3)                   DEFAULT NULL,
  `on_side`               TINYINT(1)  NOT NULL
  COMMENT '是否上门服务',
  `entry_id`              INT(11)     NOT NULL,
  `entry_time`            DATETIME    NOT NULL,
  `update_id`             INT(11)                       DEFAULT NULL,
  `update_time`           DATETIME(3)                   DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `communicationI1` (`customer_id`),
  KEY `communicationI3` (`follow_user_id`),
  KEY `communicationI2` (`customer_contact_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `communication`
--

LOCK TABLES `communication` WRITE;
/*!40000 ALTER TABLE `communication`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `communication`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id`    INT(11)                       NOT NULL,
  `key`   VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `value` VARCHAR(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `configuration` (`key`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration`
  DISABLE KEYS */;
INSERT INTO `configuration` VALUES
  (1, 'REGISTABLE', 'TRUE');
/*!40000 ALTER TABLE `configuration`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `id`                   INT(11)        NOT NULL AUTO_INCREMENT,
  `customer_id`          INT(11)        NOT NULL,
  `follow_user_id`       INT(11)        NOT NULL,
  `follow_department_id` INT(11)        NOT NULL,
  `amount`               DECIMAL(10, 2) NOT NULL,
  `note`                 VARCHAR(255)            DEFAULT NULL,
  `sign_time`            DATETIME(3)    NOT NULL,
  `review_id`            INT(11)        NOT NULL,
  `review_status_id`     INT(11)        NOT NULL,
  `review_time`          DATETIME(3)    NOT NULL,
  `review_note`          VARCHAR(255)            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `contractI1` (`customer_id`),
  KEY `contractI2` (`follow_user_id`),
  KEY `contractI3` (`follow_department_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `contract`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract_production`
--

DROP TABLE IF EXISTS `contract_production`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract_production` (
  `id`            INT(11)        NOT NULL AUTO_INCREMENT,
  `contract_id`   INT(11)        NOT NULL,
  `production_id` INT(11)        NOT NULL,
  `amount`        DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contract_productionI1` (`contract_id`, `production_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract_production`
--

LOCK TABLES `contract_production` WRITE;
/*!40000 ALTER TABLE `contract_production`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_production`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id`                    INT(11)                       NOT NULL AUTO_INCREMENT,
  `name`                  VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `status_id`             INT(11)                       NOT NULL,
  `address`               VARCHAR(255) COLLATE utf8_bin          DEFAULT NULL,
  `follow_user_id`        INT(11)                                DEFAULT NULL,
  `follow_department_id`  INT(11)                                DEFAULT NULL,
  `last_communication_id` INT(11)                                DEFAULT NULL,
  `note`                  VARCHAR(255) COLLATE utf8_bin          DEFAULT NULL,
  `entry_id`              INT(11)                       NOT NULL,
  `entry_time`            DATETIME(3)                   NOT NULL,
  `update_id`             INT(11)                                DEFAULT NULL,
  `update_time`           DATETIME(3)                            DEFAULT NULL,
  `delete_id`             INT(11)                                DEFAULT NULL,
  `delete_time`           DATETIME(3)                            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customerI1` (`name`),
  KEY `customerI2` (`follow_user_id`),
  KEY `customerI3` (`follow_department_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `customer`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_approval`
--

DROP TABLE IF EXISTS `customer_approval`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_approval` (
  `id`                   INT(11)                       NOT NULL AUTO_INCREMENT,
  `opt_type_id`          INT(11)                       NOT NULL,
  `review_type_id`       INT(11)                       NOT NULL,
  `customer_id`          INT(11)                                DEFAULT NULL,
  `name`                 VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `status_id`            INT(11)                       NOT NULL,
  `address`              VARCHAR(1000) COLLATE utf8_bin         DEFAULT NULL,
  `follow_user_id`       INT(11)                                DEFAULT NULL,
  `follow_department_id` BIGINT(20)                             DEFAULT NULL,
  `note`                 VARCHAR(255) COLLATE utf8_bin          DEFAULT NULL,
  `entry_id`             INT(11)                       NOT NULL,
  `entry_time`           DATETIME(3)                   NOT NULL,
  `review_status_id`     INT(11)                       NOT NULL,
  `final_approval_time`  DATETIME(3)                            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_approvalI1` (`name`),
  KEY `customer_approvalI2` (`follow_user_id`),
  KEY `customer_approvalI3` (`follow_department_id`),
  KEY `customer_approvalI4` (`customer_id`),
  KEY `customer_approvalI5` (`opt_type_id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_approval`
--

LOCK TABLES `customer_approval` WRITE;
/*!40000 ALTER TABLE `customer_approval`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_approval`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_contact`
--

DROP TABLE IF EXISTS `customer_contact`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_contact` (
  `id`          INT(11)                      NOT NULL AUTO_INCREMENT,
  `customer_id` INT(11)                               DEFAULT NULL,
  `approval_id` INT(11)                               DEFAULT NULL,
  `name`        VARCHAR(45) COLLATE utf8_bin NOT NULL,
  `phone_no`    VARCHAR(20) COLLATE utf8_bin NOT NULL,
  `email`       VARCHAR(50) COLLATE utf8_bin          DEFAULT NULL,
  `job`         VARCHAR(100) COLLATE utf8_bin         DEFAULT NULL,
  `entry_id`    INT(11)                      NOT NULL,
  `entry_time`  DATETIME(3)                  NOT NULL,
  `note`        VARCHAR(255) COLLATE utf8_bin         DEFAULT NULL,
  `active`      TINYINT(1)                   NOT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_contactI1` (`customer_id`),
  KEY `customer_contactI2` (`name`, `phone_no`),
  KEY `customer_contactI3` (`approval_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_contact`
--

LOCK TABLES `customer_contact` WRITE;
/*!40000 ALTER TABLE `customer_contact`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_contact`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id`          INT(11)                      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `parent_id`   INT(11)                               DEFAULT NULL,
  `manager_id`  INT(11)                               DEFAULT NULL,
  `entry_id`    INT(11)                      NOT NULL,
  `entry_time`  DATETIME(3)                  NOT NULL,
  `update_id`   INT(11)                               DEFAULT NULL,
  `update_time` DATETIME(3)                           DEFAULT NULL,
  `delete_id`   INT(11)                               DEFAULT NULL,
  `delete_time` DATETIME(3)                           DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `departmentI1` (`name`),
  KEY `departmentI2` (`manager_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `department`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goal`
--

DROP TABLE IF EXISTS `goal`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goal` (
  `id`          INT(11)        NOT NULL AUTO_INCREMENT,
  `goal`        DECIMAL(10, 2) NOT NULL,
  `goal_time`   DATETIME(3)    NOT NULL,
  `entity_type` INT(11)        NOT NULL,
  `entity_id`   INT(10)        NOT NULL,
  `entry_id`    INT(11)        NOT NULL,
  `entry_time`  DATETIME(3)    NOT NULL,
  `update_id`   INT(11)                 DEFAULT NULL,
  `update_time` DATETIME(3)             DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `goalI1` (`entity_type`, `entity_id`, `goal_time`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goal`
--

LOCK TABLES `goal` WRITE;
/*!40000 ALTER TABLE `goal`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `goal`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `level`
--

DROP TABLE IF EXISTS `level`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `level` (
  `id`                      INT(11)                      NOT NULL AUTO_INCREMENT,
  `name`                    VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `display_name`            VARCHAR(50) COLLATE utf8_bin          DEFAULT NULL,
  `default_payment_percent` DECIMAL(4, 2)                NOT NULL,
  `entry_id`                INT(11)                      NOT NULL,
  `entry_time`              DATETIME(3)                  NOT NULL,
  `update_id`               INT(11)                               DEFAULT NULL,
  `update_time`             DATETIME(3)                           DEFAULT NULL,
  `delete_id`               INT(11)                               DEFAULT NULL,
  `delete_time`             DATETIME(3)                           DEFAULT NULL,
  `deletable`               TINYINT(1)                            DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `levelI1` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `level`
--

LOCK TABLES `level` WRITE;
/*!40000 ALTER TABLE `level`
  DISABLE KEYS */;
INSERT INTO `level` VALUES
  (1, 'NEW_USER', '新员工', 0.00, -1, NOW(), NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `level`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list_box`
--

DROP TABLE IF EXISTS `list_box`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `list_box` (
  `id`            INT(11)                       NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `name`          VARCHAR(100) COLLATE utf8_bin NOT NULL,
  `display_name`  VARCHAR(100) COLLATE utf8_bin          DEFAULT NULL,
  `sequence`      INT(11)                       NOT NULL,
  `entry_id`      INT(11)                       NOT NULL,
  `entry_time`    DATETIME(3)                   NOT NULL,
  `update_id`     INT(11)                                DEFAULT NULL,
  `update_time`   DATETIME(3)                            DEFAULT NULL,
  `delete_id`     INT(11)                                DEFAULT NULL,
  `delete_time`   DATETIME(3)                            DEFAULT NULL,
  `editable`      TINYINT(1)                             DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `list_boxI1` (`category_name`, `name`),
  UNIQUE KEY `list_boxI2` (`category_name`, `sequence`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_box`
--

LOCK TABLES `list_box` WRITE;
/*!40000 ALTER TABLE `list_box`
  DISABLE KEYS */;
INSERT INTO `list_box` VALUES
  (1, 'CUSTOMER_STATUS', 'NEW_CUSTOMER', '新客户', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (2, 'CUSTOMER_STATUS', 'INTENTIONAL_CUSTOMER', '意向客户', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (3, 'CUSTOMER_STATUS', 'SIGNING_CUSTOMER', '签约客户', 3, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (4, 'CUSTOMER_STATUS', 'EXPIRED_CUSTOMER', '过期客户', 4, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (5, 'APPROVAL_STATUS', 'PENDING', '待审批', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (6, 'APPROVAL_STATUS', 'REJECTED', '已驳回', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (7, 'APPROVAL_STATUS', 'APPROVED', '已通过', 3, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (8, 'GOAL_TYPE', 'USER', '员工目标', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (9, 'GOAL_TYPE', 'DEPARTMENT', '部门目标', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (10, 'USER_STATUS', 'ACTIVE', '正常', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (11, 'USER_STATUS', 'FROZEN', '冻结', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (12, 'NOTIFICATION_TYPE', 'SYSTEM_NOTIFICATION', '系统通知', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (13, 'NOTIFICATION_TYPE', 'USER_NOTIFICATION', '用户通知', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (14, 'NOTIFICATION_READ_STATUS', 'READ', '已读', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (15, 'NOTIFICATION_READ_STATUS', 'UNREAD', '未读', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (16, 'OPERATION_TYPE', 'ADD', '新增', 1, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (17, 'OPERATION_TYPE', 'UPDATE', '修改', 2, -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (18, 'OPERATION_TYPE', 'REMOVE', '删除', 3, -1, NOW(), NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `list_box`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `id`            INT(11)                      NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `display_name`  VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `sequence`      INT(11)                      NULL,
  `icon`          VARCHAR(100) COLLATE utf8_bin         DEFAULT NULL,
  `parent_id`     INT(11)                               DEFAULT NULL,
  `permission_id` INT(11)                               DEFAULT NULL,
  `url`           VARCHAR(255) CHARACTER SET utf8       DEFAULT NULL,
  `active`        TINYINT                      NULL     DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `menuI1` (`name`),
  KEY `menuI2` (`permission_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu`
  DISABLE KEYS */;
INSERT INTO `menu` VALUES
  (1, 'CUSTOMER_MANAGEMENT', '客户管理', 1, NULL, NULL, NULL, NULL, 1),
  (2, 'MY_CUSTOMER', '我的客户', 1, NULL, 1, NULL, NULL, 1),
  (3, 'DEPARTMENT_OPEN_SEA', '部门公海', 2, NULL, 1, 1, NULL, 1),
  (4, 'COMPANY_OPEN_SEA', '公司公海', 3, NULL, 1, 2, NULL, 1),
  (5, 'PRODUCTION_MANAGEMENT', '产品管理', 2, NULL, NULL, NULL, NULL, 1),
  (6, 'CONTRACT_MANAGEMENT', '合同管理', 3, NULL, NULL, NULL, NULL, 1),
  (7, 'MY_CONTRACT', '我的合同', 1, NULL, 6, NULL, NULL, 1),
  (8, 'DEPARTMENT_CONTRACT', '部门合同', 2, NULL, 6, 28, NULL, 1),
  (9, 'COMPANY_CONTRACT', '公司合同', 3, NULL, 6, 29, NULL, 1),
  (10, 'APPROVAL_MANAGEMENT', '审批管理', 4, NULL, NULL, NULL, NULL, 1),
  (11, 'MY_CUSTOMER_REQUEST', '我的客户申请', 1, NULL, 10, NULL, NULL, 1),
  (12, 'MY_CONTRACT_REQUEST', '我的合同申请', 2, NULL, 10, NULL, NULL, 1),
  (13, 'CUSTOMER_APPROVAL', '客户申请审批', 3, NULL, 10, 16, NULL, 1),
  (14, 'CONTRACT_APPROVAL', '合同申请审批', 4, NULL, 10, 30, NULL, 1),
  (15, 'GOAL_MANAGEMENT', '目标管理', 5, NULL, NULL, NULL, NULL, 1),
  (16, 'MY_GOAL', '我的目标', 1, NULL, 15, NULL, NULL, 1),
  (17, 'USER_GOAL', '员工目标', 2, NULL, 15, 38, NULL, 1),
  (18, 'DEPARTMENT_GOAL', '部门目标', 3, NULL, 15, 36, NULL, 1),
  (19, 'USER_MANAGEMENT', '员工管理', 6, NULL, NULL, NULL, NULL, 1),
  (20, 'DEPARTMENT_USER', '部门员工', 1, NULL, 19, 17, NULL, 1),
  (21, 'COMPANY_USER', '公司员工', 2, NULL, 19, 18, NULL, 1),
  (22, 'DEPARTMENT_MANAGEMENT', '部门管理', 7, NULL, NULL, NULL, NULL, 1),
  (23, 'ROLE_AND_PERMISSION', '角色与权限', 8, NULL, NULL, NULL, NULL, 1),
  (24, 'MY_ROLE_AND_PERMISSION', '我的', 1, NULL, 23, NULL, NULL, 1),
  (25, 'ROLES', '角色', 2, NULL, 23, NULL, NULL, 1),
  (26, 'PERSONAL_CENTER', '个人中心', 9, NULL, NULL, NULL, NULL, 1),
  (27, 'NOTIFICATION_CENTER', '通知中心', 10, NULL, NULL, NULL, NULL, 1),
  (28, 'SYSTEM_NOTIFICATION', '系统通知', 1, NULL, 27, NULL, NULL, 1),
  (29, 'USER_NOTIFICATION', '站内信', 2, NULL, 27, NULL, NULL, 0),
  (30, 'SETTING', '设置', 11, NULL, NULL, NULL, NULL, 1),
  (31, 'MY_SETTING', '个人设置', 1, NULL, 30, NULL, NULL, 1),
  (32, 'SYSTEM_SETTING', '系统配置', 2, NULL, 30, 34, NULL, 1);
/*!40000 ALTER TABLE `menu`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notification` (
  `id`          INT(11)     NOT NULL AUTO_INCREMENT,
  `to_user_id`  INT(11)     NOT NULL,
  `subject`     VARCHAR(100)         DEFAULT NULL,
  `content`     TEXT,
  `type_id`     INT(11)     NOT NULL,
  `read_status` INT(11)     NOT NULL,
  `entry_id`    INT(11)     NOT NULL,
  `entry_time`  DATETIME(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `notificationI1` (`to_user_id`),
  KEY `notificationI2` (`type_id`),
  KEY `notificationI3` (`read_status`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `notification`
  ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id`            INT(11)                      NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `value`         VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `display_name`  VARCHAR(50) COLLATE utf8_bin          DEFAULT NULL,
  `note`          VARCHAR(255) COLLATE utf8_bin         DEFAULT NULL,
  `dependence_id` INT(11)                               DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permissionI2` (`value`),
  KEY `permissionI1` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission`
  DISABLE KEYS */;
INSERT INTO `permission` VALUES
  (1, 'CUSTOMER_DEPARTMENT_VIEW', 'customer:department:view', '查看部门公海', NULL, NULL),
  (2, 'CUSTOMER_COMPANY_VIEW', 'customer:company:view', '查看公司公海', NULL, NULL),
  (3, 'CUSTOMER_ADD', 'customer:add', '添加客户', NULL, NULL),
  (4, 'CUSTOMER_MY_UPDATE', 'customer:my:update', '修改我的客户', NULL, NULL),
  (5, 'CUSTOMER_DEPARTMENT_UPDATE', 'customer:department:update', '修改部门客户', NULL, 1),
  (6, 'CUSTOMER_COMPANY_UPDATE', 'customer:company:update', '修改公司客户', NULL, 2),
  (7, 'CUSTOMER_COMMUNICATION_ADD', 'customer:communication:add', '添加拜访记录', NULL, NULL),
  (8, 'CUSTOMER_STATUS_CHANGE', 'customer:status:change', '状态转换', '用于客户状态之间的转换（如新客户转换到意向客户）', NULL),
  (9, 'CUSTOMER_MY_DELETE', 'customer:my:delete', '删除我的客户', NULL, NULL),
  (10, 'CUSTOMER_DEPARTMENT_DELETE', 'customer:department:delete', '删除部门客户', NULL, 1),
  (11, 'CUSTOMER_COMPANY_DELETE', 'customer:company:delete', '删除公司客户', NULL, 2),
  (12, 'CUSTOMER_DEPARTMENT_TRANSFER_TO_USER', 'customer:department:transfer_to_user', '转入到员工', '从部门公海中将客户转移至部门内部员工', 1),
  (13, 'CUSTOMER_MY_TRANSFER_TO_USER', 'customer:my:transfer_to_user', '转出到员工', '将其客户转移至部门内部员工', NULL),
  (14, 'CUSTOMER_COMPANY_TRANSFER_TO_DEPARTMENT', 'customer:company:transfer_to_department', '转入到部门', '将公司公海中的客户转移至员工所属的部门', 2),
  (15, 'CUSTOMER_MY_TRANSFER_TO_DEPARTMENT', 'customer:my:transfer_to_department', '转出到部门', '将其客户转移至部门公海', NULL),
  (16, 'CUSTOMER_APPROVAL', 'customer:approval', '审批客户', NULL, NULL),
  (17, 'USER_DEPARTMENT_VIEW', 'user:department:view', '查看部门员工', NULL, NULL),
  (18, 'USER_COMPANY_VIEW', 'user:company:view', '查看公司员工', NULL, NULL),
  (19, 'USER_ADD', 'user:add', '添加员工', NULL, NULL),
  (20, 'USER_UPDATE', 'user:update', '修改员工', NULL, NULL),
  (21, 'USER_DELETE', 'user:delete', '员工离职', NULL, NULL),
  (22, 'ROLE_ADD', 'role:add', '添加角色', NULL, NULL),
  (23, 'ROLE_UPDATE', 'role:update', '修改角色', NULL, NULL),
  (24, 'ROLE_DELETE', 'role:delete', '删除角色', NULL, NULL),
  (25, 'DEPARTMENT_ADD', 'department:add', '添加部门', NULL, NULL),
  (26, 'DEPARTMENT_UPDATE', 'department:update', '修改部门', NULL, NULL),
  (27, 'DEPARTMENT_DELETE', 'department:delete', '删除部门', NULL, NULL),
  (28, 'CONTRACT_DEPARTMENT_VIEW', 'contract:department:view', '查看部门合同', NULL, NULL),
  (29, 'CONTRACT_COMPANY_VIEW', 'contract:company:view', '查看公司合同', NULL, NULL),
  (30, 'CONTRACT_APPROVAL', 'contract:approval', '审批合同', NULL, NULL),
  (31, 'PRODUCTION_ADD', 'production:add', '添加产品', NULL, NULL),
  (32, 'PRODUCTION_UPDATE', 'production:update', '修改产品', NULL, NULL),
  (33, 'PRODUCTION_DELETE', 'production:delete', '删除产品', NULL, NULL),
  (34, 'SYSTEM_CONFIGURE', 'system:configure', '系统配置', NULL, NULL),
  (35, 'DEPARTMENT_GOAL_VIEW', 'department_goal:view', '查看部门目标', NULL, NULL),
  (36, 'DEPARTMENT_GOAL_ASSIGN', 'department_goal:assign', '分配部门目标', NULL, NULL),
  (37, 'USER_GOAL_VIEW', 'user_goal:view', '查看员工目标', NULL, NULL),
  (38, 'USER_GOAL_ASSIGN', 'user_goal:assign', '分配员工目标', NULL, 37),
  (39, 'ROLE_ASSIGN', 'role:assign', '分配角色', NULL, NULL);

/*!40000 ALTER TABLE `permission`
  ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `TIP_CRM`.`menu_permission` (
  `id`            INT(11) NOT NULL AUTO_INCREMENT,
  `menu_id`       INT(11) NOT NULL,
  `permission_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `permission_menuI1` (`menu_id` ASC, `permission_id` ASC)
);

INSERT INTO `TIP_CRM`.`menu_permission` (`menu_id`, `permission_id`) VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4),
  (2, 7),
  (2, 8),
  (2, 9),
  (2, 13),
  (2, 15),
  (3, 1),
  (3, 3),
  (3, 5),
  (3, 10),
  (3, 12),
  (4, 2),
  (4, 3),
  (4, 6),
  (4, 11),
  (4, 14),
  (5, 31),
  (5, 32),
  (5, 33),
  (8, 28),
  (9, 29),
  (13, 16),
  (14, 30),
  (17, 37),
  (17, 38),
  (18, 35),
  (18, 36),
  (20, 17),
  (20, 19),
  (20, 20),
  (20, 21),
  (20, 39),
  (21, 18),
  (21, 19),
  (21, 39),
  (22, 25),
  (22, 26),
  (22, 27),
  (25, 22),
  (25, 23),
  (25, 24),
  (32, 34);

--
-- Table structure for table `production`
--

DROP TABLE IF EXISTS `production`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `production` (
  `id`          INT(11)     NOT NULL,
  `name`        VARCHAR(50) NOT NULL,
  `note`        VARCHAR(255) DEFAULT NULL,
  `entry_id`    INT(11)     NOT NULL,
  `entry_time`  DATETIME(3) NOT NULL,
  `update_id`   INT(11)      DEFAULT NULL,
  `update_time` DATETIME(3)  DEFAULT NULL,
  `delete_id`   INT(11)      DEFAULT NULL,
  `delete_time` DATETIME(3)  DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `productionI1` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production`
--

LOCK TABLES `production` WRITE;
/*!40000 ALTER TABLE `production`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `production`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id`           INT(11)                      NOT NULL AUTO_INCREMENT,
  `name`         VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `display_name` VARCHAR(50) COLLATE utf8_bin          DEFAULT NULL,
  `entry_id`     INT(11)                      NOT NULL,
  `entry_time`   DATETIME(3)                  NOT NULL,
  `update_id`    INT(11)                               DEFAULT NULL,
  `update_time`  DATETIME(3)                           DEFAULT NULL,
  `delete_id`    INT(11)                               DEFAULT NULL,
  `delete_time`  DATETIME(3)                           DEFAULT NULL,
  `editable`     TINYINT(1)                            DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `roleI1` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role`
  DISABLE KEYS */;
INSERT INTO `role` VALUES
  (1, 'GENERAL_MANAGER', '经理', -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (2, 'NORMAL', '员工', -1, NOW(), NULL, NULL, NULL, NULL, 0),
  (3, 'DEPARTMENT_MANAGER', '部门经理', -1, NOW(), NULL, NULL, NULL, NULL, 0);
/*!40000 ALTER TABLE `role`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission` (
  `id`            INT(11)     NOT NULL AUTO_INCREMENT,
  `role_id`       INT(11)     NOT NULL,
  `permission_id` INT(11)     NOT NULL,
  `deletable`     TINYINT(1)  NOT NULL,
  `entry_id`      INT(11)     NOT NULL,
  `entry_time`    DATETIME(3) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permissionI1` (`role_id`, `permission_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission`
  DISABLE KEYS */;
INSERT INTO `role_permission` VALUES
  (1, 1, 1, 0, -1, NOW()),
  (2, 1, 2, 0, -1, NOW()),
  (3, 1, 3, 0, -1, NOW()),
  (4, 1, 4, 0, -1, NOW()),
  (5, 1, 5, 0, -1, NOW()),
  (6, 1, 6, 0, -1, NOW()),
  (7, 1, 7, 0, -1, NOW()),
  (8, 1, 8, 0, -1, NOW()),
  (9, 1, 9, 0, -1, NOW()),
  (10, 1, 10, 0, -1, NOW()),
  (13, 2, 4, 1, -1, NOW());
/*!40000 ALTER TABLE `role_permission`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security`
--

DROP TABLE IF EXISTS `security`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `security` (
  `user_id`  INT(11)      NOT NULL,
  `password` VARCHAR(50)  NOT NULL,
  `salt`     VARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security`
--

LOCK TABLES `security` WRITE;
/*!40000 ALTER TABLE `security`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `security`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id`              INT(11)                      NOT NULL AUTO_INCREMENT,
  `username`        VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `email`           VARCHAR(50) COLLATE utf8_bin NOT NULL,
  `id_card`         VARCHAR(20) COLLATE utf8_bin          DEFAULT NULL,
  `birthday`        DATETIME(3)                           DEFAULT NULL,
  `phone_no`        VARCHAR(20) COLLATE utf8_bin          DEFAULT NULL,
  `avatar`          VARCHAR(255) CHARACTER SET utf8       DEFAULT NULL,
  `motto`           VARCHAR(1000) COLLATE utf8_bin        DEFAULT NULL,
  `status`          INT(11)                      NOT NULL,
  `hire_id`         INT(11)                      NOT NULL,
  `hire_time`       DATETIME(3)                  NOT NULL,
  `department_id`   INT(11)                               DEFAULT NULL,
  `level_id`        INT(11)                               DEFAULT NULL,
  `payment_percent` DECIMAL(4, 2)                NOT NULL,
  `dismiss_id`      INT(11)                               DEFAULT NULL,
  `dismiss_date`    DATETIME(3)                           DEFAULT NULL,
  `dismiss_reason`  VARCHAR(255) COLLATE utf8_bin         DEFAULT NULL,
  `update_id`       INT(11)                               DEFAULT NULL,
  `update_time`     DATETIME(3)                           DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userI2` (`email`),
  UNIQUE KEY `userI4` (`phone_no`),
  KEY `userI1` (`username`),
  KEY `userI3` (`status`),
  KEY `userI5` (`department_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user`
  DISABLE KEYS */;
INSERT INTO `user` VALUES
  (-1, 'SYSTEM', '', NULL, NULL, NULL, NULL, NULL, 10, -1, NOW(), NULL, NULL, 0.00, NULL, NULL, NULL, NULL,
   NULL);
/*!40000 ALTER TABLE `user`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `id`      BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11)    NOT NULL,
  `role_id` INT(11)    NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_roleI1` (`user_id`, `role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role`
  DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-02-19 21:22:13
