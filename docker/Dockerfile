
FROM adoptopenjdk/openjdk11:alpine-jre
# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY app.jar app.jar
COPY startapi.sh startapi.sh

EXPOSE 8080
# java -jar /opt/app/app.jar
ENTRYPOINT ["startapi.sh"]