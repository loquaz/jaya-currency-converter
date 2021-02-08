#!/bin/bash

mvn clean package assembly:single
docker container stop $(docker container ps -aq)
docker container rm $(docker container ps -aq)
JAR_FILE=target/jaya-cc-1.0-jar-with-dependencies.jar
PORT=6000
if [[ -f "$JAR_FILE" ]]; then
    echo "$JAR_FILE"
    docker build -t jaya-app . && docker run --name jaya-app-container -p 5000:5000 jaya-app
else 
    echo "$JAR_FILE not found"
fi