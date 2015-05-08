/*
Navicat MySQL Data Transfer

Source Server         : teamtank
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : scorm

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2014-08-21 00:02:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for answerinfo
-- ----------------------------
DROP TABLE IF EXISTS `answerinfo`;
CREATE TABLE `answerinfo` (
  `AnswerId` int(20) NOT NULL AUTO_INCREMENT,
  `ACourseTitle` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ASendName` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `AnswerContent` text COLLATE utf8_unicode_ci NOT NULL,
  `QuestionId` int(20) NOT NULL,
  `ASendTime` datetime NOT NULL,
  PRIMARY KEY (`AnswerId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of answerinfo
-- ----------------------------

-- ----------------------------
-- Table structure for applicationdata
-- ----------------------------
DROP TABLE IF EXISTS `applicationdata`;
CREATE TABLE `applicationdata` (
  `dataName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `numberValue` int(20) DEFAULT NULL,
  PRIMARY KEY (`dataName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of applicationdata
-- ----------------------------
INSERT INTO `applicationdata` VALUES ('nextCourseID', '1000');

-- ----------------------------
-- Table structure for courseinfo
-- ----------------------------
DROP TABLE IF EXISTS `courseinfo`;
CREATE TABLE `courseinfo` (
  `CourseID` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `CourseTitle` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL,
  `RegNum` int(4) DEFAULT '0',
  `image` tinyint(1) unsigned zerofill DEFAULT NULL,
  `AvgEva` float(2,1) unsigned zerofill DEFAULT NULL,
  `Author` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EvaNum` int(5) unsigned DEFAULT '0',
  PRIMARY KEY (`CourseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of courseinfo
-- ----------------------------

-- ----------------------------
-- Table structure for iteminfo
-- ----------------------------
DROP TABLE IF EXISTS `iteminfo`;
CREATE TABLE `iteminfo` (
  `CourseID` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Identifier` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Title` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `Launch` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ParameterString` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DataFromLMS` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Prerequisites` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MasteryScore` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MaxTimeAllowed` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TimeLimitAction` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Sequence` int(11) DEFAULT NULL,
  `TheLevel` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of iteminfo
-- ----------------------------

-- ----------------------------
-- Table structure for noteinfo
-- ----------------------------
DROP TABLE IF EXISTS `noteinfo`;
CREATE TABLE `noteinfo` (
  `NoteID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `courseTitle` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `NoteStatus` tinyint(1) DEFAULT NULL,
  `Content` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`NoteID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of noteinfo
-- ----------------------------

-- ----------------------------
-- Table structure for questioninfo
-- ----------------------------
DROP TABLE IF EXISTS `questioninfo`;
CREATE TABLE `questioninfo` (
  `QuestionId` int(20) NOT NULL AUTO_INCREMENT,
  `QCourseTitle` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `QSendName` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `QuestionContent` text COLLATE utf8_unicode_ci NOT NULL,
  `QSendTime` datetime NOT NULL,
  PRIMARY KEY (`QuestionId`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of questioninfo
-- ----------------------------

-- ----------------------------
-- Table structure for usercourseinfo
-- ----------------------------
DROP TABLE IF EXISTS `usercourseinfo`;
CREATE TABLE `usercourseinfo` (
  `UserName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `CourseID` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Evaluate` int(1) unsigned zerofill DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of usercourseinfo
-- ----------------------------

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `UserID` int(50) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `UserName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `Admin` tinyint(1) NOT NULL,
  `LastCourse` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `UserFace` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `ShareNum` int(10) DEFAULT NULL,
  `RegNum` int(10) DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for userscoinfo
-- ----------------------------
DROP TABLE IF EXISTS `userscoinfo`;
CREATE TABLE `userscoinfo` (
  `UserName` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `CourseID` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `SCOID` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Launch` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ParameterString` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LessonStatus` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Prerequisites` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IsExit` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Entry` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `MasteryScore` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Sequence` bigint(20) DEFAULT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TotalTime` time DEFAULT NULL,
  `Score` int(3) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of userscoinfo
-- ----------------------------
