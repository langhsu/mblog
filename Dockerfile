FROM maven:3.5.4-jdk-8
MAINTAINER langhsu

ENV TZ=Asia/Shanghai mysql_user="root" mysql_password="root"
RUN mkdir /app && ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

WORKDIR /app

ADD mblog-latest.jar mblog-latest.jar
ENTRYPOINT ["java",  "-jar", "/app/mblog-latest.jar", "--spring.datasource.username=${mysql_user}","--spring.datasource.password=${mysql_password}"]