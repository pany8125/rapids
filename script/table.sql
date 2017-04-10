SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Admin`
-- ----------------------------
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE `Admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL COMMENT 'user account',
   password varchar(64) NOT NULL COMMENT 'user password',
   name   varchar(50) NOT NULL COMMENT 'user name',
   mobile varchar(20) NOT NULL COMMENT 'user mobile',
   createTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   createBy varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `AdminRole`
-- ----------------------------
DROP TABLE IF EXISTS `AdminRole`;
CREATE TABLE `AdminRole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` varchar(20) NOT NULL COMMENT 'ACTIVE,INACTIVE,LOGICAL_DETELTED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `AdminRoleMember`
-- ----------------------------
DROP TABLE IF EXISTS `AdminRoleMember`;
CREATE TABLE `AdminRoleMember` (
  `adminid` int(11) NOT NULL COMMENT 'admin id',
  `roleid` int(11) NOT NULL COMMENT 'adminrole id',
  PRIMARY KEY (`adminid`,`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `RoleMenuMember`
-- ----------------------------
DROP TABLE IF EXISTS `RoleMenuMember`;
CREATE TABLE `RoleMenuMember` (
  `roleid` int(11) NOT NULL COMMENT 'adminrole id',
  `menuid` int(11) NOT NULL COMMENT 'menu id',
  PRIMARY KEY (`roleid`,`menuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
--  Table structure for `Menu`
-- ----------------------------
DROP TABLE IF EXISTS `Menu`;
CREATE TABLE `Menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) NOT NULL COMMENT 'menu名称',
  `parentId` int(11) NOT NULL DEFAULT '0',
  `url` varchar(100) DEFAULT NULL COMMENT '跳转url',
  `leaf` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是叶节点',
  `status` varchar(10) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE OR INACTIVE OR LOCALDELETE',
  `orderNum` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createdBy` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



