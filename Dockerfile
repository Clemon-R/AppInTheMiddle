FROM openjdk:8-jdk

WORKDIR /app

COPY . .

RUN ./gradlew tasks
RUN ./gradlew build
RUN ./gradlew jar

CMD java -jar build/libs/AppInTheMiddle-1.0.jar