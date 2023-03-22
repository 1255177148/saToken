-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.29 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 sa_token_demo 的数据库结构
CREATE DATABASE IF NOT EXISTS `sa_token_demo` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sa_token_demo`;

-- 导出  表 sa_token_demo.system_operate_log 结构
CREATE TABLE IF NOT EXISTS `system_operate_log` (
                                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志id',
                                                    `operate_user_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '操作用户id',
                                                    `operate_user_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作人员',
                                                    `operate_login_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作账号',
                                                    `operate_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '操作类型',
                                                    `operate_module_id` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '操作模块id',
                                                    `operate_content` varchar(4000) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
                                                    `operate_time` datetime DEFAULT NULL COMMENT '操作时间',
                                                    `user_ip` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作ip',
                                                    `operate_module_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作模块名称',
                                                    `operate_source` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '1' COMMENT '数据来源 1 WEB端 2 app端',
                                                    `log_type` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8_general_ci DEFAULT '1' COMMENT ' 类型1 操作日志 2 登录日志',
                                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC COMMENT='平台操作日志表';

-- 正在导出表  sa_token_demo.system_operate_log 的数据：~0 rows (大约)
DELETE FROM `system_operate_log`;

-- 导出  表 sa_token_demo.user 结构
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `login_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
                                      `user_name` varchar(50) NOT NULL DEFAULT '',
                                      `password` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';

-- 正在导出表  sa_token_demo.user 的数据：~1 rows (大约)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `login_name`, `user_name`, `password`) VALUES
    (1, 'admin', '管理员', '123');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
