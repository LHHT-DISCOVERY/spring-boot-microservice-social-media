# spring-boot-microservice-social-media

## Microservices đang là kiến trúc được sử dụng rộng rãi nhất hiện nay, repo này thực hiện cách triển khai micoservices với spring boot 3 thông qua việc xây dựng từ đầu dự án mạng xã hội



### 1. **------------------------------------------install MYSQL from docker ------------------------------------------** 

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



### 2. **------------------------------------------ Install Mongodb from docker ------------------------------------------**

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




### 3. **------------------------------------------ Install GraphDB NEO4J from docker ------------------------------------------**

#### Start Docker
#### Install Neo4j from Docker Hub
#### CLI type to pull images of Docker Hub:
```docker pull neo4j:latest```

#### Start Neo4j server at port 7474 (admin page run on web) with root username and password: neo4j/12345678
#### at port 7687 -> connect to admin server (7474)
#### CLI type to start container from images of docker:
``` docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/12345678' neo4j:latest```



### 4. **------------------------------------------ Using Profile and variable environment ------------------------------------------**

#### Using Profile
##### Docs Spring : https://docs.spring.io/spring-boot/reference/features/profiles.html
##### 1.Start Intellij
##### 2.Because values in dev environment usually difference in prod environment; need to edit change 
###### example : url, username, password of db ,....vv.v.v of two environment is different 
###### certain is: side dev env we can using db localhost with username and password we define by myself but prod env using db at cloud with url,username and password not same dev env -> need to override application yml at dev env
##### 3.At application of service need run, side right button debug -> click ``` ...```  -> choose ``` edit ```  -> choose ``` modify options```  -> choose ``` add VM options``` -> type ```-Dspring.profiles.active=prod```  above application class 
###### ->> ```active=prod``` this mean depend on name ```application-prod.yml``` have "prod" 
##### 4.Run application again -> will run with application yml file of ```prod```

    ``` detail example more:
    Assume we have files following:
    - application.yml
    - application-dev.yml
    - application-prod.yml
    - custom-config.yml ```

#### And we want to using config in ```custom-config.yml``` to override values in ```application-dev.yml```, we can beginning from step2 then type:
```-Dspring.profiles.active=dev -Dspring.config.location=classpath:/custom-config.yml,classpath:/application-dev.yml```
##### or using  ```spring.config.location```
```-Dspring.config.location=classpath:/custom-config.yml,classpath:/application-dev.yml```
#### this mean, will do Spring Boot load file ```custom-config.yml``` first,then load file ```application-dev.yml``` to override values config from ```custom-config.yml```

#### Problem packing application to jar file
##### if you want to override values config default by config from a profile other,you can config as below:
##### in ```application.yml```
    spring:        
        config:
            active:
                on-profile: dev

##### then, you can have configs certain for each profile:

##### in ```application-dev.yml```
    spring:         
        config:
            active:
                on-profile: dev_value

##### in ```application-prod.yml```
    spring:         
        config:
            active:
                on-profile: prod_value

#### when we active profile prod, values ```config.active.on-profile``` will be ```prod_value```.
#### Run with profile ```dev```
#### CLI : ```java -jar target/myapp.jar -Dspring.profiles.active=dev```
#### Run with profile ```prod```
#### CLI : ```java -jar target/myapp.jar -Dspring.profiles.active=prod```



### 5. **------------------------------------------ Using Variable Environment ------------------------------------------**

#### target is hide essential information in application yml config 
##### Docs Spring : https://docs.spring.io/spring-boot/reference/features/external-config.html
    ``` To convert a property name in the canonical-form to an environment variable name you can follow these rules:
    - Replace dots (.) with underscores (_).
    - Remove any dashes (-).
    - Convert to uppercase.```
##### 1.Start Intellij
##### 2.At application of service need run, side right button debug -> click ``` ...```  -> choose ``` edit ```  -> choose ``` Environment variables```  -> choose icon ``` same book``` -> type ```Name``` and ```Value``` , Note that: when type ```Name``` need to implement role above
##### 3.Remove variable im yml application that we used at step4 -> Run application again  
    ``` ex: create variable environment in yml file to hide value 3600 of jwt valid-duration: 

    ----------------- C1 -----------------
    -> in file yml original:

         jwt:
            valid-duration: 3600

    -> value is: jwt.valid-duration -> have value 3600
    -> convert to variable environment by introduction step2 above: type  "Name" is : JWT_VALIDDURATION -> "Name" is valid by rules above, then type "Value" : 3600;
    -> result will hide value need to security

    ----------------- or using C2 -----------------
    
    -> in file yml original:

         jwt:
            valid-duration: 3600
    

    -> changed yml file to:

         jwt:
            valid-duration: ${JWT_VALIDDURATION}
 
    -> convert to variable environment by introduction step2 above : type "Name" is : JWT_VALIDDURATION, then type "Value" : 3600;
    -> result will hide value need to security, 
    -> can see at application.yml with db 
        -> explaint: (${DBMS_PASSWORD:123456} this means, if not define value "Name" and "Value" in step2 -> will get default value after character ":" is 123456)