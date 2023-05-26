FROM openjdk:17-alpine
COPY target/*.war /usr/local/lib/app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.war"]