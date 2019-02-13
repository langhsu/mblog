FROM maven:3.5.4-jdk-8
MAINTAINER mtons

ENV TZ=Asia/Shanghai
RUN mkdir /app && ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

WORKDIR /app

ADD mblog-latest.jar mblog-latest.jar
ENTRYPOINT ["java",  "-jar", "/app/mblog-latest.jar"]