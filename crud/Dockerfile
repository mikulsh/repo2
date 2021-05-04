# FROM openjdk:11
# ADD target/docker-spring-project.jar app.jar
# # EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]



FROM openjdk:11
ADD target/docker-spring.jar docker-spring.jar
ENTRYPOINT ["java","-jar","docker-spring.jar"]