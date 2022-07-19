FROM openjdk:17-alpine
EXPOSE 8899
ADD target/testJar.jar testJar.jar
ENTRYPOINT ["java","-jar","testJar.jar"]