FROM maven:3.5.4-jdk-8
MAINTAINER mtons

ENV TZ=Asia/Shanghai db_u="root" db_pwd="root"
RUN mkdir /app && ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

WORKDIR /app

ADD mblog-latest.jar mblog-latest.jar
ENTRYPOINT ["java",  "-jar", "/app/mblog-latest.jar", "--spring.datasource.username=${db_u}","--spring.datasource.password=${db_pwd}"]