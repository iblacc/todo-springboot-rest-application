FROM openjdk:11

WORKDIR /usr/app

COPY ./target/todo-app-0.0.1-SNAPSHOT.jar .

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "todo-app-0.0.1-SNAPSHOT.jar"]
