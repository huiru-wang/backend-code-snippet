# MySQL
container:
```shell
docker pull mysql:8.0.39
docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0.39

docker exec -it mysql /bin/bash
docker exec -it mysql mysql -uroot -proot

# 配置文件
cat /etc/my.cnf
```
表：
```sql
CREATE TABLE IF NOT EXISTS `t_user` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(128) NOT NULL COMMENT "唯一user_id",
    `username` VARCHAR(128) NOT NULL COMMENT "username",
    `password` VARCHAR(128) NOT NULL COMMENT "password",
    `phone_no` VARCHAR(20) DEFAULT NULL COMMENT "phone",
    `email` VARCHAR(100) DEFAULT NULL COMMENT "email",
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY `uk_user_id` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `t_order` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `order_id` VARCHAR(128) NOT NULL,
    `user_id` VARCHAR(128) NOT NULL,
    `type` INT NOT NULL COMMENT '订单类型',
    `status` INT NOT NULL COMMENT '订单状态',
    `payment_type` INT NOT NULL COMMENT '支付方式',
    `amount` DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     UNIQUE KEY `uk_order_id` (`order_id`),
     INDEX idx_user_id(`user_id`)
);

CREATE TABLE IF NOT EXISTS `t_order_detail`(
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
```

# PostgreSQL
```shell
docker pull postgres:14.15
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres postgres:14.15

docker exec -it postgres /bin/bash
docker exec -it postgres psql -U postgres
```

# MongoDB
```shell
docker pull mongo:7.0.15
docker run -d --name mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=root mongo:7.0.15

docker exec -it mongo mongosh
```

# Redis
```shell
docker pull redis:6.2
docker run -d --name redis -p 6379:6379 redis:6.2
```

# RabbitMQ
```shell
docker pull rabbitmq:4.0.2
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:4.0.2

docker exec -it rabbitmq:4.0.2 /bin/bash
```
开启控制台，登录容器执行：
```shell
rabbitmq-plugins enable rabbitmq_management
```
访问：http://localhost:15672
账户：guest，密码：guest


# Nacos

```shell
docker pull nacos/nacos-server:2.2.3
docker run -d --name nacos -p 8848:8848 -p 9848:9848 -p 9849:9849 -e MODE=standalone -e NACOS_USER_NAME=nacos -e NACOS_USER_PASSWORD=nacos nacos/nacos-server:v2.3.1
```


