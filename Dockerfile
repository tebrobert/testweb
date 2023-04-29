#FROM openjdk:11
FROM postgres:14.3

ENV POSTGRES_PASSWORD=Stt-6789
#EXPOSE 5432
RUN echo create table test \(id int\) ';' >> /docker-entrypoint-initdb.d/1.sql
RUN echo insert into test values \(555\) ';' >> /docker-entrypoint-initdb.d/1.sql

RUN apt-get update && apt-get install -y default-jre

COPY ./target/scala-2.13/testweb-assembly-v4.jar testweb.jar
EXPOSE 8080

#CMD ["java", "--class-path", "testweb.jar", "com.example.testweb.Main", "&"]
RUN echo 'java --class-path testweb.jar com.example.testweb.Main &' >> /docker-entrypoint-initdb.d/init_docker_postgres.sh

