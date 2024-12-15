# Components




## Postgres

pull images and run
```shell
docker pull postgres
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

check Postgres version
```shell
docker exec -it postgres bash

# root@a22bc1d489e6:/# 
cat /var/lib/postgresql/data/PG_VERSION
exit
```

## MongoDB

```shell
docker pull mongodb/mongodb-community-server:latest

docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest

/usr/bin/mongosh
```
