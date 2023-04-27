FROM openjdk:11

COPY ./target/scala-2.13/testweb-assembly-v4.jar testweb.jar

EXPOSE 8080

CMD java --class-path testweb.jar com.example.testweb.Main

