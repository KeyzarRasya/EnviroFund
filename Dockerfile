# Gunakan base image Java 11
FROM openjdk:20-jdk

# Tentukan direktori kerja dalam kontainer
ARG JAR_FILE=target/*.jar

# Salin JAR aplikasi Anda ke dalam kontainer
COPY ./target/EnviroFund-1.0-SNAPSHOT.jar app.jar

# Jalankan aplikasi saat kontainer dimulai
ENTRYPOINT ["java", "-jar", "/app.jar"]
