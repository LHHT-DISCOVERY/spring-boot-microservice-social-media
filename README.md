# spring-boot-microservice-social-media
# spring-boot-microservice-social-media...hi
# Create by Huu Tri Ly

# spring-boot-microservice-social-media
# Create by Huu Tri Ly
## Microservices đang là kiến trúc được sử dụng rộng rãi nhất hiện nay, repo này thực hiện cách triển khai micoservices với spring boot 3 thông qua việc xây dựng từ đầu dự án mạng xã hội

## RoadMap Spring Microservices

### ------------------------ Spring Cloud ------------------------

#### API Gateway and Load balancer

    ``` API Gateway and Load balancer , it same as layer , "ontop" of microservices below side,
        ,It will responsibility Navigation of requests to each service , can load balancer```

#### Http Clients (OpenFeign,...)

    ``` Http Clients need to communicate between microservices -> standard communication precent is protocol HTTP
        , we need to understand and using smoothly Http Clients , in spring cloud usally (OpenFeign) because it no need implementation many code
        ```

#### Distributed Logging, Tracing, metric (ELK stack, Zipkin, Grafana)

    ``` This Distributed Logging, Tracing, metric includes information about system that we can observe : logging (log information of the system)
        , tracing (we tracking a request from api gateway, then this request how many microservices will go thought )

        including tool to resolve the above problem (we only understand and using this tool):
        - OpenTelemetry : is a protocol supported logging,tracing and metric
        - Promethues : help we collect information from microservices by real time, using for metrics
        - Grafana : same Presentation Layer of "Promethues", Promethues will collect information
            ->  Grafana responsible for show data to we can know and tracking information
        - Zipkin : help we know a request how many microservices will go thought and How much time does this request take at each microservice
        - ELK stack (important): using for logging, Elasticsearch , Loggstack and Kibana - help we collect information ,search and show log
        ```

#### Resilient microservices (SAGA pattern, Circuits breaker pattern, Bulk Head)

    ```  Resilient microservices to secure each microservice run stable and can restore when met incident
    including pattern to resolve the above problem:
        - SAGA pattern : help and process management transaction of microservices -> this mean one: all successful ,or two: rollback all
        - Circuits breaker pattern : because microservices interdependence, when one service happen incident -> influence the other services
            (ex: has a request from service1 call to service2 -> happens incident -> interupt connect to that service2 -> when service2 run successful -> normal operation )
                -> need to apply Circuits breaker pattern at service1 -> to avoid take time
        - Bulk Head : target Bulk Head is handle problems with number of request larger than ability to process of service -> cause issue
                -> so, we determine the number of requests that reach the service processing limit
                    -> we will informational is "server can busy" -> we can will process this request at a later time
    ```

#### Documentation with swagger

    ``` To microservices communicate with each other , we need to using Documentation with swagger to do standard for api
        ex: what is respons? , what is request body? , what is parameter? ,.... it will handle by swagger```

### ------------------------ Architecture ------------------------

#### DDD (Domain Driven Design)

    ``` Designing a system by domain, simply understood as a business, a certain field, e.g.,
    ex: a system with many business, we will subdivide each business, and each microservice will handle each business differently
    ex detail : (userservice is responsible for managing users, notificationservice are responsible for managing such as email, SMS,.. etc...) ```

#### EDD (Event Driven Design) (Kafka, RabbitMQ)

    ``` Designing a system operator by events, when cause event -> create trigger another microservice
        Include tools:
        - Kafka : "Kafka acts as a postman",help we transport messages from this microservice to other microservices.
            Kafka includes : producer (this mean who sent notification) and consumer (this mean who received notification)
             + Kafka cluster :  is where responsible for sending notification to consumer
                + in kafka cluster including "topics", "topic" will navigate when we want to "what is send notification" ?
                    + example same as a T.V will have multiple channels (VTV1, VTV2, VTV3, etc) each channel will have multiple content another,
                        when we want to seen "what content" ? we navigate to that channel, and that chanel will contain content we wish seen
            ** ex kafka in this system : kafka will be intermediate,
            + details : when user registeres successfully (in identityservice) -> identityservice will sent message to kafka -> then kafka will send message to Notification service
                        -> Notification service will receive this message from kafka -> it will send email to user with contain of email can is "Welcome user onboard" .
            ** instead of, at indentity service -> when register successful -> call api to Notification service and Notification service will send mail -> at here, we will using kafka
                **NOTE THAT: by using kafka will help us Reduce the workload of the service -> secure "secret of conccern" of each microservice
                    -> this mean, identity service implementation successfully -> it only need public messages to kafka at topic certain that define before,
                     -> and indentity service don't care any service will handle these messages that indentity service only focus on features registere, and sent messages to kafka
                        -> and other services that want to take that notifications -> listens in kafka to have action with business by myself
                            -> we will using notifications service to listen in kafka -> if kafka have messages "register successfully"
                                -> sent mail to user with content is "wellcome user onboard"
                            -> in this system -> indetity service is producer and consumer is notification service
                ** reducing workload in this case is: indetity service after the public to kafka,indetity service will don't care next step or next logic
                            -> so, save resources for handle other requests
                    -> at notification service will receive mutiple notification, and notification can no need to process inmediately
                        -> that notification service will have mechanism processing gradually that requests -> it still secure user recives notification and system operation smoothly
        - RabbitMQ : ...
    ```

