/*存放点*/
DROP TABLE
IF EXISTS `t_storage_depots`;

CREATE TABLE `t_storage_depots` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标示，主键ID',
   `name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '库存存放点名称',
   `introduction` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '存放点简介',
   `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后修改时间',
   PRIMARY KEY (`id`)
) CHARSET=utf8 COMMENT='存放点';

/*库存单据*/
DROP TABLE
IF EXISTS `t_storage_document`;

CREATE TABLE `t_storage_documents` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`number` varchar(20) NOT NULL DEFAULT '' COMMENT '编号',
	`comment` varchar(255) NOT NULL DEFAULT '' COMMENT '单据备注',
	`depots_id` INT(11) NOT NULL DEFAULT '0' COMMENT '存放点ID',
	`handler` varchar(10) NOT NULL DEFAULT '' COMMENT '经手人',
	`party_id` INT(11) NOT NULL DEFAULT '0' COMMENT '当事人ID',
	`money` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '单据金额',
	`status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '单据状态：0-未结算、1-已结算',
	`type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '单据类型：1-入库单、2-出库单、3-盘盈单、4-盘亏单',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库存单据';

/*库存单据详细表*/
DROP TABLE
IF EXISTS `t_storage_item`;

CREATE TABLE `t_storage_item` (
   `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标示，主键ID',
   `item_id` INT(11) NOT NULL DEFAULT '0' COMMENT '原料ID',
   `quantity` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '入库数量',
   `count` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '计数',
   `price` DECIMAL(10,2) NOT NULL DEFAULT '0.00' COMMENT '成本价',
   `document_id` INT(11) NOT NULL DEFAULT '0' COMMENT '单据ID',
   `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'创建时间',
   `last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT'最后修改时间',
   PRIMARY KEY (`id`)
)CHARSET=utf8 COMMENT='库存单据详细';

/*结算表*/
DROP TABLE
IF EXISTS `t_storage_settlement`;

CREATE TABLE `t_storage_settlement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='结算表';

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
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算详情';

/* 库存物品 */
CREATE TABLE `t_storage_item` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
	`supplier_party_id` INT(11) NOT NULL DEFAULT '0' COMMENT '供货商ID',
	`tag_id` INT(11) NOT NULL DEFAULT '0' COMMENT '分类ID',
	`name` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '名称',
	`item_number` VARCHAR(30) NOT NULL DEFAULT '' COMMENT '物品编号',
	`assistant_code` VARCHAR(30) NOT NULL  DEFAULT '' COMMENT '助记码',
	`order_unit_id` INT(11) NOT NULL DEFAULT '0' COMMENT '订货单位',
	`storage_unit_id` INT(11) NOT NULL DEFAULT '0' COMMENT '库存单位',
	`cost_card_unit_id` INT(11) NOT NULL DEFAULT '0' COMMENT '成本卡单位',
	`orderToStorageRatio` DECIMAL(10, 2) NOT NULL DEFAULT '1.00' COMMENT '订货单位到库存单位转换比例',
	`storageToCostCardRatio` DECIMAL(10, 2) NOT NULL DEFAULT '1.00' COMMENT '库存单位到成本卡单位转换比例',
	`max_storage_quantity` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '最大库存量',
	`min_storage_quantity` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '最小库存量',
	`stock_out_type` TINYINT(11) NOT NULL DEFAULT '1' COMMENT '出库方式(1-加权平均,2-手动)',
	`total_stock_in_quantity` DECIMAL(11, 2) NOT NULL DEFAULT '0.00' COMMENT '总进货数量',
	`total_stock_in_money` DECIMAL(11, 2) NOT NULL DEFAULT '0.00' COMMENT '总进货金额',
	`last_stock_in_price` DECIMAL(10, 2) NOT NULL DEFAULT '0.00' COMMENT '最近入库单价',
	`created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`last_modified_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='库存物品表'