# Extend vert.x base image
FROM vertx/vertx3

ENV VERTICLE_JAR target/vertx-task-flow-1.0-SNAPSHOT-fat.jar
ENV VERTICLE_HOME verticles

EXPOSE 8006

# Copy the jar file into a container folder
COPY $VERTICLE_JAR $VERTICLE_HOME/application.jar

WORKDIR .

#
ENTRYPOINT ["sh", "-c","java -cp $VERTICLE_HOME/application.jar com.oxy.vertx.Main"]
