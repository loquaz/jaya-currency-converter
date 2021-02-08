#!/bin/bash

git checkout docker
mvn clean package assembly:single
java -jar target/jaya-cc-1.0-jar-with-dependencies.jar