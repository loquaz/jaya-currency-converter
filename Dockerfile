FROM adoptopenjdk/openjdk11

EXPOSE ${PORT}
ARG JAR_FILE 
ADD target/${JAR_FILE} /usr/share/jaya/currency-converter.jar
CMD ["/opt/java/openjdk/bin/java", "-jar", "/usr/share/jaya/currency-converter.jar"]