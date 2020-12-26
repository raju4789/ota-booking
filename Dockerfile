FROM openjdk:8
ADD target/booking-0.0.1-SNAPSHOT.jar booking-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "booking-0.0.1-SNAPSHOT.jar", "com.ogado.booking.BookingApplication"]