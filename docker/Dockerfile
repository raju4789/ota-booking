# our base build image
FROM maven:3.6.3-jdk-11 as mvn

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN mvn package

EXPOSE 8081

ENTRYPOINT mvn exec:java
