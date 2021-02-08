FROM adoptopenjdk/openjdk11

EXPOSE ${PORT}
ARG JAR_FILE 
ADD target/jaya-cc-1.0-jar-with-dependencies.jar /usr/share/jaya/currency-converter.jar
CMD ["/opt/java/openjdk/bin/java", "-jar", "/usr/share/jaya/currency-converter.jar"]