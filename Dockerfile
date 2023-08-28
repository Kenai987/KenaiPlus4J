# 使用基础的Java 1.8镜像
FROM openjdk:8-jdk

# 设置工作目录
WORKDIR /app

# 复制所有项目文件到镜像中
COPY . /app

# 将构建后的可执行jar文件复制到镜像的根目录
COPY web/target/KenaiPlus4J.jar app.jar

# 暴露80端口
EXPOSE 80

# 运行Spring Boot应用
CMD ["java", "-jar", "app.jar"]