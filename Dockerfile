FROM openjdk:8-jre-alpine

EXPOSE 5000
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/jaya/currency-converter.jar"]

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/jaya/currency-converter.jar