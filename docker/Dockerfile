
FROM adoptopenjdk/openjdk11:alpine-jre

RUN mkdir /opt/app
# cp target/spring-boot-web.jar /opt/app/app.jar
COPY app.jar /opt/app/
COPY startapi.sh /opt/app/startapi.sh
RUN chmod +x /opt/app/startapi.sh

EXPOSE 8080

ENTRYPOINT ["sh" ,"/opt/app/startapi.sh"]