FROM maven:3.6.0-jdk-8-slim AS build-env
MAINTAINER mtons
WORKDIR /build
COPY . /build
RUN mvn package -Dmaven.test.skip -Ph2

FROM openjdk:8u181-jre-slim
ENV TZ=Asia/Shanghai
RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone
WORKDIR /app
COPY --from=build-env /build/target/mblog-latest.jar /app
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/mblog-latest.jar"]