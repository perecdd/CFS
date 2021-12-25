FROM openjdk:14-jdk-alpine
COPY target/swagger-spring-1.0.0.jar cfs/swagger-spring-1.0.0.jar
ENTRYPOINT ["java","-jar","/cfs/swagger-spring-1.0.0.jar"]