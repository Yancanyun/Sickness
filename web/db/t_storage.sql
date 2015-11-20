/*存放点*/
DROP TABLE
IF EXISTS `t_storage_depot`;

CREATE TABLE `t_storage_depot` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标示，主键ID',
   `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '库存存放点名称',
   `introduction` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '存放点简介',
   `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存放点';

/*库存单据*/
DROP TABLE
IF EXISTS `t_storage_report`;

CREATE TABLE `t_storage_report` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`serial_number` varchar(16) NOT NULL DEFAULT '' COMMENT '编号',
	`comment` varchar(100) NOT NULL DEFAULT '' COMMENT '单据备注',
	`depot_id` INT(11) NOT NULL DEFAULT '0' COMMENT '存放点ID',
	`handler_party_id` INT(11) NOT NULL DEFAULT '0' COMMENT '经手人',
	`created_party_id` INT(11) NOT NULL DEFAULT '0' COMMENT '当事人ID',
	`money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '单据金额',
	`status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '单据状态：0-未结算、1-已结算',
	`type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '单据类型：1-入库单、2-出库单、3-盘盈单、4-盘亏单',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存单据';

/*库存单据详细表*/
DROP TABLE
IF EXISTS `t_storage_report_item`;

CREATE TABLE `t_storage_report_item` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标示，主键ID',
   `item_id` INT(11) NOT NULL DEFAULT '0' COMMENT '原料ID',
   `quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库数量',
   `count` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '计数',
   `price` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',
   `comment` varchar(15) NOT NULL DEFAULT '' COMMENT '单据原料详细备注',
   `report_id` INT(11) NOT NULL DEFAULT '0' COMMENT '单据ID',
   `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后修改时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存单据详细';

/*结算表*/
DROP TABLE
IF EXISTS `t_storage_settlement`;

CREATE TABLE `t_storage_settlement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `serial_number` varchar(16) NOT NULL DEFAULT '' COMMENT '编号',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算表';

/*结算详情*/
DROP TABLE
IF EXISTS `t_storage_settlement_item`;

CREATE TABLE `t_storage_settlement_item` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`item_id` INT(11) NOT NULL DEFAULT '0' COMMENT '原料id',
  `stock_in_quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库数量',
  `stock_in_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库金额',
	`stock_out_quantity` DECIMAL(10,2) NOT NUll DEFAULT '0.00' COMMENT '出库数量',
	`stock_out_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '出库金额',
	`income_on_quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '盘盈数量',
	`income_on_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '盘盈金额',
	`loss_on_quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '盘亏数量',
	`loss_on_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '盘亏金额',
	`real_quantity`DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际数量',
	`real_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际金额' ,
	`total_quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '总数量',
	`total_money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '总金额',
	`settlement_id` INT(11) NOT NULL DEFAULT '0.00' COMMENT '结算id',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算详情';