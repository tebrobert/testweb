FROM openjdk:11

#ENV SBT_VERSION 1.5.5

#RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v1.5.5/sbt-$SBT_VERSION.zip
#RUN unzip sbt-$SBT_VERSION.zip -d ops

#WORKDIR /Testweb

COPY ./target/scala-2.13/testweb-assembly-v4.jar testweb.jar

EXPOSE 8080

CMD java --class-path testweb.jar com.example.testweb.Main
