# 使用官方的 OpenJDK 基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 复制 Maven 构建文件
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# 下载依赖（利用 Docker 缓存加速构建）
RUN ./mvnw dependency:go-offline -B

# 复制源代码
COPY src src

# 构建应用
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# 暴露应用端口（与 application.properties 中的 server.port 一致）
EXPOSE 8080

# 运行应用
ENTRYPOINT ["java", "-jar", "target/regional-weather-0.0.1-SNAPSHOT.jar"]
