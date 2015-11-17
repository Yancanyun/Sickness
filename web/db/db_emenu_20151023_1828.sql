/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.24-log : Database - db_emenu
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_emenu` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_emenu`;

/*Table structure for table `t_area` */

DROP TABLE IF EXISTS `t_area`;

CREATE TABLE `t_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '区域名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '区域状态：0、可用；1、已删除',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='餐台区域表';

/*Data for the table `t_area` */

insert  into `t_area`(`id`,`name`,`status`,`created_time`,`last_modified_time`) values (1,'1楼',0,'2015-10-23 15:19:33','2015-10-23 15:54:43'),(2,'2楼',0,'2015-10-23 15:19:41','2015-10-23 15:19:43'),(3,'3楼',1,'2015-10-23 15:19:50','2015-10-23 15:19:53'),(4,'4楼',0,'2015-10-23 15:41:48','2015-10-23 15:41:48');

/*Table structure for table `t_dish_tag` */

DROP TABLE IF EXISTS `t_dish_tag`;

CREATE TABLE `t_dish_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '类别名称',
  `p_id` int(11) NOT NULL DEFAULT '0' COMMENT '父类别ID',
  `weight` int(11) NOT NULL DEFAULT '20' COMMENT '权重',
  `type` tinyint(4) NOT NULL COMMENT 'type为1则为菜品的类别，type为2则为原料的类别',
  `print_after_confirm_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '下单之后是否立即打印(0-不立即打印,1-立即打印)(此字段只用于最低级分类)',
  `max_print_num` int(11) NOT NULL DEFAULT '1' COMMENT '分类下的菜同时最多可以做的数量(此字段只用于最低级分类)',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='菜品与原料的类别';

/*Data for the table `t_dish_tag` */

insert  into `t_dish_tag`(`id`,`name`,`p_id`,`weight`,`type`,`print_after_confirm_order`,`max_print_num`,`created_time`,`last_modified_time`) values (1,'商品',0,20,1,0,1,'2015-10-23 18:25:31','2015-10-23 18:25:31'),(2,'菜品',0,20,1,0,1,'2015-10-23 18:28:00','2015-10-23 18:28:00');

/*Table structure for table `t_dish_unit` */

DROP TABLE IF EXISTS `t_dish_unit`;

CREATE TABLE `t_dish_unit` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ψһ��ʶ������ID',
  `name` varchar(10) NOT NULL DEFAULT '' COMMENT '��λ����',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1����������λ��2����������λ',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����޸�ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='��Ʒ��ԭ�ϵĵ�λ';

/*Data for the table `t_dish_unit` */

insert  into `t_dish_unit`(`id`,`name`,`type`,`created_time`,`last_modified_time`) values (2,'斤',2,'2015-10-23 15:17:09','2015-10-23 17:32:08'),(3,'杯',2,'2015-10-23 15:53:23','2015-10-23 15:54:25'),(5,'碗',2,'2015-10-23 17:20:42','2015-10-23 17:33:32');

/*Table structure for table `t_index_img` */

DROP TABLE IF EXISTS `t_index_img`;

CREATE TABLE `t_index_img` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `img_path` varchar(255) NOT NULL DEFAULT '' COMMENT '图片路径',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：未使用，1：正在使用',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_index_img` */

/*Table structure for table `t_keywords` */

DROP TABLE IF EXISTS `t_keywords`;

CREATE TABLE `t_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `key` varchar(15) NOT NULL DEFAULT '' COMMENT '关键字',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0：点餐平台的关键字；1：服务员系统的关键字',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_keywords` */

insert  into `t_keywords`(`id`,`key`,`type`,`created_time`,`last_modified_time`) values (2,'xz',1,'2015-10-23 10:56:06','2015-10-23 10:56:06'),(3,'鸡肉',0,'2015-10-23 10:57:00','2015-10-23 10:57:00'),(4,'xw',1,'2015-10-23 10:57:19','2015-10-23 10:57:19');

/*Table structure for table `t_party` */

DROP TABLE IF EXISTS `t_party`;

CREATE TABLE `t_party` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `party_type_id` int(11) NOT NULL DEFAULT '0' COMMENT '当事人类型ID',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='当事人表';

/*Data for the table `t_party` */

insert  into `t_party`(`id`,`party_type_id`,`description`,`created_user_id`,`created_time`,`last_modified_time`) values (1,1,'测试',0,'2015-10-16 11:04:07','2015-10-16 11:04:07');

/*Table structure for table `t_party_employee_role` */

DROP TABLE IF EXISTS `t_party_employee_role`;

CREATE TABLE `t_party_employee_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `party_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户表id',
  `role_id` int(11) NOT NULL DEFAULT '1' COMMENT '用户角色：1代表后台，2代表吧台，3代表后厨，4代表服务员，5代表顾客',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `t_party_employee_role` */

/*Table structure for table `t_party_employee_waiter_table` */

DROP TABLE IF EXISTS `t_party_employee_waiter_table`;