### ------------------------ Devops ------------------------

#### Docker

#### K8s

#### AWS/Azure/GCP

#### Github Actions , GitLab CI/CD

#### Jenkin

### 1. **------------------------ Install MYSQL from docker ------------------------**

#### Start Docker

#### Install MYSQL from Docker Hub

#### CLI type to pull images of Docker Hub:

`docker pull mysql:8.0.36-debian`

#### 0: "docker run" using to run image (then run -> show in container) we just pull from docker hub

#### 1: "--name mysql-8.0.36" is name of container

#### 2: "-p 3306:3306" export port to public , "3306"(port need to export to using connection):"3306" (port of mysql)

#### 3: "-e MYSQL_ROOT_PASSWORD=123456" is set password for mysql : 123456

#### 4 : "-d mysql:8.0.36-debian" version of mysql image just pull from docker hub, "8.0.36-debian"

#### CLI type to start container from images of docker:

` docker run --name mysql-8.0.36 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.36-debian`

#### download Mysql workbench :

https://dev.mysql.com/downloads/file/?id=525959

### 2. **------------------------ Install Mongodb from docker ------------------------**

#### Start Docker

#### Install Mongodb from Docker Hub

#### CLI type to pull images of Docker Hub:

`docker pull bitnami/mongodb:7.0.11`

#### Start Mongodb server at port 27017 with root username and password: root/root

#### CLI type to start container from images of docker:

`docker run -d --name mongodb-7.0.11 -p 27017:27017 -e MONGODB_ROOT_USER=root -e MONGODB_ROOT_PASSWORD=root bitnami/mongodb:7.0.11`

#### download Mongo compass :

https://www.mongodb.com/try/download/compass

#### connect from Mongo compass to Mongo server at port: 27017

    ``` Advanced Connection Options
        -> authentication
        -> enter username and password
        -> connect
        -> successful ```

### 3. **------------------------ Install GraphDB NEO4J from docker ------------------------**

#### Start Docker

#### Install Neo4j from Docker Hub

#### CLI type to pull images of Docker Hub:

`docker pull neo4j:latest`

#### Start Neo4j server at port 7474 (admin page run on web) with root username and password: neo4j/12345678

#### at port 7687 -> connect to admin server (7474)

#### CLI type to start container from images of docker:

` docker run --publish=7474:7474 --publish=7687:7687 -e 'NEO4J_AUTH=neo4j/12345678' neo4j:latest`

### 4. **------------------------ Using Profile and variable environment ------------------------**

#### Using Profile

##### Docs Spring : https://docs.spring.io/spring-boot/reference/features/profiles.html

##### 1.Start Intellij

##### 2.Because values in dev environment usually difference in prod environment; need to edit change

###### example : url, username, password of db ,....vv.v.v of two environment is different

###### certain is: side dev env we can using db localhost with username and password we define by myself but prod env using db at cloud with url,username and password not same dev env -> need to override application yml at dev env

##### 3.At application of service need run, side right button debug -> click ` ...` -> choose `edit` -> choose ` modify options` -> choose ` add VM options` -> type `-Dspring.profiles.active=prod` above application class

###### ->> `active=prod` this mean depend on name `application-prod.yml` have "prod"

##### 4.Run application again -> will run with application yml file of `prod`

    ``` detail example more:
    Assume we have files following:
    - application.yml
    - application-dev.yml
    - application-prod.yml
    - custom-config.yml ```

#### And we want to using config in `custom-config.yml` to override values in `application-dev.yml`, we can beginning from step2 then type:

`-Dspring.profiles.active=dev -Dspring.config.location=classpath:/custom-config.yml,classpath:/application-dev.yml`

##### or using `spring.config.location`

`-Dspring.config.location=classpath:/custom-config.yml,classpath:/application-dev.yml`

