

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

# Sentinel



