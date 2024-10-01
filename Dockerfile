FROM amazoncorretto:17
RUN touch /env.txt
RUN printenv > /env.txt
MAINTAINER korede
COPY target/Transaction-Statistics-0.0.1-SNAPSHOT.jar Transaction-Statistics-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "/Transaction-Statistics-0.0.1-SNAPSHOT.jar"]