#### this mean, will do Spring Boot load file `custom-config.yml` first,then load file `application-dev.yml` to override values config from `custom-config.yml`

#### Problem packing application to jar file

##### if you want to override values config default by config from a profile other,you can config as below:

##### in `application.yml`

    spring:
        config:
            active:
                on-profile: dev

##### then, you can have configs certain for each profile:

##### in `application-dev.yml`

    spring:
        config:
            active:
                on-profile: dev_value

##### in `application-prod.yml`

    spring:
        config:
            active:
                on-profile: prod_value

#### when we active profile prod, values `config.active.on-profile` will be `prod_value`.

#### Run with profile `dev`

#### CLI : `java -jar target/myapp.jar -Dspring.profiles.active=dev`

#### Run with profile `prod`

#### CLI : `java -jar target/myapp.jar -Dspring.profiles.active=prod`

### 5. **------------------------ Using Variable Environment ------------------------**

#### target is hide essential information need to secure about security in application yml config

##### Docs Spring : https://docs.spring.io/spring-boot/reference/features/external-config.html

    ``` To convert a property name in the canonical-form to an environment variable name you can follow these rules:
    - Replace dots (.) with underscores (_).
    - Remove any dashes (-).
    - Convert to uppercase.```

##### 1.Start Intellij

##### 2.At application of service need run, side right button debug -> click ` ...` -> choose `edit` -> choose ` Environment variables` -> choose icon ` same book` -> type `Name` and `Value` , Note that: when type `Name` need to implement role above

