/*菜品与原料的类别*/
DROP TABLE
IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '类别名称',
  `p_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类别ID',
  `weight` int(11) NOT NULL DEFAULT '20' COMMENT '权重',
  `time_limit` int(11) NOT NULL DEFAULT '0' COMMENT '上菜时限',
  `print_after_confirm_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '下单之后是否立即打印(0-不立即打印,1-立即打印)(此字段只用于最低级分类)',
  `max_print_num` int(11) NOT NULL DEFAULT '1' COMMENT '分类下的菜同时最多可以做的数量(此字段只用于最低级分类)',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='菜品与原料的类别';


/*菜品分类与打印机关联表*/
DROP TABLE
IF EXISTS `t_dish_tag_printer`;

CREATE TABLE `t_dish_tag_printer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `dish_tag_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜品分类ID',
  `printer_id` int(11) NOT NULL DEFAULT '0' COMMENT '打印机ID',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1-菜品类别，2-具体某一个菜',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜品分类与打印机关联表';


/*菜品与原料的单位*/
DROP TABLE
IF EXISTS `t_dish_unit`;

CREATE TABLE `t_dish_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(10) NOT NULL DEFAULT '' COMMENT '单位名称',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1代表重量单位，2代表数量单位',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位';

/* 菜品表 */
DROP TABLE IF EXISTS `t_dish`;
CREATE TABLE `t_dish` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '名称',
	`dish_number` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '菜品编号',
	`assistant_code` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '助记码',
	`unit_id` INT(11) NOT NULL DEFAULT '0' COMMENT '单位ID',
	`price` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '定价',
	`sale_type` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '促销方式(1-无促销,2-折扣,3-促销价格)',
	`discount` DECIMAL(4, 2) NOT NULL DEFAULT '10.00' COMMENT '折扣',
	`sale_price` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '售价',
	`category_id` INT(11) NOT NULL DEFAULT '3' COMMENT '总分类ID',
	`tag_id` INT(11) NOT NULL DEFAULT '0' COMMENT '菜品小类ID',
	`description` VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '简介',
	`status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '状态(0-停售,1-销售中,2-标缺,3-已删除)',
	`like_nums` INT(11) NOT NULL DEFAULT '0' COMMENT '点赞人数',
	`is_network_available` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否网络可点(0-不可用,1-可用)',
	`is_vip_price_available` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否启用会员价(0-不可用,1-可用)',
	`is_voucher_available` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否可用代金卷(0-不可用,1-可用)',
	`time_limit` INT(11) NOT NULL DEFAULT '0' COMMENT '上菜时限(0-无限制)',
	`created_party_id` INT(11) NOT NULL DEFAULT '0' COMMENT '创建者partyId',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '菜品表';

/* 口味表 */
DROP TABLE IF EXISTS `t_taste`;
CREATE TABLE `t_taste` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`name` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '名称',
	`related_charge` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '关联收费',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '口味';

/* 菜品-口味关联表 */
DROP TABLE IF EXISTS `t_dish_taste`;
CREATE TABLE `t_dish_taste` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`dish_id` INT(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
	`taste_id` INT(11) NOT NULL DEFAULT '0' COMMENT '口味ID',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT'菜品-口味关联表';

/* 菜品图片表 */
DROP TABLE IF EXISTS `t_dish_img`;
CREATE TABLE `t_dish_img` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`dish_id` INT(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
	`img_type` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '图片类型(1-小图,2-大图)',
	`img_path` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '图片路径',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '菜品-图片表';

/* 套餐表 */
DROP TABLE IF EXISTS `t_dish_package`;
CREATE TABLE `t_dish_package` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`package_id` INT(11) NOT NULL DEFAULT '0' COMMENT '套餐ID',
	`dish_id` INT(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
	`dish_quantity` INT(11) NOT NULL DEFAULT '1' COMMENT '菜品数量',
	`created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '套餐表';


/* 成本卡 */
DROP TABLE IF EXISTS `t_cost_card`;
CREATE TABLE `t_cost_card` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`dish_id` INT(11) NOT NULL DEFAULT '0' COMMENT '菜品ID',
	`standard_cost` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '标准成本',
	`condiment_cost` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '调料成本',
	`created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='成本卡';
/* 成本卡物品 */
DROP TABLE IF EXISTS `t_cost_card_item`;
CREATE TABLE `t_cost_card_item` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`cost_card_id` INT(11) NOT NULL DEFAULT '0' COMMENT '成本卡ID',
	`item_id` INT(11) NOT NULL DEFAULT '0' COMMENT '库存物品ID',
	`net_item_quantity` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '净料用量',
	`net_item_ratio` DECIMAL(10, 2) NOT NULL DEFAULT '100.00' COMMENT '净料率',
	`auto_stock_out` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否自动出库(0-否，1-是)',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY(`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='成本卡物品表';
