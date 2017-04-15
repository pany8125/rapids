/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50622
 Source Host           : localhost
 Source Database       : memory_study

 Target Server Type    : MySQL
 Target Server Version : 50622
 File Encoding         : utf-8

 Date: 04/16/2017 00:53:28 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `Admin`
-- ----------------------------
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE `Admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL COMMENT 'user account',
  `password` varchar(64) NOT NULL COMMENT 'user password',
  `name` varchar(50) NOT NULL COMMENT 'user name',
  `mobile` varchar(20) NOT NULL COMMENT 'user mobile',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `createBy` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `Admin`
-- ----------------------------
BEGIN;
INSERT INTO `Admin` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', '1', '2017-03-25 23:28:43', '1'), ('2', 'root', '63a9f0ea7bb98050796b649e85481845', 'root', '123', '2017-03-27 10:40:45', 'JXU3QkExJXU3NDA2JXU1NDU4'), ('7', 'abc', '900150983cd24fb0d6963f7d28e17f72', 'abc', '12321', '2017-03-29 15:41:30', '管理员'), ('8', 'asdf', 'cc83733cb0af8b884ff6577086b87909', 'fa', 'fa', '2017-04-04 20:53:22', '管理员'), ('14', 'd', '8277e0910d750195b448797616e091ad', 'd', 'd', '2017-04-04 20:54:11', '管理员'), ('17', 'g', 'b2f5ff47436671b6e533d8dc3614845d', 'g', 'g', '2017-04-04 21:12:48', '����&#21592;'), ('18', 'h', '2510c39011c5be704182423e3a695e91', 'hh', 'h', '2017-04-04 21:12:57', '����&#21592;'), ('19', 'i', '865c0c0b4ab0e063e5caa3387c1a8741', 'i', 'i', '2017-04-04 21:13:05', '����&#21592;'), ('20', 'rapids', 'aec182e4c2afe84b4cf05e24095e61eb', '重构记忆', '18611991876', '2017-04-12 20:50:11', '管理员'), ('21', '13011072377', '96e79218965eb72c92a549dd5a330112', 'zhang', '13011072377', '2017-04-15 17:38:14', '管理员');
COMMIT;

-- ----------------------------
--  Table structure for `AdminRole`
-- ----------------------------
DROP TABLE IF EXISTS `AdminRole`;
CREATE TABLE `AdminRole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE,INACTIVE,LOGICAL_DETELTED',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `AdminRole`
-- ----------------------------
BEGIN;
INSERT INTO `AdminRole` VALUES ('1', '系统管理员', '拥有所有页面权限', 'ACTIVE'), ('2', '学生管理人员', '学生信息管理, 学生和知识包关联管理', 'ACTIVE'), ('3', '知识包管理人员', '知识包信息管理', 'ACTIVE'), ('4', '系统用户管理人员', '系统用户的添加', 'ACTIVE');
COMMIT;

-- ----------------------------
--  Table structure for `AdminRoleMember`
-- ----------------------------
DROP TABLE IF EXISTS `AdminRoleMember`;
CREATE TABLE `AdminRoleMember` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `adminid` int(11) NOT NULL COMMENT 'admin id',
  `roleid` int(11) NOT NULL COMMENT 'adminrole id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKb4lpj1h397tjev3jsvcdh888f` (`adminid`,`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `AdminRoleMember`
-- ----------------------------
BEGIN;
INSERT INTO `AdminRoleMember` VALUES ('1', '1', '1'), ('14', '2', '2'), ('10', '2', '3'), ('12', '2', '4'), ('15', '20', '1'), ('16', '21', '1');
COMMIT;

-- ----------------------------
--  Table structure for `Grade`
-- ----------------------------
DROP TABLE IF EXISTS `Grade`;
CREATE TABLE `Grade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gradeYear` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `Grade`
-- ----------------------------
BEGIN;
INSERT INTO `Grade` VALUES ('2', 'fsfsd2', '2fdsafdsaf', '2fdsfs'), ('15', '小学精英班', '2017年春', '小学一班');
COMMIT;

