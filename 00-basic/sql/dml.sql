CREATE TABLE `t_user` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(128) NOT NULL COMMENT "唯一user_id",
    `password` VARCHAR(128) NOT NULL COMMENT "password",
    `phone_no` VARCHAR(20) DEFAULT NULL COMMENT "phone",
    `email` VARCHAR(100) DEFAULT NULL COMMENT "email",
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_user_id` (`user_id`)
);

CREATE TABLE `t_order` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_id` VARCHAR(128) NOT NULL,
    `user_id` VARCHAR(128) NOT NULL,
    `type` INT(11) NOT NULL COMMENT '订单类型',
    `status` INT(11) NOT NULL COMMENT '订单状态',
    `payment_method` INT(11) NOT NULL COMMENT '支付方式',
    `amount` DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     UNIQUE KEY `uk_order_id` (`order_id`),
     INDEX idx_user_id(`user_id`)
);

CREATE TABLE t_order_detail(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_id` VARCHAR(128) NOT NULL COMMENT "订单id",
    `sku_id` VARCHAR(128) NOT NULL COMMENT "商品id",
   	`price` DECIMAL(10,2) unsigned NOT NULL COMMENT "原价格",
    `actual_price` DECIMAL(10,2) unsigned  NOT NULL COMMENT "实际购买价格",
    `quantity` INT unsigned NOT NULL COMMENT "购买数量",
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_order_id(`order_id`)
) COMMENT="订单详情表";