CREATE TABLE `t_party_employee_waiter_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `party_id` int(11) NOT NULL DEFAULT '0' COMMENT '服务员id',
  `table_id` int(11) NOT NULL DEFAULT '0' COMMENT '餐桌id',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务员服务的餐桌表';

/*Data for the table `t_party_employee_waiter_table` */

/*Table structure for table `t_party_emplyee` */

DROP TABLE IF EXISTS `t_party_emplyee`;

CREATE TABLE `t_party_emplyee` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(40) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `phone` varchar(15) NOT NULL DEFAULT '' COMMENT '用户电话',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0代表禁用，1代表启用，2代表已删除',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `t_party_emplyee` */

/*Table structure for table `t_party_security_group` */

DROP TABLE IF EXISTS `t_party_security_group`;

CREATE TABLE `t_party_security_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '安全组名称',
  `description` varchar(1024) DEFAULT '' COMMENT '安全组的简短描述',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全组表';

/*Data for the table `t_party_security_group` */

/*Table structure for table `t_party_security_group_permission` */

DROP TABLE IF EXISTS `t_party_security_group_permission`;

CREATE TABLE `t_party_security_group_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL DEFAULT '0' COMMENT '安全组ID',
  `permission_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安全组-权限关联表';

/*Data for the table `t_party_security_group_permission` */

/*Table structure for table `t_party_security_permission` */

DROP TABLE IF EXISTS `t_party_security_permission`;

CREATE TABLE `t_party_security_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `expression` varchar(512) NOT NULL DEFAULT '' COMMENT '权限表达式',
  `description` varchar(1024) NOT NULL DEFAULT '' COMMENT '权限描述',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

/*Data for the table `t_party_security_permission` */

/*Table structure for table `t_party_security_user` */

DROP TABLE IF EXISTS `t_party_security_user`;

CREATE TABLE `t_party_security_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `party_id` int(11) NOT NULL DEFAULT '0' COMMENT '当事人ID',
  `login_name` varchar(128) NOT NULL DEFAULT '' COMMENT '登录名',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态:0-禁用,1-启用',
  `account_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '账户类型:1-正常账户,2-微信账户',
  `update_password` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否需要更新密码:0-不需要,1-需要',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户登录表';

/*Data for the table `t_party_security_user` */

insert  into `t_party_security_user`(`id`,`party_id`,`login_name`,`password`,`status`,`account_type`,`update_password`,`created_time`,`last_modified_time`) values (1,1,'sadmin','e10adc3949ba59abbe56e057f20f883e',2,1,0,'2015-10-16 11:08:15','2015-10-16 11:10:01'),(2,1,'wechat','e10adc3949ba59abbe56e057f20f883e',1,2,0,'2015-10-16 11:16:28','2015-10-16 11:16:28');

/*Table structure for table `t_party_security_user_group` */

DROP TABLE IF EXISTS `t_party_security_user_group`;

CREATE TABLE `t_party_security_user_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `group_id` int(11) NOT NULL DEFAULT '0' COMMENT '安全组ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-安全组关联表';

/*Data for the table `t_party_security_user_group` */

/*Table structure for table `t_party_type` */

DROP TABLE IF EXISTS `t_party_type`;

CREATE TABLE `t_party_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '类型名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级类型ID',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '描述',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='当事人类别(默认1-员工,2-会员)';

/*Data for the table `t_party_type` */

/*Table structure for table `t_party_vip_info` */

DROP TABLE IF EXISTS `t_party_vip_info`;

CREATE TABLE `t_party_vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员id',
  `party_id` int(11) NOT NULL DEFAULT '0' COMMENT '当事人id',
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '会员姓名',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别,0-未说明,1-男,2-女,3-其他',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(15) NOT NULL DEFAULT '' COMMENT '电话号码',
  `qq` varchar(15) DEFAULT '' COMMENT 'qq号码',
  `email` varchar(30) DEFAULT '' COMMENT '邮箱',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '帐号状态,1-启用,2-停用,3-删除',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员基本信息表';

/*Data for the table `t_party_vip_info` */

/*Table structure for table `t_table` */

DROP TABLE IF EXISTS `t_table`;

CREATE TABLE `t_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_id` int(11) NOT NULL COMMENT '所属区域ID',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '餐台名称',
  `seat_num` int(11) NOT NULL DEFAULT '0' COMMENT '标准座位数',
  `seat_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '餐位费用/人',
  `table_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '餐台费用',
  `min_cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低消费',
  `qrcode_path` varchar(255) NOT NULL DEFAULT '' COMMENT '二维码地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '餐台状态：0、停用；1、可用；2、占用已结账；3、占用未结账；4、已并桌；5、已预订；6、已删除',
  `person_num` int(11) NOT NULL DEFAULT '0' COMMENT '实际人数',
  `open_time` datetime DEFAULT NULL COMMENT '开台时间',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='餐台表';

/*Data for the table `t_table` */

insert  into `t_table`(`id`,`area_id`,`name`,`seat_num`,`seat_fee`,`table_fee`,`min_cost`,`qrcode_path`,`status`,`person_num`,`open_time`,`created_time`,`last_modified_time`) values (1,1,'1号桌',10,'0.00','0.00','0.00','',1,0,NULL,'2015-10-23 17:26:04','2015-10-23 17:26:06');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
