/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 112.74.75.19:3306
 Source Schema         : medhub

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 08/06/2022 21:54:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(2) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '分类编号',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_name`(`name` ASC) USING BTREE COMMENT '[唯一索引] 分类名'
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '药品分类信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fre
-- ----------------------------
DROP TABLE IF EXISTS `fre`;
CREATE TABLE `fre`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '收支编号',
  `type` enum('采购','销售','退货') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '销售' COMMENT '类型',
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `total` float(7, 2) NOT NULL COMMENT '总额（最大999W）',
  `uid` int(6) UNSIGNED ZEROFILL NOT NULL COMMENT '经手人 [员工编号]',
  `sid` int(5) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '供应商编号（采购）',
  `mid` int(7) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '会员编号（销售）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`uid` ASC) USING BTREE COMMENT '[普通索引] 经手人工号',
  INDEX `idx_supplier_id`(`sid` ASC) USING BTREE COMMENT '[普通索引] 供应商编号',
  INDEX `idx_member_id`(`mid` ASC) USING BTREE COMMENT '[普通索引] 会员编号',
  INDEX `idx_time`(`time` DESC) USING BTREE COMMENT '[普通索引] 时间',
  CONSTRAINT `fk_member_id` FOREIGN KEY (`mid`) REFERENCES `member` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_supplier_id` FOREIGN KEY (`sid`) REFERENCES `supplier` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_user_id` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '财政收支表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fre_item
-- ----------------------------
DROP TABLE IF EXISTS `fre_item`;
CREATE TABLE `fre_item`  (
  `fre_id` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '财政收支编号',
  `mid` int(8) UNSIGNED ZEROFILL NOT NULL COMMENT '药品编号',
  `price` float(6, 2) UNSIGNED NOT NULL COMMENT '药品单价（最大99W）',
  `quantity` int UNSIGNED NOT NULL COMMENT '药品数量',
  `subtotal` float(7, 2) NOT NULL COMMENT '金额小计（最大999W）',
  PRIMARY KEY (`fre_id`, `mid`) USING BTREE,
  INDEX `idx_medicine_id`(`mid` ASC) USING BTREE COMMENT '[普通索引] 药品编号',
  INDEX `idx_fre_id`(`fre_id` ASC) USING BTREE COMMENT '[普通索引] 财政收支编号',
  CONSTRAINT `fk_medicine_id` FOREIGN KEY (`mid`) REFERENCES `medicine` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '财政收支明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '药品编号',
  `code` char(13) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '药品批准文号',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名称',
  `cid` int(3) UNSIGNED ZEROFILL NOT NULL COMMENT '药品所属分类编号',
  `purchase_price` float(6, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '采购单价（最大99W）',
  `price` float(6, 2) UNSIGNED NOT NULL COMMENT '销售单价（最大99W）',
  `inventory` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '库存数量',
  `limit` int UNSIGNED NOT NULL COMMENT '库存下限',
  `specification` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '规格',
  `packing` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '包装',
  `medicare` enum('甲类','乙类','无') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '无' COMMENT '医保类型',
  `manufacturer` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '生产厂家',
  `address` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '生产地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_code`(`code` ASC) USING BTREE COMMENT '[唯一索引] 批准文号',
  INDEX `idx_subcategory_id`(`cid` ASC) USING BTREE COMMENT '[普通索引] 分类编号',
  INDEX `idx_inventory`(`inventory` DESC) USING BTREE COMMENT '[普通索引] 库存数量',
  CONSTRAINT `fk_subcategory_id` FOREIGN KEY (`cid`) REFERENCES `subcategory` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '药品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(7) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '会员编号',
  `name` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '联系电话',
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '男' COMMENT '性别',
  `regdate` date NOT NULL COMMENT '注册日期',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_phone`(`phone` ASC) USING BTREE COMMENT '[唯一索引] 手机号'
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '会员信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for subcategory
-- ----------------------------
DROP TABLE IF EXISTS `subcategory`;
CREATE TABLE `subcategory`  (
  `id` int(4) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '子分类编号',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '子分类名称',
  `cid` int(2) UNSIGNED ZEROFILL NOT NULL COMMENT '所属分类编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`cid` ASC) USING BTREE COMMENT '[普通索引] 所属分类编号',
  CONSTRAINT `fk_category_id` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '药品子分类信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '供应商编号',
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '企业名称',
  `address` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '企业地址',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '联系电话',
  `email` varchar(38) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电子邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_phone`(`phone` ASC) USING BTREE COMMENT '[唯一索引] 联系电话',
  UNIQUE INDEX `uniq_email`(`email` ASC) USING BTREE COMMENT '[唯一索引] 电子邮箱'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '供应商信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT '工号',
  `password` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '123456' COMMENT '登录密码',
  `type` enum('员工','经理') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '员工' COMMENT '账号类型',
  `name` char(6) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `entry` date NOT NULL COMMENT '入职日期',
  `id_num` char(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_phone`(`phone` ASC) USING BTREE COMMENT '[唯一索引] 手机号',
  UNIQUE INDEX `uniq_id_num`(`id_num` ASC) USING BTREE COMMENT '[唯一索引] 身份证号'
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '系统用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- View structure for view_category
-- ----------------------------
DROP VIEW IF EXISTS `view_category`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_category` AS select `category`.`name` AS `分类`,`subcategory`.`name` AS `子分类`,`subcategory`.`id` AS `子分类编号` from (`subcategory` join `category` on((`subcategory`.`cid` = `category`.`id`)));

-- ----------------------------
-- View structure for view_fre_month
-- ----------------------------
DROP VIEW IF EXISTS `view_fre_month`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_fre_month` AS select year(`fre`.`time`) AS `year`,month(`fre`.`time`) AS `month`,`fre`.`type` AS `type`,sum(`fre`.`total`) AS `total` from `fre` group by year(`fre`.`time`),month(`fre`.`time`),`fre`.`type` order by year(`fre`.`time`) desc,month(`fre`.`time`) desc;

-- ----------------------------
-- View structure for view_fre_year
-- ----------------------------
DROP VIEW IF EXISTS `view_fre_year`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_fre_year` AS select year(`fre`.`time`) AS `year`,`fre`.`type` AS `type`,sum(`fre`.`total`) AS `total` from `fre` group by year(`fre`.`time`),`fre`.`type` order by year(`fre`.`time`) desc;

-- ----------------------------
-- View structure for view_sold
-- ----------------------------
DROP VIEW IF EXISTS `view_sold`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_sold` AS select `fre_item`.`mid` AS `id`,`medicine`.`name` AS `name`,sum(`fre_item`.`quantity`) AS `sold` from ((`fre_item` join `fre` on((`fre_item`.`fre_id` = `fre`.`id`))) join `medicine` on((`fre_item`.`mid` = `medicine`.`id`))) where (`fre`.`type` = '销售') group by `fre_item`.`mid` order by `sold` desc;

-- ----------------------------
-- Procedure structure for fre_filter
-- ----------------------------
DROP PROCEDURE IF EXISTS `fre_filter`;
delimiter ;;
CREATE PROCEDURE `fre_filter`(IN `start` date,IN `end` date)
BEGIN
  SET start = IFNULL(start,'1949-10-01');
	SET end = IFNULL(end,CURRENT_DATE);
	SET end = DATE_ADD(end,INTERVAL 1 DAY);
	SELECT * FROM view_fre WHERE 时间 >= start AND 时间 <= end;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for fre_statistics
-- ----------------------------
DROP PROCEDURE IF EXISTS `fre_statistics`;
delimiter ;;
CREATE PROCEDURE `fre_statistics`(IN `start` date,IN `end` date)
BEGIN
	SET start = IFNULL(start,'1949-10-01');
	SET end = IFNULL(end,CURRENT_DATE);
	SET end = DATE_ADD(end,INTERVAL 1 DAY);
	SELECT 
		COUNT(*) AS '收支数目',
		SUM(amount) AS '盈亏',
		(SELECT SUM(amount) FROM fre WHERE time >= start AND time <= end AND amount > 0) AS '总收入',
		(SELECT SUM(amount) FROM fre WHERE time >= start AND time <= end AND amount < 0) AS '总支出'
	FROM fre WHERE time >= start AND time<=end;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for purchase
-- ----------------------------
DROP PROCEDURE IF EXISTS `purchase`;
delimiter ;;
CREATE PROCEDURE `purchase`(IN `medicine_id` int,IN `quantity` int,IN `user_id` int)
BEGIN
START TRANSACTION;
INSERT INTO fre(type, mid, quantity, uid) VALUES ('采购', medicine_id, quantity, user_id);
COMMIT;
SELECT * FROM view_purchase WHERE 收支编号 = LAST_INSERT_ID();
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for return
-- ----------------------------
DROP PROCEDURE IF EXISTS `return`;
delimiter ;;
CREATE PROCEDURE `return`(IN `sales_id` int, IN `return_qty` int, IN `user_id` int)
BEGIN
DECLARE original_qty INT; -- 原购买数量
DECLARE original_fre_id INT; -- 原购买收支编号
START TRANSACTION;
SET original_fre_id = (SELECT fre_id FROM sales WHERE id = sales_id);
SET original_qty = (SELECT quantity FROM fre WHERE id = original_fre_id);
-- 如果退货数量 = NULL，则设置为原购买的数量
SET return_qty = IFNULL(return_qty, original_qty);
-- 如果退货数量 > 购买数量，则重置为购买数量
SET return_qty = IF(return_qty > original_qty, original_qty, return_qty);
-- 通过原销售编号查询原收支信息，复制并将类型从‘销售’改为‘退货’，同时指定新审核人
INSERT INTO fre(type, uid, mid, quantity) SELECT '退货', user_id, mid, return_qty FROM fre WHERE id = original_fre_id;
INSERT INTO `return`(sid, fre_id) VALUES (sales_id, LAST_INSERT_ID());
COMMIT;
-- 显示退货信息
SELECT * FROM view_return WHERE 退货编号 = LAST_INSERT_ID();
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for sales
-- ----------------------------
DROP PROCEDURE IF EXISTS `sales`;
delimiter ;;
CREATE PROCEDURE `sales`(IN `medicine_id` int,IN `quantity` int,IN `member_id` int,IN `user_id` int)
BEGIN
START TRANSACTION;
INSERT INTO fre(type, mid, uid, quantity) VALUES ('销售', medicine_id, user_id, quantity);
INSERT INTO sales(mid, fre_id) VALUES (member_id, LAST_INSERT_ID());
COMMIT;
SELECT * FROM view_sales WHERE 销售编号 = LAST_INSERT_ID();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table category
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_delete_category`;
delimiter ;;
CREATE TRIGGER `trg_before_delete_category` BEFORE DELETE ON `category` FOR EACH ROW BEGIN
	IF old.`name`='未分类' THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = '不允许删除系统预置分类';
	ELSE UPDATE subcategory SET cid=0 WHERE cid=old.id;
	END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table category
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_update_category`;
delimiter ;;
CREATE TRIGGER `trg_before_update_category` BEFORE UPDATE ON `category` FOR EACH ROW BEGIN
	IF old.`name`='未分类' THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = '不允许更新系统预置分类';
	END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table fre_item
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_after_inser_fre_item`;
delimiter ;;
CREATE TRIGGER `trg_after_inser_fre_item` AFTER INSERT ON `fre_item` FOR EACH ROW BEGIN
	DECLARE n INT DEFAULT 0;
	set n = IF(new.subtotal>0,-new.quantity,new.quantity);
	UPDATE medicine SET inventory = inventory + n WHERE id = new.mid;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table member
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_inert_member`;
delimiter ;;
CREATE TRIGGER `trg_before_inert_member` BEFORE INSERT ON `member` FOR EACH ROW SET new.`regdate` = CURRENT_DATE()
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table subcategory
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_update_subcategory`;
delimiter ;;
CREATE TRIGGER `trg_before_update_subcategory` BEFORE UPDATE ON `subcategory` FOR EACH ROW BEGIN
	IF old.`name`='未分类' THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = '不允许更新系统预置分类';
	END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table subcategory
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_delete_subcategory`;
delimiter ;;
CREATE TRIGGER `trg_before_delete_subcategory` BEFORE DELETE ON `subcategory` FOR EACH ROW BEGIN
	IF old.`name`='未分类' THEN
		SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = '不允许删除系统预置分类';
	END IF;
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_insert_user`;
delimiter ;;
CREATE TRIGGER `trg_before_insert_user` BEFORE INSERT ON `user` FOR EACH ROW # 插入新用户时，加密密码并将其入职日期设置为当天
BEGIN
	SET new.`password` = MD5(new.`password`);
	SET new.`entry` = CURRENT_DATE();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_before_update_user`;
delimiter ;;
CREATE TRIGGER `trg_before_update_user` BEFORE UPDATE ON `user` FOR EACH ROW SET new.`password` = IF(new.`password`=old.`password`,old.`password`,MD5(new.`password`))
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
