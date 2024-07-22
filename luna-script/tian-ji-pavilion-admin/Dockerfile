# docker pull eclipse-temurin:17-jre-alpine
FROM eclipse-temurin:17-jre-alpine

# 作者信息
MAINTAINER luna

# 语言环境为中文
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8

# 设置时区
ENV TZ=Asia/Shanghai

# 设置工作目录
WORKDIR /app

# 复制jar文件
COPY ./luna-server-tian-ji-pavilion.jar /app/luna-server-tian-ji-pavilion.jar

# JVM参数配置
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
# Application 参数
ENV ARGS="--server.port=80"

# 暴露端口
EXPOSE 80

# 启动
CMD java ${JAVA_OPTS} -jar /app/luna-server-tian-ji-pavilion.jar $ARGS
