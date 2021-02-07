FROM adoptopenjdk/openjdk11

EXPOSE 7000
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/jaya/currency-converter.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/jaya/currency-converter.jar