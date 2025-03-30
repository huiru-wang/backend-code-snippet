

## Nacos
```shell
docker pull nacos/nacos-server:2.2.3
docker run -d --name nacos -p 8848:8848 -p 9848:9848 -p 9849:9849 -e MODE=standalone -e NACOS_USER_NAME=nacos -e NACOS_USER_PASSWORD=nacos nacos/nacos-server:v2.3.1
```

