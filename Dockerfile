# 指定基础镜像
FROM openjdk:8-jre-slim
# 维护者信息
MAINTAINER ciaj. <595009116@qq.com>

ENV LANG en_US.UTF-8
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Djava.awt.headless=true"

# 用于指定持久化目录
VOLUME /tmp

#RUN apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 将本地文件添加到容器中
ADD ./target/ciaj-admin.jar app.jar
# 指定于外界交互的端口
EXPOSE 1211
# 配置容器，使其可执行化
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Duser.timezone=GMT+08", "-jar","/app.jar"]