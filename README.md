# spring-boot-microservice-social-media

## Microservices đang là kiến trúc được sử dụng rộng rãi nhất hiện nay, repo này thực hiện cách triển khai micoservices với spring boot 3 thông qua việc xây dựng từ đầu dự án mạng xã hội

#build image 'mysql' with docker :
#0: "docker run" using to run image (then run -> show in container) we just pull from docker hub
#1: "--name mysql-8.0.36" is name of container
#2: "-p 3306:3306" export port to public , "3306"(port of mysql) : "3306" (port need to export to using connection)
#3: "-e MYSQL_ROOT_PASSWORD=123456" is set password for mysql : 123456
#4 : "-d mysql:8.0.36-debian" version of mysql image just pull from docker hub, "8.0.36-debian"
CLI type: docker run --name mysql-8.0.36 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.36-debian