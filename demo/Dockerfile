FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y netcat

COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8080

ENV REDIS_HOST=redis
ENV REDIS_PORT=6379
ENV MYSQL_HOST=mysql
ENV MYSQL_PORT=3306


ENTRYPOINT sh -c "/wait-for-it.sh \"$MYSQL_HOST\" \"$MYSQL_PORT\" -- java -jar app.jar"
