#!/bin/bash

git clone git@github.com:loquaz/jaya-currency-converter.git
cd jaya-currency-converter
git checkout docker
mvn clean package assembly:single
java -jar target/jaya-cc-1.0-jar-with-dependencies.jar