-- ----------------------------
--  Table structure for `Knowledge`
-- ----------------------------
DROP TABLE IF EXISTS `Knowledge`;
CREATE TABLE `Knowledge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `descPic` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `editor` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `memo` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `memoPic` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `packId` bigint(20) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_msevp17wynwogss0mhd2kuds5` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `Knowledge`
-- ----------------------------
BEGIN;
INSERT INTO `Knowledge` VALUES ('32', null, 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/17.jpg!m348', 'abc', '重构记忆', null, 'ced', 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/16.jpg!m348', 'apple', '17', '苹果'), ('31', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc9', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA9', '15', 'aaaa Title + 9'), ('29', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc7', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA7', '15', 'aaaa Title + 7'), ('30', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc8', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA8', '15', 'aaaa Title + 8'), ('28', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc6', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA6', '15', 'aaaa Title + 6'), ('27', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc5', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA5', '15', 'aaaa Title + 5'), ('26', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc4', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA4', '15', 'aaaa Title + 4'), ('25', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc3', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA3', '15', 'aaaa Title + 3'), ('24', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc2', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA2', '15', 'aaaa Title + 2'), ('23', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc1', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA1', '15', 'aaaa Title + 1'), ('22', '2017-04-12 20:45:05', 'img/culinary/people/1.jpg', 'desc0', null, null, '没什么妙记，记住就好，死记硬背，我也没有什么好说得了', 'img/culinary/people/1.jpg', 'AAAA0', '15', 'aaaa Title + 0'), ('33', null, 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/15.jpg!m348', '啊啊啊', '重构记忆', null, '不是单身', 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/14.jpg!m348', 'pine', '17', '菠萝'), ('34', null, 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/13.jpg!m348', '放大范德萨', '重构记忆', null, '32饿', 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/12.jpg!m348', 'orange', '17', '橘子'), ('35', null, 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/11.jpg!m348', '范德萨', '重构记忆', null, '发生完', 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/10.jpg!m348', 'fruit', '17', '水果'), ('36', null, 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/1.jpg!m348', '佛挡杀佛的是否都是范德萨范德萨 发送到发送到范德萨范德萨范德萨 嗯嗯3热吻热无热无热无热无我确认群若群二无群二群无', '重构记忆', null, '发大水发大水丰盛的发到付都是范德萨范德萨发都是范德萨范德萨发到付都是范德萨范德萨发是否收到范德萨范德萨发的是否是范德萨丰盛的方式方式发是方式方式方式方式方式', 'http://picstore-1253379393.pictj.myqcloud.com/170411/216266/3D/%E6%9C%80%E7%83%AD3D/2.jpg!m348', 'coffee', '17', '咖啡'), ('37', null, 'abc', '水是生命之源', '重构记忆', null, '水母', 'bc', '水', '17', 'water'), ('38', null, 'ab', '纸', '重构记忆', null, '纸是大树变来的', 'dd', 'paper', '17', 'paper'), ('39', '2017-04-14 19:11:09', 'abc', 'abca', '重构记忆', null, 'dfs', 'fds', 'abc', '17', 'abc'), ('40', '2017-04-14 19:59:00', 'ewqewq', 'ewqewq', '重构记忆', null, 'ewqewq', 'ewqewq', '3wer', '21', '3wer'), ('41', '2017-04-15 11:27:24', 'http://localhost/static/img/upload/20170415/e6ead337410e4cf9b9073789f969a8f9.jpg', '测试一下\n测试一下测试一下\n测试一下测试一下测试一下', '重构记忆', null, '测试2下\n测试2下测试2下\n测试2下测试2下测试2下', 'http://localhost/static/img/upload/20170415/9779b71c9bd44743ad1bcef519c6c04b.jpg', 'test', '22', 'test'), ('42', '2017-04-15 11:53:18', 'http://localhost/static/img/upload/20170415/eedfe0c825b94254b78bfcfcd5c9720b.jpg', 'dsa\ndas\nd\nsa\ndsa\ndas\n', '重构记忆', null, 'dad\nasd\nsad\nas\ndsa\ndas\nda\n', 'http://localhost/static/img/upload/20170415/277fb6e74c154530900e0d41105400e2.jpg', 're', '17', 're'), ('43', '2017-04-15 15:03:08', 'http://localhost/rapids/knowledge/2017/04/15/362c6216d07cbe324f659c654c45ea24.JPEG', 'fasfsa\r\nfsfsa\r\nds\r\nf\r\nsadf', 'student:18', null, '34243ew\r\nre\r\nfds\r\nfds', null, 'test123', '-1', 'test123'), ('44', '2017-04-15 17:45:11', '', '2222\\\n222', 'zhang', null, '33333', '', '11111', '23', '11111'), ('45', '2017-04-15 17:47:22', '', 'diliweizhi', 'zhang', null, 'ziyuan', '', 'dili', '23', 'dili'), ('46', '2017-04-15 18:08:16', 'http://localhost/static/img/upload/20170415/6c832749ec1a41ef9486c17834e29b24.jpg', '地理位置影响因数', 'zhang', null, '地理位置,资源,能源,', 'http://localhost/static/img/upload/20170415/2f5d1327753948c8ab889bb0e7c39c60.jpg', '地理位置', '23', '地理位置');
COMMIT;

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
  `parentName` varchar(255) DEFAULT NULL,
  `roleid` int(11) DEFAULT NULL,
  `updateby` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `Menu`
-- ----------------------------
BEGIN;
INSERT INTO `Menu` VALUES ('2', '用户权限管理', '0', 'admin.html', '1', 'ACTIVE', '1', '2017-03-22 21:56:06', 'admin', null, null, null), ('3', '知识包管理', '0', 'package.html', '1', 'ACTIVE', '3', '2017-03-22 22:01:50', 'admin', null, null, null), ('4', '学生管理', '0', 'student.html', '1', 'ACTIVE', '2', '2017-03-22 22:01:57', 'admin', null, null, null), ('5', '学生知识包关联管理', '0', 'rela.html', '1', 'ACTIVE', '4', '2017-03-22 22:02:21', 'admin', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `Pack`
-- ----------------------------
DROP TABLE IF EXISTS `Pack`;
CREATE TABLE `Pack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `createBy` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `createTime` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `Pack`
-- ----------------------------
BEGIN;
INSERT INTO `Pack` VALUES ('19', 'w', '0', '重构记忆', '2017-04-14 17:50:04.26', '1'), ('18', '一年级数学', '1', '重构记忆', '2017-04-12 21:04:50.95', '数学'), ('17', '二年级英语', '0', '重构记忆', '2017-04-12 21:03:57.317', '二年级精英英语'), ('15', '一年级英语', '0', null, null, null), ('23', 'gaozhongdili', '0', 'zhang', '2017-04-15 17:44:26.586', '111'), ('21', '3232', '0', '重构记忆', '2017-04-14 17:50:17.785', '3223'), ('22', '二年级数学', '1', '重构记忆', '2017-04-15 09:46:22.178', '数学');
COMMIT;

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
--  Records of `RoleMenuMember`
-- ----------------------------
BEGIN;
INSERT INTO `RoleMenuMember` VALUES ('1', '2'), ('1', '3'), ('1', '4'), ('1', '5'), ('2', '4'), ('2', '5'), ('3', '3'), ('4', '2');
COMMIT;

-- ----------------------------
--  Table structure for `Sample`
-- ----------------------------
DROP TABLE IF EXISTS `Sample`;
CREATE TABLE `Sample` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `Sample`
-- ----------------------------
BEGIN;
INSERT INTO `Sample` VALUES ('1', '10', 'ttt', 'aaa12asdfasdfasdf'), ('2', '10', 'ttt', 'aaa'), ('3', '10', 'ttt', 'aaa'), ('4', '10', 'ttt', 'aaa'), ('5', '10', 'ttt', 'aaa'), ('6', '10', 'ttt', 'aaa'), ('7', '10', 'ttt', 'aaa'), ('8', '10', 'ttt', 'aaa'), ('9', '10', 'ttt', 'aaa'), ('10', '10', 'ttt', 'aaa'), ('11', '10', 'ttt', 'aaa'), ('13', '10', 'ttt', 'aaa');
COMMIT;

-- ----------------------------
--  Table structure for `StuKnowledgeQueue`
-- ----------------------------
DROP TABLE IF EXISTS `StuKnowledgeQueue`;
CREATE TABLE `StuKnowledgeQueue` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `knowledgeId` bigint(20) DEFAULT NULL,
  `lastLearnTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmghe1jowg0onijlc7lgdwvlox` (`studentId`,`knowledgeId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `StuKnowledgeRela`
-- ----------------------------
DROP TABLE IF EXISTS `StuKnowledgeRela`;
CREATE TABLE `StuKnowledgeRela` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `deleted` bit(1) NOT NULL,
  `enableTime` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `knowledgeId` bigint(20) DEFAULT NULL,
  `lastReviewTime` datetime DEFAULT NULL,
  `leanSeq` bigint(20) DEFAULT NULL,
  `memorized` bit(1) NOT NULL,
  `packId` bigint(20) DEFAULT NULL,
  `reviewCount` int(11) DEFAULT NULL,
  `reviewTime` datetime DEFAULT NULL,
  `reviewed` bit(1) NOT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  `viewCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKj79nwnmf3eq82t1tloxh32bwl` (`studentId`,`knowledgeId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `StuKnowledgeRela`
-- ----------------------------
BEGIN;
INSERT INTO `StuKnowledgeRela` VALUES ('f822cfa97a92d3c9100d75f72c2d3509', '2017-04-12 20:45:05', b'0', null, b'1', '31', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('b427f2d7409bc350260c3a76ef6db380', '2017-04-12 20:45:05', b'0', null, b'1', '30', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('db70c463b98fbdf09ab5dd72fb01a68c', '2017-04-12 20:45:05', b'0', null, b'1', '29', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('3c21b8ad8e32baa11b508aba83d43be5', '2017-04-12 20:45:05', b'0', null, b'1', '28', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('f6cd6dbad61018fb6908446d8524a743', '2017-04-12 20:45:05', b'0', null, b'1', '27', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('b458bd475025cd527dab19f01f748eb5', '2017-04-12 20:45:05', b'0', null, b'1', '26', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('a7ec380dff734fa15b9aec5a23006103', '2017-04-12 20:45:05', b'0', null, b'1', '25', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('70b4d2b0f45ef40e66c4c85ed093d482', '2017-04-12 20:45:05', b'0', null, b'1', '24', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('de2c524d6d8cbea66fc21fa8dccafbd2', '2017-04-12 20:45:05', b'0', null, b'1', '23', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('274d7b3872dcc79f3e34dadfe0c399ea', '2017-04-12 20:45:05', b'0', null, b'1', '22', null, '0', b'0', '15', '0', null, b'0', '1', '0'), ('f369981f2f2bbfcda4ca7c927b8433ca', '2017-04-12 21:22:04', b'1', '2017-04-15 14:45:43', b'1', '32', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('562ab53c5471d9d0dbfc59442290121b', '2017-04-12 21:22:04', b'1', '2017-04-15 14:45:43', b'1', '33', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('12950a8627d0fec71b9e28594c926d39', '2017-04-12 21:22:04', b'1', '2017-04-15 14:45:43', b'1', '34', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('6d6a768447baf8652b80dbb1265ba446', '2017-04-12 21:22:04', b'1', '2017-04-15 14:45:43', b'1', '35', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('de9ff6ee12ee84301f76f4b1b1c0c750', '2017-04-12 21:22:04', b'1', '2017-04-15 14:45:43', b'1', '36', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('541d38a966330e9fb841f752bf1cbd7e', '2017-04-14 19:11:09', b'1', '2017-04-15 14:45:43', b'1', '39', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('42092b3f4103c21c492db4faa47a8dd5', '2017-04-15 11:53:18', b'1', '2017-04-15 14:45:43', b'1', '42', null, '-1', b'0', '17', '0', null, b'0', '18', '0'), ('b038dd122ef3e661693019fb214422fb', '2017-04-15 15:03:08', b'0', null, b'1', '43', null, '0', b'0', '-1', '0', null, b'0', '18', '0'), ('15c881b4f92b0929880fa13d05ebea21', '2017-04-15 17:52:11', b'0', null, b'0', '44', null, '0', b'0', '23', '0', null, b'0', '18', '0'), ('d27707a06b279f38aff5c8fef56df8a8', '2017-04-15 17:52:11', b'0', null, b'0', '45', null, '0', b'0', '23', '0', null, b'0', '18', '0'), ('0aafeeb5a6c8abdb9b6c662dcb5547fa', '2017-04-15 17:52:36', b'1', null, b'1', '44', null, '-1', b'0', '23', '0', null, b'0', '25', '0'), ('b0249034597091856b370fbc3026ee30', '2017-04-15 17:52:36', b'1', null, b'1', '45', null, '-1', b'0', '23', '0', null, b'0', '25', '0'), ('4ff6c724cae73deee5084e6ee3a03c0c', '2017-04-15 18:08:16', b'0', null, b'0', '46', null, '0', b'0', '23', '0', null, b'0', '18', '0'), ('ada4d78f5640288e26626ca28f2c17d3', '2017-04-15 18:10:59', b'1', null, b'1', '46', null, '-1', b'0', '23', '0', null, b'0', '25', '0');
COMMIT;

-- ----------------------------
--  Table structure for `StuPackRela`
-- ----------------------------
DROP TABLE IF EXISTS `StuPackRela`;
CREATE TABLE `StuPackRela` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime DEFAULT NULL,
  `knowledgeNum` int(11) DEFAULT NULL,
  `lastLearnTime` datetime DEFAULT NULL,
  `learnedNum` int(11) DEFAULT NULL,
  `packId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  `packName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKaa6d68q4gmlkwlgfnhqt5dpvj` (`studentId`,`packId`),
  KEY `IDXmq66xvoy1eauisglnd3i9v809` (`studentId`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `StuPackRela`
-- ----------------------------
BEGIN;
INSERT INTO `StuPackRela` VALUES ('3', '2017-04-12 20:45:05', '10', null, '0', '15', '0', '1', '一年级英语'), ('4', '2017-04-12 21:22:04', '7', '2017-04-15 14:46:28', '8', '17', null, '18', '二年级英语'), ('5', '2017-04-15 17:52:11', '3', null, '0', '23', null, '18', 'gaozhongdili'), ('6', '2017-04-15 17:52:36', '3', '2017-04-15 18:11:02', '4', '23', null, '25', 'gaozhongdili');
COMMIT;

-- ----------------------------
--  Table structure for `Student`
-- ----------------------------
DROP TABLE IF EXISTS `Student`;
CREATE TABLE `Student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `editor` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gradeId` bigint(20) DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `gradeName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fs9lu2k3vcl3h3kcj7d2vxxdk` (`mobile`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `Student`
-- ----------------------------
BEGIN;
INSERT INTO `Student` VALUES ('1', '2017-03-02 17:32:17', 'TESTER', '2017-03-02 17:32:17', 'David', 'aaa', null, null, null, null, null, null), ('13', '2017-04-08 14:35:02', '管理员', null, 'a', '1', '1', '23', 'fds@fdas.com', '2', '1', null), ('12', '2017-04-08 14:35:05', '管理员', null, 'abc', '1dwa', '1', '1', 'asf@fas.com', '2', '11231', null), ('14', '2017-04-08 14:39:24', '管理员', null, '1', '1', '0', '1', '111@fds.com', '2', 'qeq', null), ('15', '2017-04-08 14:45:44', '管理员', null, '1', 'c4ca4238a0b923820dcc509a6f75849b', '0', '1', 'd@fsd.com', '2', '2', null), ('16', '2017-04-08 15:22:59', '管理员', null, '1', 'c4ca4238a0b923820dcc509a6f75849b', '1', '1', 'fds@fdsa.com', '1', '333', null), ('17', '2017-04-09 20:01:43', '管理员', null, 'aads', '41ea31d329ff1f34dc8f63bb07cd83b7', '1', '32', 'fds@fdjs.com', '2', '12312', null), ('18', '2017-04-12 20:53:11', '重构记忆', null, '333', '96e79218965eb72c92a549dd5a330112', '1', '18', 'abc@dfs.com', '15', '18611991876', null), ('19', '2017-04-14 19:17:22', '重构记忆', null, '321', 'caf1a3dfb505ffed0d024130f58c5cfa', '0', '1', '', '2', '1231', null), ('20', '2017-04-14 19:17:33', '重构记忆', null, '12', '950a4152c2b4aa3ad78bdd6b366cc179', '0', '1', '', '2', '321', null), ('21', '2017-04-14 19:17:42', '重构记忆', null, '34', '17e62166fc8586dfa4d1bc0e1742c08b', '0', '43', '', '2', '43', null), ('22', '2017-04-14 19:17:55', '重构记忆', null, '3', 'eccbc87e4b5ce2fe28308fd9f2a7baf3', '0', '1', '', '2', '54', null), ('23', '2017-04-14 19:18:07', '重构记忆', null, '4', '6c8349cc7260ae62e3b1396831a8398f', '0', '32', '', '2', '132', null), ('24', '2017-04-14 19:18:18', '重构记忆', null, '23', '3416a75f4cea9109507cacd8e2f2aefc', '0', '2', '', '2', '324', null), ('25', '2017-04-15 17:46:39', 'zhang', null, 'zhang', '96e79218965eb72c92a549dd5a330112', '0', '1', '', '15', '18911009132', null);
COMMIT;

-- ----------------------------
--  Table structure for `StudyLog`
-- ----------------------------
DROP TABLE IF EXISTS `StudyLog`;
CREATE TABLE `StudyLog` (
  `id` varchar(255) COLLATE utf8_bin NOT NULL,
  `knowledgeId` bigint(20) DEFAULT NULL,
  `learnTimes` int(11) DEFAULT NULL,
  `logTime` datetime DEFAULT NULL,
  `packId` bigint(20) DEFAULT NULL,
  `studentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `StudyLog`
-- ----------------------------
BEGIN;
INSERT INTO `StudyLog` VALUES ('78e6fab02fc1ae8dd081f16254d4b3c1', '33', '0', '2017-04-15 14:46:10', '17', '18'), ('1fd75349544eb8d0726e01f9467add66', '34', '0', '2017-04-15 14:46:13', '17', '18'), ('bfffed38d6817fcb4bba6d4caf70b7fd', '35', '0', '2017-04-15 14:46:15', '17', '18'), ('484e9a687b8017271d537772b9ae3ac8', '36', '0', '2017-04-15 14:46:18', '17', '18'), ('f47aedc1af62c02b515b5fc886006e41', '32', '0', '2017-04-15 14:46:20', '17', '18'), ('acbfb51b53e7f32e7e264299b0644102', '39', '0', '2017-04-15 14:46:22', '17', '18'), ('3dc8b5f77a7a5c906c006423812cc7da', '42', '0', '2017-04-15 14:46:26', '17', '18'), ('5f7830ffa66f8fc1a4b743e168f1efd8', '42', '0', '2017-04-15 14:46:28', '17', '18'), ('1eccdf8d037281e1d23932629c21b14e', '44', '0', '2017-04-15 18:05:11', '23', '25'), ('b8b8b486f3b8c9dc4297b88e6369c865', '45', '0', '2017-04-15 18:05:31', '23', '25'), ('9ce8f21c07201dd1900ca2a5b1422381', '46', '0', '2017-04-15 18:10:59', '23', '25'), ('982ee8d423b67cc94a85f75c5b6623ad', '46', '0', '2017-04-15 18:11:02', '23', '25');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