##### 3.Remove variable im yml application because that we used at step2 -> Run application again

    ``` ex: create variable environment in yml file to hide value 3600 of jwt valid-duration:

    ----------------- C1 -----------------
    -> in file yml original:

         jwt:
            valid-duration: 3600
            refresh: 36000

    -> value is: jwt.valid-duration -> have value 3600
    -> convert to variable environment by introduction rules above from "jwt.valid-duration" type  "Name" is : JWT_VALIDDURATION
        -> "Name" is valid by rules above, then type "Value" : 3600;

    -> in file yml then change:

         jwt:
            refresh: 36000

    -> we can see result of yml file then change was hided value "valid-duration" to security

    ----------------- or using C2 -----------------

    -> in file yml original:

         jwt:
            valid-duration: 3600
            refresh: 36000


    -> changed yml file to:

         jwt:
            valid-duration: ${JWT_VALIDDURATION}
            refresh: 36000

    -> convert to variable environment : type "Name" is : JWT_VALIDDURATION same with variable, then type "Value" : 3600;
    -> we can result of value "valid-duration" in yml file then change, was hided by "${JWT_VALIDDURATION}" to security,
    -> can see at application.yml with db
        -> explaint: (${DBMS_PASSWORD:123456} this means, if not define value "Name" and "Value" in step2
            -> will get default value after character ":" is 123456)

### 6. **------------------------ Config Kafka from Docker ------------------------**

###### NOTE THAT : time before , kafka have service is ZooKeeper help management clusters in system kafka, since version 3.6

###### Kafka using new system replace for Zookeeper is Kraft metadata system, since version 4.0 kafka remove Zookeeper

###### we will using version 3.7 -> no need to using Zookeeper of Kafka

##### docs: https://kafka.apache.org/blog

##### 1.At docker-composer.yml file containing information essentially for kafka configuration, can see at this file

##### 2.At terminal, cd to folder of project and type `docker-compose up -d` ,

###### this mean -> take information from docker-composer.yml file to install kafka (note that: have to install docker)

###### this system , we implementation on identity services and notification service

###### 3.determine services is consumer or producer, after add dependency kafka into pom.xml

###### 4.ex: add dependency into pom.xml, because using kafka at notification service (consumer) and identity service (producer) (at 2 this service)

###### 5.can reference config yml file at notification service (consumer) and identity service (producer)

###### NOTE THAT:

##### Kafka Architecture

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka.png)

##### Kafka Brokers

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_broker.png)

##### Kafka Topics

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_topic.png)

##### Kafka Partitions

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_partitions.png)

##### Kafka Offset

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_offset.png)

##### Kafka GroupID

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_group.png)

##### Kafka Comsumer

![Hình ảnh](https://github.com/LHHT-DISCOVERY/spring-boot-microservice-social-media/blob/main/kafka_architecture/kafka_consumer.png)

###### ** --- PROBLEM 1 --- **

###### when side producer publishes messages to "topic1" in Kafka system

###### if side consumer listeners message from "topic2" -> will not receive messages

###### because before, producer already pushed message to kafka at "topic1"

###### -> when change consumer to listeners "topic1" -> will receive messages from "topic1"

###### so, only need side consumer change to "topic1" -> will receive messages

###### -> this mean, no influence or call api again from identity service -> same according to the queue mechanism

###### -> in practice, these things, this mean consumer was disconnected to topic in kafka

###### -> after if consumer connection successful again -> still receive messages latest that no read yet -> this message not duplicate

###### FLOW KAFKA : SERVICE_1 (PRODUCER) AND SERVICE_2 (CONSUMER) AND "GROUP A" , "TOPIC DIFFERENT BUT SAME GROUP"

###### AT SERVICE_1 -> PUBLISH MESSAGE TO KAFKA AT "TOPIC1" -> SERVICE_2 LISTENER AT "TOPIC2" AND "GROUP A" -> START SERVER OF SERVICE 1 AND 2

###### -> SERVICE_2 NOT RECEIVED MESSAGE FROM "TOPIC1" -> THIS MEAN, MESSAGE AT "TOPIC1", SERVICE_2 NOT READ YET

    BECAUSE SERVICE_1 PUSH TO "TOPIC1" BUT SERVICE_2 IS READING MESSAGE AT "TOPIC2", SO NOT RECEIVED MESSAGE FROM "TOPIC1"

###### -> WHEN SERVICE_2 CHANGED TO LISTENER TO "TOPIC1" -> CONTINUE START SERVER OF SERVICE_2 (CONSUMER) AGAIN (\*)

###### -> SERVICE_2 WILL RECEIVED MESSAGE LATEST AND NOT READ YET FROM "TOPIC1"

###### -> AFTER SERVICE_2 FINISHED READING MESSAGE FROM "TOPIC1" -> WHEN CONTINUE START SERVER OF SERVICE_2 (CONSUMER) AGAIN

###### -> SERVICE_2 DIDN'T RECEIVE THE NOTIFICATION

    BECAUSE SERVICE_2 CONSUMED THIS MESSAGE BEFORE AT STEP (*)

###### ** --- PROBLEM 2 --- **

###### FLOW KAFKA : SERVICE_1 (PRODUCER) AND SERVICE_2 (CONSUMER) AND "GROUP A" AFTER CHANGE TO "GROUP B", "TOPIC SAME BUT DIFFERENT GROUP"

###### AT SERVICE_1 -> PUBLISH MANY MESSAGES TO KAFKA AT "TOPIC1"

###### -> SERVICE_2 LISTENER AT "TOPIC1" WITH "GROUP A" AND ALREADY READ ALL MESSAGES (\*)

###### -> WHEN SERVICE_2 STILL LISTENER FROM "TOPIC1" BUT NOW CHANGE AGAIN TO "GROUP B"

###### -> START SERVER OF SERVICE_2 (CONSUMER) AGAIN -> SERVICE_2 RECEIVE ALL MESSAGES FROM "TOPIC1" -> FROM OLDEST TO LATEST (\*\*)

    ``` SERVICE_2 READ SUCCESSFUL ALL MESSAGE FROM "TOPIC1" BECAUSE SERVICE_2 READ BY "GROUP".
        ->BEFORE, SERVICE_2 AT "GROUP A" ALREADY CONSUMED ALL MESSAGES
        -> WHEN USING NEW "GROUP B" JOIN INTO , SERVICE_2 WILL REQUEST ALL MESSAGE FROM THE BEGINNING TO THE PRESENT
      ```

###### -> WHEN CONTINUE START SERVER OF SERVICE_2 (CONSUMER) AGAIN -> SERVICE_2 NOT READ

    -> BECAUSE SERVICE_2 ALREADY CONSUMED ALL THIS MESSAGES FROM "TOPIC1" WITH "GROUP B" BEFORE AT STEP (**).

###### -> CONTINUE WHEN WE BACK TO "GROUP A" -> CONTINUE START SERVER OF SERVICE_2 (CONSUMER) AGAIN -> SERVICE_2 NOT READ

    -> BECAUSE CONSUMED MESSAGE IN "GROUP A" AT STEP (*)

###### -> IF SERVICE_1 -> CREATE MESSAGE -> SERVICE_2 WILL CAN RECEIVE MESSAGE

    -> BECAUSE MESSAGE AT "TOPIC1" WITH "GROUP A" NOT CONSUME YET, IT JUST CREARE NEW MESSAGE BY SERVICE_1

###### when producer sent an object to kafka for message , we using extend from JsonMessageConverter

    -> JsonMessageConverter help server receive request have datatype is json -> then server auto convert to Object in Java
    -> process business with object java (by json sent to server just convert to java) -> and can return again datatype is json for client
