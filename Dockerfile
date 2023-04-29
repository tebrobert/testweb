FROM postgres:14.3

ENV POSTGRES_PASSWORD=Stt-6789

RUN echo create table test \(id int\) ';' >> /docker-entrypoint-initdb.d/1.sql
RUN echo insert into test values \(555\) ';' >> /docker-entrypoint-initdb.d/1.sql

RUN echo 'echo port 5555 >> /var/lib/postgresql/data/postgresql.conf' >> /docker-entrypoint-initdb.d/1.sh

RUN apt-get update && apt-get install -y default-jre



COPY ./target/scala-2.13/testweb-assembly-v4.jar testweb.jar

RUN echo 'java --class-path testweb.jar com.example.testweb.Main &' >> /docker-entrypoint-initdb.d/1.sh

