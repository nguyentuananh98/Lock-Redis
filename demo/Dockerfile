# 1. Dùng base image Java 17
FROM openjdk:17-jdk-slim

# 2. Tạo thư mục làm việc
WORKDIR /app

# Cài netcat để wait-for-it.sh hoạt động
RUN apt-get update && apt-get install -y netcat

# 3. Copy app jar và wait-for-it script vào container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# 4. Expose cổng
EXPOSE 8080

# 5. Biến môi trường (có thể override từ docker-compose.yml)
ENV REDIS_HOST=redis
ENV REDIS_PORT=6379
ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306

# 6. Chờ MySQL rồi mới chạy app
ENTRYPOINT sh -c "/wait-for-it.sh $MYSQL_HOST:$MYSQL_PORT -- java -jar app.jar"

