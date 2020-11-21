FROM maven:3.6.3-jdk-11
COPY ./ ./
RUN mvn clean package
COPY /target/booksLibrary.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]