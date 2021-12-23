FROM openjdk:11
COPY ./target/swagger-spring-1.0.0.jar /tmp
WORKDIR /tmp
#ENTRYPOINT ["java -jar","./swagger-spring-1.0.0.jar"]