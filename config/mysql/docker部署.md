# MySQL
container:
```shell
docker pull mysql:8.0.39
docker run -d --name mysql --restart always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:8.0.39
```

# 创建数据库
```shell
> docker exec -it mysql /bin/bash
bash> mysql -uroot -proot
mysql> create database code_snippet;
```


# 配置文件
```
cat /etc/my.cnf
```
