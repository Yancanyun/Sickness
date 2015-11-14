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
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-菜品类别，1-具体某一个菜',
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
