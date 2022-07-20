FROM openjdk:17-alpine
EXPOSE 8899
ADD target/demo.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]