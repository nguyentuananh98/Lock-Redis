# 1. Dùng base image Java 17
FROM openjdk:17-jdk-slim

# 2. Tạo thư mục làm việc
WORKDIR /app

# 3. Copy jar vào container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose cổng
EXPOSE 8080

# 5. Biến môi trường mặc định (tùy chọn, có thể override)
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379

# 6. Chạy app
ENTRYPOINT ["java", "-jar", "app.jar"]
