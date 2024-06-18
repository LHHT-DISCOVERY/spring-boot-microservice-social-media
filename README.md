# spring-boot-microservice-social-media

## Microservices đang là kiến trúc được sử dụng rộng rãi nhất hiện nay, repo này thực hiện cách triển khai micoservices với spring boot 3 thông qua việc xây dựng từ đầu dự án mạng xã hội

### 1. **install MYSQL from docker** 

#### Start Docker
#### Install MYSQL from Docker Hub
#### CLI type to pull images of Docker Hub:
```docker pull mysql:8.0.36-debian```

#### 0: "docker run" using to run image (then run -> show in container) we just pull from docker hub
#### 1: "--name mysql-8.0.36" is name of container
#### 2: "-p 3306:3306" export port to public , "3306"(port need to export to using connection):"3306" (port of mysql)
#### 3: "-e MYSQL_ROOT_PASSWORD=123456" is set password for mysql : 123456
#### 4 : "-d mysql:8.0.36-debian" version of mysql image just pull from docker hub, "8.0.36-debian"
#### CLI type to start container from images of docker: 
``` docker run --name mysql-8.0.36 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.36-debian```
#### download Mysql workbench :

https://dev.mysql.com/downloads/file/?id=525959



### 2. **install Mongodb from docker**

#### Start Docker
#### Install Mongodb from Docker Hub
#### CLI type to pull images of Docker Hub:
```docker pull bitnami/mongodb:7.0.11```

#### Start Mongodb server at port 27017 with root username and password: root/root
#### CLI type to start container from images of docker:
``` docker run -d --name mongodb-7.0.11 -p 27017:27017 -e MONGODB_ROOT_USER=root -e MONGODB_ROOT_PASSWORD=root bitnami/mongodb:7.0.11 ```

#### download Mongo compass :

https://www.mongodb.com/try/download/compass

#### connect from Mongo compass to Mongo server at port: 27017

    ``` Advanced Connection Options 
        -> authentication 
        -> enter username and password 
        -> connect 
        -> successful ```


### 3. **install GraphDB NEO4J from docker**

#### Start Docker
#### Install Neo4j from Docker Hub
#### CLI type to pull images of Docker Hub:
```docker pull neo4j:latest```

#### Start Neo4j server at port 7474 (admin page run on web) with root username and password: neo4j/12345678
#### at port 7687 -> connect to admin server (7474)
#### CLI type to start container from images of docker:
``` docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/12345678' neo4j:latest```
